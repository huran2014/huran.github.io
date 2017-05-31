define('comp/mobile/user', function(require, exports, module) {

    var sso = require('mods/global/sso');
    var tpl = require('template/mobile/tpl');
    var globalVars = require('mods/global/vars');


    // 加载组件
    var data = {
        isLogin: sso.isLogin,
        userinfo: sso.userinfo,
        frame: '',
        hasFrame: false,
        normalClass: '',
        frameClass: 'C_thumb_nobrd'
    };
    var comp = Vue.component('app-user', {
        template: tpl.user,
        data: function() {
            return data;
        },
        methods: {
            reqUserFrameApi: function() {
                var _this = this;
                $.ajax({
                    url: globalVars.apiFrameDomain + '/get_by_uid?uid=' + sso.userinfo.ssouid,
                    type: 'GET',
                    dataType: 'jsonp',
                    success: function(res) {
                        var code = res.code;
                        var data = res.data;
                        if (code == 200) {
                            if (data.frame_img) {
                                _this.frame = data.frame_img.replace('http://', 'https://');
                                _this.hasFrame = true;
                            }
                        } else {
                            return;
                        }
                    },
                    error: function() {
                        return;
                    }
                });
            }
        },
        created: function() {
            // var _this = this;
            if (this.isLogin) {
                this.reqUserFrameApi();
            }
        }
    });
    module.exports = comp;
});
