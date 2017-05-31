define('mods/util/transDate', function(require, exports, module) {
    var format0Date = function(date) {
        date = date.split('/');
        if (date[1] < 10) {
            date[1] = "0" + parseInt(date[1]);
        }
        if (date[2] < 10) {
            date[2] = "0" + parseInt(date[2]);
        }
        date = date.join('/');

        return date;
    };
    var timestampToDate = function(timestamp) {
        // php反回的时间戳是秒，需要变成毫秒格式
        timestamp = timestamp * 1000;
        var dd = new Date(timestamp);
        var y = dd.getFullYear();
        var m = dd.getMonth() + 1;
        var d = dd.getDate();

        return format0Date(y + '/' + m + '/' + d);
    };
    module.exports = timestampToDate;
});
