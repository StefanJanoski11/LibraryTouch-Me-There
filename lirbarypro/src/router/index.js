import Vue from 'vue'
import Router from 'vue-router'

import MainIndex from '../views/MainIndex'
import Login from '../views/Login'
import Reader from '../views/Reader'
import RentList from '../views/RentList'

Vue.use(Router);

export default new Router({
    routes: [{
        path: '/',
        component: MainIndex,
        name: "Home"
    }, {
        path: '/login',
        component: Login
    }, {
        path: '/reader',
        component: Reader
    }, {
        path: '/rentlist',
        component: RentList
    }],
    mode: "history" // mode 设置为history ，去掉地址栏上的 # 号
});