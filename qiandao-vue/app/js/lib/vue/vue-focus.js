/**
 * @fileoverview focus插件 for vuejs-2.0，From：https://github.com/simplesmiler/vue-focus
 * @author liyifei@le.com
 * @date 2017/04
 */
define('lib/vue/vue-focus', function(require, exports, module) {

    'use strict';

    var Vue = require('vue');
    Vue = 'default' in Vue ? Vue['default'] : Vue;

    var version = '2.1.0';

    var compatible = (/^2\./).test(Vue.version);
    if (!compatible) {
        Vue.util.warn('VueFocus ' + version + ' only supports Vue 2.x, and does not support Vue ' + Vue.version);
    }

    var focus = {
        inserted: function(el, binding) {
            if (binding.value) el.focus();
            else el.blur();
        },

        componentUpdated: function(el, binding) {
            if (binding.modifiers.lazy) {
                if (Boolean(binding.value) === Boolean(binding.oldValue)) {
                    return;
                }
            }

            if (binding.value) el.focus();
            else el.blur();
        },
    };

    var mixin = {
        directives: {
            focus: focus,
        },
    };

    exports.version = version;
    exports.focus = focus;
    exports.mixin = mixin;

});
