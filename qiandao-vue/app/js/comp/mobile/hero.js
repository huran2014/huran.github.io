define('comp/mobile/hero', function(require, exports, module) {
    var sso = require('mods/global/sso');
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');

    var data = {
        isLogin: sso.isLogin,
        list: [],
        length: 0
    };
    var comp = Vue.component('app-hero', {
        template: tpl.hero,
        data: function() {
            return data;
        },
        methods: {
            // 滚动

            // 请求中奖名单接口
            reqListApi: function() {
                var _this = this;
                $.ajax({
                    url: globalVars.apiDomain + '/Task/index/hero',
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var data = res.data;
                        if (code == 0) {
                            _this.length = data.length;
                            if (_this.length) {
                                $.each(data, function(key, value) {
                                    value.picture = value.picture.split(',')[2];
                                });
                                _this.list = data;
                            }

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
                this.reqListApi();
            }
        }
    });
    module.exports = comp;
});
