/**
 * @fileoverview 全局变量
 * @author liyifei@le.com
 * @date 2017/04
 */
define('mods/global/vars', function(require, exports, module) {
    // 获取url地址
    var url = window.location.href;
    // 获取协议
    var protocol = window.location.protocol;
    // 是否是测试环境
    var isTest = url.match('local') || url.match('test-sign');
    var apiDomain;

    if (isTest && protocol == 'http:') {
        apiDomain = 'test.sign.bbs.le.com';
    } else if (isTest && protocol == 'https:') {
        apiDomain = 'test.sign-bbs.le.com';
    } else if (!isTest && protocol == 'http:') {
        apiDomain = 'sign.bbs.le.com';
    } else {
        apiDomain = 'sign-bbs.le.com';
    }

    var globalVars = {
        // 接口域名
        apiDomain: protocol + '//' + apiDomain,
        apiFrameDomain: protocol + '//' + 'ues-i.le.com'
    };

    module.exports = globalVars;

});
