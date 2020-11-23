<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">图书管理系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username" >
                    <el-input v-model="param.user_name" placeholder="username" maxlength="30" show-word-limit>
                        <el-button slot="prepend"  type="primary" icon="el-icon-user"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="password"
                        v-model="param.user_password"
                        maxlength="30"
                        
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend"  type="primary" icon="el-icon-key"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <div class="register-btn">
                    <el-button type="primary" @click="register()">当场注册</el-button>
                </div>            
            </el-form>
        </div>
    </div>
</template>

<script>
import Vue from "vue";
export default {
    data: function() {
        return {
            param: {
                user_name: '',
                user_password: '',
            },
            rules: {
                user_name: [{ required: true, message: '请输入登录ID', trigger: 'blur' }],
                user_password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            },
        };
    },
    methods: {
        submitForm() {
            this.$refs.login.validate(valid => {
                if (valid) {

                     Vue.axios({
                        method: "post",
                        url: "http://10.10.102.142:8080/user/login",
                        data: this.param,
                        headers: {
                        "Content-Type": "application/json; charset=UTF-8",
                        },
                    })
                    .then((response) => {
                        console.log(response);
                    // 这要先赋值临时name才能赋值成功，很怪
                    let name = response.data.object.user_name;
                    window.sessionStorage.setItem('ms_username', name);
                    let userId = response.data.object.user_id;   
                    window.sessionStorage.setItem('ms_userid', userId);
                    let userType = response.data.object.user_identity_id;
                    if(userType == 1){
                        this.$router.push('/admin');
                    }else if(userType == 0){
                       this.$router.push('/user');
                    }                            
                        this.$message.success('登录成功'); 
                    })
                    .catch((error) => {
                        this.$message.error("登录失败，请重新登录");
                        console.log(error);
                    });
                   
                } else {
                    this.$message.error('请输入账号和密码');
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        register(){
             this.$router.push('/register');
        }
    },
};
</script>

<style scoped>
.login-wrap {
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: relative;
    left: 38%;
    top: 100px;
    width: 350px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.register-btn {
    text-align: center;
}
.register-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>