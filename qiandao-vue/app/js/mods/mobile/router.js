define('mods/mobile/router', function(require, exports, module) {
    var VueRouter = require('vue-router');
    var hooks = require('mods/mobile/hooks');
    var router = new VueRouter({
        routes: [{
            path: '/sign',
            components: {
                sign: {
                    template: '<app-sign></app-sign>'
                }
            }
        }, {
            path: '/signSuccess',
            components: {
                signSuccess: {
                    template: '<app-signSuccess></app-signSuccess>'
                }
            }
        }, {
            path: '/signCalendar',
            components: {
                signCalendar: {
                    template: '<app-signCalendar></app-signCalendar>'
                }
            }
        }]
    });
    router.beforeEach(hooks);

    module.exports = router;
});
