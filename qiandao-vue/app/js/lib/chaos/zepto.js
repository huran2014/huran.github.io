/**
 * @fileoverview zepto lib 混合文件
 * @authors liyifei@le.com
 * @date 2015/11
 */

define('lib/chaos/zepto', function(require, exports, module) {

    var zepto = require('lib/zepto/zepto'),
        corsIe9;
    // 模块
    require('lib/zepto/touch');
    require('lib/zepto/fx');
    require('lib/zepto/detect');
    module.exports = zepto;

});