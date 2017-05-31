var banner = '/* <%=pkg.name%> | <%=pkg.description%> | vserion <%=pkg.version%>*/\r\n';
var gb = require('./config').global;
module.exports = {
    options: {
        mangle: {
            except: ['require']
        },
        banner: banner
    },
    lithe: {
        files: [{
            expand: true,
            cwd: gb.destPath + '/js/conf/',
            src: '**/*.js',
            dest: gb.destPath + '/js/conf' //输出到此目录下
        }]
    },
    config: {
        files: {
            [gb.tempPath + '/js/lithe.js']: [gb.tempPath + '/js/lithe.js']
        }
    }
}
