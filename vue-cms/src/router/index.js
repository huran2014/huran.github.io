import Vue from 'vue';
import Router from 'vue-router';
// import HelloWorld from '@/components/HelloWorld';
import App from '../mods/App';

import HelloWorld1 from '@/components/HelloWorld.1';

import api from '../server/index';
// 懒加载方法
// const HelloWorld = r => require.ensure([], r(require('../components/HelloWorld')), 'HelloWorld');

Vue.use(Router);
Vue.use(api);

export default new Router({
    routes: [{
        path: '/',
        component: App
    }, {
        path: '/hello/:id',
        component: HelloWorld1
    }]
});
