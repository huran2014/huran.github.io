define('lib/chaos/ajax', function(require, exports, module) {
    var ajax, jquery;
    if (window.XDomainRequest) {
        jquery = require('jquery');
        ajax = require('lib/jquery/cors-ie9')(jquery).ajax;
    } else {
        ajax = require('zepto').ajax;
    }
    return ajax;
});