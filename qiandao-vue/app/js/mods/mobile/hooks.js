define('mods/mobile/hooks', function(require, exports, module) {
    var sso = require('mods/global/sso');


    return function(to, from, next) {
        next();
        return;
    };

});
