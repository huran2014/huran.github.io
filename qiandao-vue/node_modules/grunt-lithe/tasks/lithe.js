/*
 * grunt-lithe
 * https://github.com/xiaojue/grunt-lithe
 *
 * Copyright (c) 2013 xiaojue
 * Licensed under the MIT license.
 */

'use strict';

var lithe = require('lithe');
var tool = lithe.tool;
var litheOptions = tool.options;
var Path = require('path');
var rootpath = process.cwd();

function isJs(path) {
	return Path.extname(path) === '.js';
}

function isDirPath(path) {
	return path.lastIndexOf('/') === path.length - 1;
}

module.exports = function(grunt) {

	grunt.registerMultiTask('lithe', 'lithe for grunt plugin', function() {

		var options = this.options({
			//alias: {}
			//basepath:''
			//filter:function(){}
		});

		if (options.basepath) {
			litheOptions.basepath = Path.resolve(rootpath, options.basepath);
		}
		if (options.alias) {
			litheOptions.alias = options.alias;
		}

		this.files.forEach(function(f) {
			var src = [];
			f.src.filter(function(path) {
				if (grunt.file.exists(path)) {
					return true;
				} else {
					return false;
				}
			}).forEach(function(path) {
				if (grunt.file.isFile(path) && isJs(path)) {
					src.push({
						path: path,
						name: Path.basename(path)
					});
				} else if (grunt.file.isDir(path)) {
					grunt.file.recurse(path, function(abspath, rootdir, subdir, filename) {
						if (isJs(abspath)) {
							src.push({
								path: abspath,
								name: filename
							});
						}
					});
				}
			});
			src.forEach(function(file) {
				var conf = Path.resolve(rootpath, file.path);
				var requires = tool.findJsAllrequires(conf,[],options.filter);
				requires.push(conf);
				var str = requires.map(function(file) {
					return grunt.file.read(file);
				}).join('');

				if (isDirPath(f.dest)) {
					grunt.file.write(f.dest + file.name, str);
					grunt.log.writeln('File "' + f.dest + file.name + '" created.');
				} else {
					grunt.file.write(f.dest, str);
					grunt.log.writeln('File "' + f.dest + '" created.');
				}
			});
		});

	});

};

