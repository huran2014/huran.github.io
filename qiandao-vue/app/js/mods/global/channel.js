define('mods/global/channel', function(require, exports, module) {
    var env = require('mods/global/env');
    var channel = 0;
    if (env.isLeMe) {
        channel = 0;
    }
    module.exports = channel;
});
