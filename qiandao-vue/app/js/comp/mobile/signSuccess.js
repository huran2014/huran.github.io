define('comp/mobile/signSuccess', function(require, exports, module) {
    var tpl = require('template/mobile/tpl');
    var router = require('mods/mobile/router');
    var bus = require('mods/mobile/bus');
    var globalVars = require('mods/global/vars');
    var sso = require('mods/global/sso');
    var env = require('mods/global/env');
    var weather = require('mods/util/weather');

    var curDate = new Date();
    var curMonth = curDate.getMonth() + 1;
    var curDay = curDate.getDate();

    var data = {
        // 签到信息
        credit: '0',
        lasted: '0',
        extra: '0',
        cardId: '0',
        activeId: '0',
        extraContent: '',
        // 是否是leme环境
        isleme: env.isLeMe,
        // 运势
        fortune: '老夫掐指一算，您还没有完善您的个人信息。',
        btnContent: '',
        href: 'javascript:;',
        // 天气
        city: '北京',
        temp: '',
        weatherIcon: '',
        date: curMonth + '月' + curDay + '日',
        // 红包
        detailUrl: '',
        isEmpty: true
    };
    var comp = Vue.component('app-signSuccess', {
        template: tpl.signSuccess,
        data: function() {
            return data;
        },
        methods: {
            onCloseLayer: function() {
                router.replace({
                    path: '/'
                });
            },
            // 请求运势接口
            reqAstroApi: function() {
                var _this = this;
                $.ajax({
                    url: globalVars.apiDomain + '/Sign/Index/astro/uid/' + sso.userinfo.ssouid,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var data = res.data;
                        // 运势

                        if (code == 0) {
                            if (data && !$.isArray(data) && data.fortune) {
                                _this.btnContent = '查看更多运势';
                                _this.fortune = data.fortune;
                            } else {
                                _this.btnContent = '去完善';
                                _this.href = '/sign/userinfo.html';
                            }
                        }
                    },
                    error: function() {
                        return;
                    }
                });
            },
            // 请求天气接口
            reqWeatherApi: function() {
                var _this = this;
                $.ajax({
                    url: globalVars.apiDomain + '/Sign/Index/weather',
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var data = res.data;
                        var weatherCode;
                        if (code == 0) {
                            if (data.city) {
                                _this.city = data.city;
                            }
                            if (data.temp.indexOf('-') > -1) {
                                _this.temp = data.temp;
                            } else {
                                _this.temp = data.temp + '°C';
                            }
                            weatherCode = weather(data.code);
                            _this.weatherIcon = 'icon_w_' + weatherCode;
                        }
                    },
                    error: function() {
                        return;
                    }
                });
            },
            // 请求红包详情接口
            reqPackageDetailApi: function() {
                var _this = this;
                if (this.cardId && this.activeId) {
                    $.ajax({
                        url: globalVars.apiDomain + '/Package/Index/cardInfo/activeId/' + _this.activeId + '/cardId/' + _this.cardId,
                        type: 'GET',
                        dataType: 'jsonp',
                        success: function(res) {
                            var code = res.ret;
                            if (code == 0) {
                                // 获取成功
                                _this.detailUrl = res.data.url;
                                _this.isEmpty = false;
                            }
                        },
                        error: function() {
                            return;
                        }
                    });
                }
            }
        },
        created: function() {
            var _this = this;
            // 签到信息
            bus.$on('signResult', function(data) {
                console.log('signResult', data);
                _this.credit = data.credit;
                _this.lasted = data.lasted;
                _this.extra = data.extra;
                _this.cardId = data.cardId;
                _this.activeId = data.activeId;
                if (_this.extra != "0" && _this.cardId && _this.activeId) {
                    _this.extraContent = '额外奖励乐米+红包';
                } else if (_this.extra != "0" && !_this.cardId && !_this.activeId) {
                    _this.extraContent = '额外奖励乐米';
                } else if (_this.extra == "0" && _this.cardId && _this.activeId) {
                    _this.extraContent = '额外奖励红包';
                } else if (_this.extra != "0" && !_this.cardId && _this.activeId) {
                    _this.extraContent = '额外奖励乐米';
                }
            });
            // 天气、运势
            this.reqAstroApi();
            this.reqWeatherApi();
            // 红包详情
            this.reqPackageDetailApi();
        }
    });
    module.exports = comp;
});
