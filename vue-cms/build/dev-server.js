const config = require('../config');
if (!process.env.NODE_ENV) process.env.NODE_ENV = JSON.parse(config.dev.env.NODE_ENV);
const path = require('path');
const express = require('express');
const webpack = require('webpack');
const opn = require('opn');
console.log(process.env.NODE_ENV);
const webpackConfig = process.env.NODE_ENV === 'development' ?
    require('./webpack.dev.conf') : require('./webpack.prod.conf');
const proxyMiddleware = require('http-proxy-middleware')
const webpackDevMiddleware = require('webpack-dev-middleware');
const webpackHotMiddleware = require('webpack-hot-middleware');

const port = process.env.PORT || config.dev.port;

// 采用express进行运行服务
const server = express();
const compiler = webpack(webpackConfig);

const devMiddleware = webpackDevMiddleware(compiler, {
    publicPath: '/',
    stats: {
        colors: true,
        chunks: false
    }
})

const hotMiddleware = webpackHotMiddleware(compiler);

// 当模版发生变化时，进行刷新页面
compiler.plugin('compilation', function(compilation) {
    compilation.plugin('html-webpack-plugin-after-emit', function(data, cb) {
        hotMiddleware.publish({
            action: 'reload'
        })
        cb();
    });
})

// handle fallback for HTML5 history API
server.use(require('connect-history-api-fallback')());

if (process.env.NODE_ENV === 'development') {
    const context = config.dev.context;
    //需要定义好接口/family
    // 两种写法
    // 多路径匹配的时候，推荐第一种
    server.use(proxyMiddleware(context, config.dev.proxyOptions));

    // server.use('/family', proxyMiddleware(config.dev.proxyTable["/family"]));
}


server.use(devMiddleware);
server.use(hotMiddleware);

// serve pure static assets
const staticPath = path.posix.join(config.dev.assetsPublicPath, config.dev.assetsSubDirectory);
server.use(staticPath, express.static('./src/assets'));

module.exports = server.listen(port, function(err) {
    if (err) {
        console.log(err);
        return;
    }
    const uri = config.dev.host + ':' + port;
    console.log('Listening at ' + uri + '\n');

    // opn(uri);
})
