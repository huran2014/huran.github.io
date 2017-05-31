//var lrSnippet = require('grunt-contrib-livereload/lib/utils').livereloadSnippet;
var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;
var serveStatic = require('serve-static');
var globalConfig = require('./config');
var gb = require('./config').global;
var mountFolder = function(connect, dir) {
    return serveStatic(require('path').resolve(dir));
};

var connectCfg = {
    host: globalConfig.connect.host,
    port: 80,

    proxy: {
        host: '123.126.32.251',
        port: 80
    },

    middleware: function(connect, options, middlewares) {

        middlewares = [

            //lrSnippet,

            function(req, res, next) {
                var testJsReg = /(\/[^\/]+){5}(.*)\.js/;
                var testCssReg = /(\/[^\/]+){6}(.*)\.css/;
                var replaceNameReg = new RegExp('(\/[^\\/]+){5}/' + gb.projectName);
                if (testJsReg.test(req.url)) {
                    req.url = req.url.replace(/(\/[^\/]+){5}/, '/js');
                }
                if (testCssReg.test(req.url)) {
                    req.url = req.url.replace(replaceNameReg, '/css/conf');
                    req.url = req.url.replace(/(\/[^\/]+){5}/, '/css');
                }
                next();
            },

            mountFolder(connect, 'app'),

            function(req, res, next) {
                var path = req.url;
                if (connectCfg.host == req.headers.host) {
                    proxySnippet.apply(this, arguments);
                } else {
                    next();
                }
            }
        ];

        return middlewares;
    }
};


module.exports = {
    options: {
        port: connectCfg.port,
        open: false,
        hostname: connectCfg.host,
        base: gb.basePath
    },
    proxies: [{
        context: '/',
        host: connectCfg.proxy.host,
        port: connectCfg.proxy.port,
        https: false,
        xforward: false,
        changeOrigin: true,
        headers: {},
        hideHeaders: ['x-removed-header']
    }],

    develop: {
        options: {
            base: {
                path: gb.basePath,
                options: {}
            },
            debug: false,
            middleware: connectCfg.middleware,
            livereload: false
        }
    },
    dist: {
        options: {
            base: gb.destPath,
            livereload: false,
            open: false,
            middleware: connectCfg.middleware,
        }
    }
};
