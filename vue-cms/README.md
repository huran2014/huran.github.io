# vue-cms

 一个通过 nodejs && expressjs && webpack && vue 搭建的网站。

参考的是vue-cli脚手架，去掉了其中不必要的功能

有兴趣的可以安装vue-cli看看里面的实现

---

## 安装说明

``` bash
# install dependencies
cd vue-cms
npm install -d

```
---

## 命令行工具
``` bash
# 启动开发环境，浏览器访问localhost:80
npm run dev

# 打包构建前端代码
npm run build

#启动生产环境，浏览器访问localhost:80
npm run prod

```
---

##代码结构介绍
```
├─build # 构建脚本目录
│   ├─build.js # 打包配置
│   ├─dev-server.js # 本地服务器配置
│   ├─utils.js # 文件路径、css loader一些公共的处理方法
│   ├─vue-loader.config.js # vue loader配置
│   ├─webpack.base.conf.js # 公共webpack的配置
│   ├─webpack.dev.conf.js # 开发环境webpack的配置
│   ├─webpack.prod.conf.js # 先上环境webpack的配置
├─config # 构建配置目录
├─dist # 打包后文件目录
│   ├─static # 静态资源
│       ├─css
│       ├─js
│   ├─*.html # html文件
├─src # 源码目录
│   ├─assets # 资源目录
│       ├─css
│           ├─global # 全局css
│           ├─plugins # 第三方插件
│       ├─font # 字体,svg等
│       ├─images # 图片
│       ├─js
│           ├─global # 全局js
│           ├─plugins # 第三方插件
│   ├─components # vue组件目录
│   ├─conf # 页面js入口
│   ├─mods # 页面级vue组建
│   ├─router # vue路由
│   ├─html # html页面入口
├─.babelrc # 设置转码的规则和插件
├─.eslintrc.json # eslint 脚本校验配置
├─.gitignore # git提交时忽略的文件
├─.postcssrc.js # 改善css代码质量配置
├─package.json # npm 依赖，脚本 cli 命令配置
├─README.md #项目介绍
```
---
