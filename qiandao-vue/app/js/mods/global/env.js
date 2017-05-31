define('mods/global/env', function(require, exports, module) {
    var ua = navigator.userAgent.toLowerCase();
    var ENV = {
        isLeMe: ua.indexOf('leme') > 0,
        debug: false
    };

    module.exports = ENV;
});
