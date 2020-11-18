import Vue from 'vue'
import Router from 'vue-router'

import MainIndex from '../views/MainIndex'
import Login from '../views/login.vue'
import Sign from '../views/Sign.vue'

Vue.use(Router);

export default new Router({
    routes: [{
        path: '/',
        component: MainIndex,
        name: "Home"
    }, {
        path: '/login',
        component: Login
    },{
        path: '/sign',
        component: Sign
    }],
    mode: "history" // mode 设置为history ，去掉地址栏上的 # 号
});