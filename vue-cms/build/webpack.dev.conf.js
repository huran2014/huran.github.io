'use strict'
const utils = require('./utils')
const webpack = require('webpack')
const config = require('../config')
const merge = require('webpack-merge')
const path = require('path')
const baseWebpackConfig = require('./webpack.base.conf')
const HtmlWebpackPlugin = require('html-webpack-plugin')

const HOST = process.env.HOST
const PORT = process.env.PORT && Number(process.env.PORT)

const devWebpackConfig = merge(baseWebpackConfig, {
    module: {
        rules: utils.styleLoaders({
            sourceMap: config.dev.cssSourceMap,
            usePostCSS: true
        })
    },
    // 快速定位到bug
    devtool: config.dev.devtool,

    plugins: [
        new webpack.DefinePlugin({
            'process.env': config.dev.env
        }),

        // 热更新
        new webpack.HotModuleReplacementPlugin(),

        new webpack.NamedModulesPlugin(),

        // webpack 进程遇到错误代码将不会退出
        // new webpack.NoEmitOnErrorsPlugin()
    ]
})

// 多页面html生成
var pages = utils.getMultiEntry('./src/' + config.htmlName + '/*.html');
for (var pathname in pages) {
    // 配置生成的html文件，定义路径等
    var conf = {
        filename: pathname + '.html',
        template: pages[pathname], // 模板路径
        chunks: [pathname, 'vendors', 'manifest'], // 每个html引用的js模块
        inject: true // js插入位置
    };
    // 需要生成几个html文件，就配置几个HtmlWebpackPlugin对象
    devWebpackConfig.plugins.push(new HtmlWebpackPlugin(conf));
}

module.exports = devWebpackConfig;
