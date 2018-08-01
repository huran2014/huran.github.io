'use strict'
const path = require('path');
const utils = require('./utils');
const config = require('../config');
const vueLoaderConfig = require('./vue-loader.conf');
// 获得入口js文件
var entries = utils.getMultiEntry('./src/' + config.confName + '/*.js');

function resolve(dir) {
    return path.join(__dirname, '..', dir)
}

module.exports = {
    context: path.resolve(__dirname, '../'),
    entry: entries,
    output: {
        path: config.build.assetsRoot,
        filename: '[name].js',
        publicPath: process.env.NODE_ENV === 'production' ?
            config.build.assetsPublicPath : config.dev.assetsPublicPath
    },
    resolve: {
        extensions: [
            '.js', '.vue', '.css', '.json'
        ],
        alias: {
            'vue$': 'vue/dist/vue.esm.js', //$符号指精确匹配，路径和文件名要详细
            '@': resolve('src'), // resolve('src')指的是项目根目录中的src文件夹目录，导入文件的时候路径可以这样简写 import somejs from "@/some.js"就可以导入指定文件
        }
    },
    module: {
        rules: [{
            // 解析.vue文件
            test: /\.vue$/,
            loader: 'vue-loader',
            options: vueLoaderConfig
        }, {
            // 将下面文件夹中的代码进行解析（es6）
            test: /\.js$/,
            loader: 'babel-loader',
            include: [resolve('src'), resolve('node_modules/webpack-dev-server/client')]
        }, {
            // 进行代码检测
            test: /\.js$/,
            loader: 'eslint-loader',
            exclude: [path.resolve(__dirname, '../src/assets/js/plugins')],
            options: {
                configFile: '.eslintrc.json',
                failOnError: true,
                quiet: true
            }
        }, {
            // 将图片进行base64转化，小于10000 的图片转 base64
            test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
            loader: 'url-loader',
            options: {
                limit: 10000,
                name: utils.assetsPath('img/[name].[ext]')
            }
        }, {
            // 音频 视频类文件
            test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
            loader: 'url-loader',
            options: {
                limit: 10000,
                name: utils.assetsPath('media/[name].[ext]')
            }
        }, {
            // 字体
            test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
            loader: 'url-loader',
            options: {
                limit: 10000,
                name: utils.assetsPath('fonts/[name].[ext]')
            }
        }]
    }
}
