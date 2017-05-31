define('comp/mobile/sign', function(require, exports, module) {
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');
    var channel = require('mods/global/channel');
    var router = require('mods/mobile/router');
    var bus = require('mods/mobile/bus');

    var data = {
        reqLock: false
    };
    var comp = Vue.component('app-sign', {
        template: tpl.sign,
        data: function() {
            return data;
        },
        methods: {
            // 请求签到接口
            reqSignApi: function(callback) {
                var _this = this;
                if (this.reqLock) {
                    return;
                }
                this.reqLock = true;
                $.ajax({
                    url: globalVars.apiDomain + '/Sign/Index/index/channel/' + channel,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var data = res.data;
                        // 是否已签到
                        var isSign = parseInt(data.issign);
                        if (code == 0) {
                            if (isSign == 1) {
                                // 签到信息
                                console.log('sign', data);
                                bus.$emit('signResult', data);
                                // 签到成功
                                router.replace({
                                    path: '/signSuccess'
                                });
                            } else {
                                router.replace({
                                    path: '/'
                                });
                            }
                        } else {
                            alert('哇哦！页面迷路找不到了，请稍后再试吧！');
                        }
                    },
                    complete: function() {
                        _this.reqLock = false;
                    },
                    error: function() {
                        alert('哇哦！页面迷路找不到了，请稍后再试吧！');
                    }
                });
            }
        },
        created: function() {

        }
    });
    exports.module = comp;
});
