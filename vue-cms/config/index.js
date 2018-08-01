'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path');

module.exports = {
    htmlName: 'html',
    confName: 'conf',
    dev: {
        env: {
            NODE_ENV: '"development"'
        },

        // 定义基本路径
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        // 代理配置
        // 如果不配置patnRewrite的时候，/family映射为http://api.wechat.le.com/family
        // 配置patnRewrite的话，/family会变为你配置的路径，为空就是去掉/family
        // 如下两种
        // proxyTable: {
        //     '/family': {
        //         target: 'http://api.wechat.le.com', // 接口域名
        //         secure: false, // 如果是https接口，需要配置这个参数
        //         changeOrigin: true, //是否跨域
        //         pathRewrite: {
        //             // '^/family': '' //需要rewrite的,
        //         }
        //     }
        // },
        // proxyTable: {
        //     '/family': {
        //         target: 'http://api.wechat.le.com/family', // 接口域名
        //         secure: false, // 如果是https接口，需要配置这个参数
        //         changeOrigin: true, //是否跨域
        //         pathRewrite: {
        //             '^/family': '' //需要rewrite的,
        //         }
        //     }
        // },

        //
        context: [
            '/family'
        ],
        proxyOptions: {
            target: 'http://api.wechat.le.com/', // 接口域名
            secure: false, // 如果是https接口，需要配置这个参数
            changeOrigin: true, //是否跨域
            pathRewrite: {
                // '^/family': '' //需要rewrite的,
            }
        },

        // 本地服务配置
        host: 'localhost',
        port: 80,
        autoOpenBrowser: false,
        errorOverlay: true,
        notifyOnErrors: true,
        poll: false,

        // sourcemap是为了解决开发代码与实际运行代码不一致时帮助我们debug到原始开发代码的技术
        devtool: 'cheap-module-eval-source-map',

        cssSourceMap: false
    },

    build: {
        env: {
            NODE_ENV: '"production"'
        },

        assetsRoot: path.resolve(__dirname, '../dist'),
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',

        productionSourceMap: true,

        // 压缩
        productionGzip: false,
        productionGzipExtensions: ['js', 'css']
    }
};
