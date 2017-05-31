define('comp/mobile/main', function(require, exports, module) {
    var router = require('mods/mobile/router');
    var comp = Vue.component({
        created: function() {
            console.log(1);

            console.log(router.currentRoute.path);
            if (router.currentRoute.path == '/') {
                router.replace({
                    path: '/main'
                });
            }
        }
    });
    exports.module = comp;
});
