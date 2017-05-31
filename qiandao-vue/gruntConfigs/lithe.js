var path = require('path');
var gb = require('./config').global;
var configFile = require(gb.basePath + '/js/config.js');

var destConfPath = gb.destPath + '/js/conf/';
var baseConfPath = gb.basePath + '/js/conf/';
var tpl = {
    options: {
        basepath: gb.basePath + '/js/',
        alias: configFile.alias
    },
    files: {}
};

tpl.files[destConfPath] = baseConfPath;

module.exports = {
    tpl: tpl
};
