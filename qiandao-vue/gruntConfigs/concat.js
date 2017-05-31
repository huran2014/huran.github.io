 var gb = require('./config').global;
 module.exports = {
     config: {
         files: {
             [gb.tempPath + '/js/lithe.js']: [gb.basePath + '/js/lithe.js', gb.basePath + '/js/config.js']
         }
     }
 }
