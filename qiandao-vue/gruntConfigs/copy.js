var gb = require('./config').global;
module.exports = {
    html: {
        files: [{
            expand: true,
            src: ['./**/*.html'],
            dest: gb.destPath,
            cwd: gb.basePath
        }]
    },
    //mincss: {
    //    files: [{
    //        expand: true,
    //        src: ['./**/*.min.css'],
    //        dest: 'dist/css/',
    //        cwd: 'app/css/'
    //    }]
    //},
    js: { //复制jquery，以后移除
        expand: true,
        src: ['./lithe.js'],
        dest: gb.destPath + '/js/',
        cwd: gb.tempPath+ '/js/'
    }
}
