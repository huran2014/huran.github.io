/**
 * @fileoverview lithe配置文件
 * @author liuluying@le.com
 */
(function(global, undef) {
    // var basepath = 'http://' + 'js.letvcdn.com';

    var router = {
        //routeStart
        //routeEnd
    }
    var mod = {
        // basepath: basepath + '/js/',
        basepath: '',
        alias: {
            'jquery': 'lib/chaos/jquery',
            'zepto': 'lib/chaos/zepto',
            'vue': 'lib/vue/vue',
            'vue-router': 'lib/vue/vue-router',
            'ajax': 'lib/chaos/ajax',
            'lebi': 'mods/global/lebi'
        },
        router: router,
        middleware: function(id, path) {
            var prefix = 'dist/js/',
                currUrl;
            if (/\.js$/.test(id)) {
                currUrl = router[prefix + id];
            } else {
                currUrl = router[prefix + id + '.js'];
            }
            if (currUrl) {
                path = basepath + '/';
                id = currUrl.replace(path, '');
            }
            return {
                id: id,
                path: path
            }
        }
    };
    if (global.define && typeof window) {
        define('config', function() {
            return mod;
        });
    } else {
        module.exports = mod;
    }
})(this);