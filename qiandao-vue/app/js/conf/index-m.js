define('conf/index-m', function(require, exports, module) {
    // 类库
    window.$ = require('zepto');
    window.Vue = require('vue');

    var vScroll = require('lib/vue/vue-scroll');
    var router = require('mods/mobile/router');

    // 业务
    // 布局模块
    require('mods/mobile/layout');
    // 签到
    require('mods/mobile/sign');
    // 签到成功
    require('mods/mobile/signSuccess');
    // 签到日历
    require('mods/mobile/signCalendar');
    // 主页面
    require('mods/mobile/main');


    Vue.use(vScroll);
    Vue.config.devtools = false;
    Vue.config.silent = false;
    Vue.config.productionTip = false;
    new Vue({
        el: '#page-sign',
        router: router
    });
});
