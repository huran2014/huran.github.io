var gb = require('./config').global;
module.exports = {
    minify: {
        expand: true,
        cwd: gb.basePath + '/css/conf/',
        src: ['*.css', '!*.min.css'],
        dest: gb.destPath + '/css/conf',
        ext: '.css'
    }
}
