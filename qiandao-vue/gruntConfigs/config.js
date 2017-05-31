/*
 * grunt config
 * @author liuluying@le.com
 * v1
 * */

var path = require('path');
var pkg = require('../package.json');


module.exports = {
    //全局
    global: {
        //项目名称,英文
        projectName: pkg.name || 'projectName',
        //项目开发目录
        basePath: path.resolve('/', __dirname) + '/../app/',
        //项目打包目录
        destPath: path.resolve('/', __dirname) + '/../../wish/',
        //项目缓存目录
        tempPath: path.resolve('/', __dirname) + '/../.tmp'
    },


    //connect
    connect: {
        //项目域名,将host绑到本地（127.0.0.1）
        host: 'local.sign.le.com',
        // host: '0.0.0.0',
        port: 80,

        //代理服务器
        proxy: {
            host: '10.154.157.145',
            port: 80
        },

        //路由规则
        rewrite: ''
    }
}
