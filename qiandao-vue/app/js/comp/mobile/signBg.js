define('comp/mobile/signBg', function(require, exports, module) {
    var tpl = require('template/mobile/tpl');
    var channel = require('mods/global/channel');
    var globalVars = require('mods/global/vars');


    var data = {
        backgroundImg: 'https://i2.letvimg.com/lc06_iscms/201702/21/14/13/5b4d55f479ce4cbe842bc10041fd4f7e.png'
    };
    var comp = Vue.component('app-signBg', {
        template: tpl.signBg,
        data: function() {
            return data;
        },
        methods: {
            // 背景图
            reqBgImgApi: function() {
                var _this = this;
                $.ajax({
                    url: globalVars.apiDomain + '/Sign/Index/bgimg/channel/' + channel,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.ret;
                        var imgCover = res.data.img1;

                        // 浮层背景
                        if (code == 0 && imgCover) {
                            _this.backgroundImg = imgCover;

                        }
                    },
                    error: function() {

                    }
                });
            }
        },
        created: function() {
            this.reqBgImgApi();
        }
    });
    module.exports = comp;
});
