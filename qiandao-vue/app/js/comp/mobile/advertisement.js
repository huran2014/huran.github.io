define('comp/mobile/advertisement', function(require, exports, module) {

    var sso = require('mods/global/sso');
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');
    var channel = require('mods/global/channel');

    var data = {
        isLogin: sso.isLogin,
        list: [],
        widthTotal: 0
    };
    var comp = Vue.component('app-ad', {
        template: tpl.advertisement,
        data: function() {
            return data;
        },
        methods: {
            // 请求接口
            reqListApi: function() {
                var _this = this;
                $.ajax({
                    url: globalVars.apiDomain + '/Sign/Index/imgad/channel/' + channel,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var data = res.data;
                        var length = 0;
                        if (code == 0) {
                            _this.list = data;
                            length = data.length;
                            _this.widthTotal = length * 14.8 + 'rem';
                        }
                    },
                    error: function() {
                        return;
                    }
                });
            },
        },
        created: function() {
            if (this.isLogin) {
                this.reqListApi();
            }
        }
    });
    module.exports = comp;
});
