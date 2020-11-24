import Vue from 'vue';
import Router from 'vue-router';
import Home from '../components/common/Home.vue';
import UserHome from '../components/common/UserHome.vue';
import Dashboard from '../components/page/Dashboard.vue';
import Userlist from '../components/page/Userlist.vue';
import Login from '../components/page/Login.vue';
import Register from '../components/page/Register.vue';
import NotFound from '../components/page/404.vue';
import History from '../components/page/History.vue';
import UserEdit from '../components/page/UserEdit.vue';
import Rent from '../components/page/Rent.vue';
import Bookshelf from '../components/page/Bookshelf.vue';
import UserLibrary from '../components/page/UserLibrary.vue';
import ManagerLibrary from '../components/page/ManagerLibrary.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [{
            path: '/admin',
            name: 'admin',
            redirect: '/admin/dashboard'
        },
        {
            path: '/admin',
            component: Home,
            children: [{
                    path: 'dashboard',
                    component: Dashboard,
                    meta: { title: '后台首页' }
                },
                {
                    path: 'userlist',
                    component: Userlist,
                    meta: { title: '用户列表' }
                },
                {
                    path: 'rent',
                    component: Rent,
                    meta: { title: '图书借阅与归还' }
                },
                {
                    path: 'library',
                    component: ManagerLibrary,
                    meta: { title: '书库' }
                }
            ]
        },
        {
            path: '/user',
            redirect: '/user/library'
        },
<<<<<<< HEAD
=======

>>>>>>> 01ef36be4ba6600be54e29d1442ecea8b1b0aa56
        {
            path: '/user',
            component: UserHome,
            children: [{
                    path: 'library',
                    component: UserLibrary,
                    meta: { title: '图书库' }
                },
                {
                    path: 'bookshelf',
                    component: Bookshelf,
                    meta: { title: '我的书架' }
                },
                {
                    path: 'edit',
                    component: UserEdit,
                    meta: { title: '信息修改' }
                },
                {
                    path: 'history',
                    component: History,
                    meta: { title: '借阅记录' }
                }

            ]
        },
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            meta: { title: '登录' }
        },
        {
            path: '/register',
            name: 'register',
            component: Register,
            meta: { title: '注册' }
        },
        {
            path: '/404',
            component: NotFound,
            meta: { title: '无法访问' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});