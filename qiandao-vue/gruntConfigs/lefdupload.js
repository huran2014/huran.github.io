var gb = require('./config').global;
var configFile = require(gb.basePath + '/js/config.js');
module.exports = {
    options: {
        url: 'http://upload2.lelecdn.com:8000/single_upload_tool.php',
        request: {
            parse: true,
            multipart: true,
            host: 'upload2.lelecdn.com',
            port: '8000',
            //'path': '/uploadfile.php',
            path: '/single_upload_tool.php',
            method: 'POST',
            headers: {
                'Cookie': 'upload_username=jsgroup;upload_token=71837b4c3e169efe5334ac4b94a29456;PHPSESSID=1359d96217507e99275b5a36fc1e0256',
                'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                'Accept-Encoding': 'gzip,deflate,sdch',
                'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6',
                'Content-Type': 'multipart/form-data'
            }
        },
        postData: {
            username: 'liubin1',
            md5str: 'c33b33a211bea60b34f0bc7ee81b8802',
            channel: 'js',
            single_upload_submit: 'ok'
        }
    },
    config: {
        files: [{
            src: ['./js/lithe.js'],
            cwd: gb.tempPath,
            expand: true
        }]
    },
    css: {
        files: [{
            src: ['**/*.css'],
            cwd: gb.destPath,
            expand: true,


        }],
        options: {
            remote: {
                folder: gb.projectName
            }

        }
    },

    js: {
        files: [{
            src: ['**/*.js'],
            cwd: gb.destPath,
            expand: true,


        }],
        options: {
            remote: {
                folder: gb.projectName

            },
            router: {
                startTag: '//routeStart',
                endTag: '//routeEnd',
                currRouter: configFile.router || {},
                file: gb.tempPath + '/js/lithe.js',
                tasks: ['uglify:config', 'lefdupload:config']
            }

        }
    },

    /*测试机配置*/
    testerconfig: {
        files: [{
            src: ['./js/lithe.js'],
            cwd: gb.tempPath,
            expand: true
        }],
        options: {
            url: 'http://127.0.0.1:8084/recive',
            request: {
                parse: true,
                multipart: true,
                host: '127.0.0.1',
                port: '8084',
                path: '/recive',
                method: 'POST',
                headers: {
                    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                    'Accept-Encoding': 'gzip,deflate,sdch',
                    'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6',
                    'Content-Type': 'multipart/form-data'
                }
            },
            postData: {
                project: gb.projectName,
                path: '/js/mod/',
                author: 'liuluying@le.com'
            },
            remote: {
                folder: gb.projectName
            }
        }
    },
    tester: {
        files: [{
            src: ['**/*.js', '**/*.css'],
            cwd: gb.destPath,
            expand: true,
        }],
        options: {
            url: 'http://127.0.0.1:8084/recive',
            request: {
                parse: true,
                multipart: true,
                host: '127.0.0.1',
                port: '8084',
                //'path': '/uploadfile.php',
                path: '/recive',
                method: 'POST',
                headers: {
                    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                    'Accept-Encoding': 'gzip,deflate,sdch',
                    'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6',
                    'Content-Type': 'multipart/form-data'
                }
            },
            postData: {
                project: gb.projectName,
                path: '/js/mod/',
                author: 'liuluying@le.com'
            },
            remote: {
                folder: gb.projectName

            },
            router: {
                startTag: '//routeStart',
                endTag: '//routeEnd',
                currRouter: configFile.router || {},
                file: gb.tempPath + '/js/lithe.js',
                tasks: ['uglify:config', 'lefdupload:testerconfig']
            }
        }

    }
}
