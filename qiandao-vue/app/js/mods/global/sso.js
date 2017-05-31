/*
 * 请在页面引用 //jstatic.letvcdn.com/sdk/passport.js
 * 登录模块
 * author: liuluying@le.com
 * */

define('mods/global/sso', function(require, exports, module) {

    // var ENV = require('mods/global/ENV');
    var Event = require('mods/global/event');

    function Sso() {
        var _this = this;
        _this.event = new Event;
        LEPass.onReady(function(res) {
            _this.userinfo = res.userinfo;
        }, {
            plat: 'bbs'
        });
        // this.ssouid = LEPass.user.ssoUid;
        // var _t = this;
        //if( LEPass.isLogin()) {
        //    $.getScript('http://bbs.le.com/misc.php?mod=pgv'); //同步用户信息到社区
        //}
    }
    Sso.prototype.showLoginDialog = function(fn) {
        //PC用弹窗
        var _this = this;
        if (LEPass.isLogin()) {
            fn && fn(this.userinfo);
        } else {
            LEPass.openLoginDialog(function(userinfo) {
                _this.userinfo = userinfo.userinfo;
                if (typeof fn === 'function') {
                    fn(_this.userinfo);
                    _this.event.fire(_this, 'login', _this.userinfo);
                } else {
                    window.location.reload();
                }
            });
        }
    };
    Sso.prototype.logOutPC = function(fn) {
        var _this = this;
        LEPass.logOut(function() {
            if (typeof fn === 'function') {
                fn();
                _this.event.fire(_this, 'logout', _this.userinfo);
            } else {
                window.location.reload();
            }
        });

    };

    Sso.prototype.showLogin = function(fn) {
        try {
            LEPass.openLoginPage();
        } catch (e) {}

        // var ssouid = this.userinfo.uid;
        // if (ssouid > 10) {
        //     return typeof fn == 'function' && fn({
        //         ssouid: this.ssouid
        //     });
        // }
        // if (ENV.isLeMe) { //乐迷登录
        //     //TODO: 增加对iOS的判断
        //     try {
        //         LetvJSBridge_For_Android.fun_callLogin('[{}]');
        //         return false;
        //     } catch (e) {
        //         LeMeJSBridge.showLogin();
        //         return false;
        //     }
        // } else {
        //     LEPass.openLoginPage();
        //     return false;
        // }
    };

    Sso.prototype.showRegister = function() {
        try {
            LEPass.openRegisterPage();
        } catch (e) {}
    };

    Sso.prototype.logOut = function() {
        try {
            LEPass.logOut(function() {
                window.location.reload();
                // LEPass.openLoginPage();
            });
        } catch (e) {}

        // //TODO:增加iOS判断
        // if (ENV.isLeMe) {
        //     //app不显示退出
        // } else {
        //     LEPass.logOut(function() {
        //         LEPass.openLoginPage();
        //     });
        //     return false;
        // }
    };

    Sso.prototype.isLogin = LEPass.isLogin();

    return new Sso();

});
