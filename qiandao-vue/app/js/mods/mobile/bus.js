define('mods/mobile/bus', function(require, exports, module) {
    var Event = require('mods/global/event');


    var bus = new Vue();
    bus.event = new Event();

    module.exports = bus;

});
