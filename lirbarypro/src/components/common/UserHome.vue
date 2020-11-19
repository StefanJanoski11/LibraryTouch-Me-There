<template>
    <div class="wrapper">
    <!--这里才是首页根目录-->
    <!--这是头边框-->
        <v-head></v-head>
        <!--这是侧边栏-->
        <v-sidebar></v-sidebar>
        <div class="content-box" :class="{'content-collapse':collapse}">           
            <div class="content">
                <transition name="move" mode="out-in">
                    <keep-alive :include="tagsList">
                        <router-view></router-view>
                    </keep-alive>
                </transition>
                <el-backtop target=".content"></el-backtop>
            </div>
        </div>
    </div>
</template>

<script>
import vHead from './UserHeader.vue';
import vSidebar from './UserSidebar.vue';
import bus from './bus';
export default {
    name: 'userhome',
    data() {
        return {
            tagsList: [],
            collapse: false
        };
    },
    components: {
        vHead,
        vSidebar
    },
    created() {
        bus.$on('collapse-content', msg => {
            this.collapse = msg;
        });
    }
};
</script>
