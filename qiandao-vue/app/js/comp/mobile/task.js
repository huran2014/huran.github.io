define('comp/mobile/task', function(require, exports, module) {
    var sso = require('mods/global/sso');
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');


    var data = {
        isLogin: sso.isLogin,
        list: [],
        receiveLock: false
    };
    var comp = Vue.component('app-task', {
        template: tpl.task,
        data: function() {
            return data;
        },
        methods: {
            // 领取接口
            onReceive: function(id) {
                if (this.receiveLock) {
                    return;
                }
                this.receiveLock = true;
                var _this = this;
                $.ajax({
                    url: globalVars.apiDomain + '/Task/Index/getRecord/type/0/id/' + id,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        if (code == 0) {
                            alert('领取成功');
                            // 刷新任务列表
                            _this.reqTaskApi(function() {
                                _this.reqTaskApi(function(data) {
                                    _this.renderTask(data);
                                });
                            });
                            // 刷新用户乐米数量

                        } else {
                            alert('乐米走失了，请刷新重新领取。');
                        }
                    },
                    complete: function() {
                        this.receiveLock = false;
                    },
                    error: function() {
                        alert('乐米走失了，请刷新重新领取。');
                    }
                });
            },
            // 请求任务接口
            reqTaskApi: function(callback) {
                $.ajax({
                    url: globalVars.apiDomain + '/Task/Index/tasks/type/0/uid/' + sso.userinfo.ssouid,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var data = res.data;
                        if (code == 0 && $.isArray(data) && callback) {
                            callback(data);
                        }
                    },
                    error: function() {
                        return;
                    }
                });
            },
            renderTask: function(res) {
                $.each(res, function(key, item) {
                    var stepList = [],
                        creditList = [];
                    if (item.step) {
                        stepList = item.step.split('\t');
                        // 阶段分母
                        item.total = stepList[0];
                    }
                    if (item.credit) {
                        creditList = item.credit.split('\t');
                        // 阶段奖励
                        item.reward = creditList[0];
                    }
                    var csc = Number(item.csc);
                    var stepCurr;
                    item.href = 'javascript:;';
                    // 阶段分子
                    item.stage = 0;

                    // 按钮文案
                    item.btnContent = '进行中';
                    // 是否完成任务
                    item.receiveFlag = false;
                    // 是否领取
                    item.achieveFlag = false;
                    if (item.available == '2' && item.csc) {
                        $.each(stepList, function(keyStep, itemStep) {
                            stepCurr = Number(itemStep);
                            // 可以领奖，且没有
                            if (csc == stepCurr && item.status == keyStep) { // 完成当前阶段
                                item.dataRole = 'task-receive';
                                item.btnContent = '领乐米';
                                item.reward = creditList[keyStep];
                                item.total = stepList[keyStep];
                                item.achieveFlag = true;
                                return false;
                            } else if (csc >= stepList[stepList.length - 1] && (item.status == "3" || item.status == "1")) { // 完成所有阶段
                                item.reward = creditList[stepList.length - 1];
                                item.total = stepList[stepList.length - 1];
                                item.receiveFlag = true;
                                return false;
                            } else if (csc == stepCurr && item.status > keyStep) { // 领取完上一阶段
                                item.reward = creditList[keyStep + 1];
                                item.total = stepList[keyStep + 1];
                                return false;
                            } else if (csc > stepCurr && item.status < keyStep) { // 完成上一阶段，且未完成下一阶段
                                item.reward = creditList[keyStep];
                                item.total = stepList[keyStep];
                                return false;
                            } else if (csc < stepCurr && item.status == keyStep) { // 未完成当前阶段
                                item.reward = creditList[keyStep];
                                item.total = stepList[keyStep];
                                return false;
                            }
                        });
                        item.stage = csc;
                        item.href = 'javascript:;';
                    } else if (item.id == '1') {
                        item.href = '/sign/userinfo.html';
                        item.dataRole = 'toAchieve';
                        item.btnContent = '去完成';
                        item.achieveFlag = true;
                    }
                    // 百分比
                    item.percent = (item.stage / item.total) * 100 + '%';

                    if ((item.stage / item.total) >= 1) {
                        item.percent = 1 * 100 + '%';
                    }
                });
                this.list = res;
            }
        },
        created: function() {
            var _this = this;
            if (this.isLogin) {
                this.reqTaskApi(function() {
                    _this.reqTaskApi(function(data) {
                        _this.renderTask(data);
                    });
                });
            }
        }
    });
    module.exports = comp;
});
