define('comp/mobile/layout', function(require, exports, module) {
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');
    var channel = require('mods/global/channel');
    var sso = require('mods/global/sso');
    var router = require('mods/mobile/router');

    var data = {
        isLogin: sso.isLogin
    };
    var comp = Vue.component('app-layout', {
        template: tpl.layout,
        data: function() {
            return data;
        },
        methods: {
            // 点击月份显示日历
            onCalShow: function() {
                router.replace({
                    path: '/signCalendar'
                });
            },
            // 请求签到状态接口
            reqSignStatusApi: function(callback) {
                $.ajax({
                    url: globalVars.apiDomain + '/Sign/Index/getstatus/channel/' + channel,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        // 是否已经签到
                        if (code != 0) {
                            router.replace({
                                path: '/sign'
                            });
                        } else {
                            router.replace({
                                path: '/'
                            });
                        }
                    },
                    error: function() {
                        return;
                    }
                });
            }
        },
        created: function() {
            if (this.isLogin) {
                this.reqSignStatusApi();
            } else {
                sso.showLogin();
            }
        }
    });
    module.exports = comp;
});
