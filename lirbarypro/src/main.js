// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import './assets/css/icon.css';
// import './components/common/directives';
import 'babel-polyfill';
import axios from 'axios'
import VueAxios from 'vue-axios'
import qs from 'qs';

Vue.prototype.$qs = qs;
Vue.prototype.$host = 'http://10.10.102.142:8080';

Vue.use(VueAxios, axios);
Vue.prototype.$axios = axios;
Vue.config.productionTip = false;
Vue.use(ElementUI, {
    size: 'small'
});

//使用钩子函数对路由进行权限跳转
// router.beforeEach((to, from, next) => {
//     const username = window.sessionStorage.getItem('ms_username');
//     document.title = `${to.meta.title} | 好果汁书室`;
//     console.log("----------------------");
//     if (to.name == 'login') { //如果进入的是login
//         next(); //直接放行
//     } else {
//         if (!username) { //在其他页面如果没有登录
//             next({ name: 'login' });
//         } else { //登录了就放行
//             next();
//         }
//     }
// });

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    render: h => h(App)
})