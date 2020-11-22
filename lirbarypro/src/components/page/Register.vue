<template>
  <div class="login-container">
    <el-form
      ref="form"
      :model="form"
      status-icon
      :rules="rules"
      label-width="100px"
      class="login-form demo-ruleForm"
      label-position="righ"
    >
      <h2 class="login-title">注册</h2>

      <el-form-item label="ID" placeholder="ID">
        <el-input v-model="form.ID" maxlength="30" show-word-limit> </el-input>
      </el-form-item>

      <el-form-item label="姓名" placeholder="姓名">
        <el-input v-model="form.name" maxlength="30" show-word-limit>
        </el-input>
      </el-form-item>

      <el-form-item label="邮箱" placeholder="邮箱">
        <el-input v-model="form.eamil" maxlength="30"> </el-input>
      </el-form-item>

      <el-form-item prop="phone" label="电话" placeholder="手机号码">
        <el-input v-model="form.phone"> </el-input>
        <el-button @click="getVerrifyCode">{{ form.btnTitle }}</el-button>
      </el-form-item>

      <el-form-item prop="telCode" label="短信验证码" placeholder="验证码">
        <el-input v-model="form.verifyCode"> </el-input>
      </el-form-item>

      <el-form-item label="输入密码" placeholder="输入密码" prop="password">
        <el-input
          type="password"
          v-model="form.password"
          autocomplete="off"
          maxlength="30"
          show-password
        >
        </el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="password2">
        <el-input
          type="password"
          v-model="form.password2"
          autocomplete="off"
          maxlength="30"
        ></el-input>
      </el-form-item>

      <div class="register-btn">
        <el-button type="primary" @click="onSubmit()">注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    // 是否包含一位大写字母
    const reg = /(?=.*[A-Z])/;
    // 是否包含一位数字
    const regNumber = /(?=.*[\d])/;
    // 是否包含一位小写字母
    const regLetter = /(?=.*[a-z])/;
    // 是否包含一位特殊字符
    const regCharacter = /(?=.*[`~!@#$%^&*()_\-+=<>?:"{}|,.\/;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、])/;
    // 校验新密码
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("新密码不能为空！请重新输入"));
      } else {
        if (value.length > 16) {
          callback(new Error("密码长度不超过16个字符。"));
        } else if (value.length < 8) {
          callback(new Error("密码长度不低于8个字符。"));
        } else {
          if (!/^[a-zA-Z\d]{1}/.test(value)) {
            callback(new Error("密码必须以英文字母或数字开头！"));
          } else {
            if (!regNumber.test(value)) {
              callback(
                new Error("密码必须由数字，大小写英文字母和特殊字符组成!")
              );
            } else if (!regLetter.test(value)) {
              callback(
                new Error("密码必须由数字，大小写英文字母和特殊字符组成!")
              );
            } else if (!regCharacter.test(value)) {
              callback(
                new Error("密码必须由数字，大小写英文字母和特殊字符组成!")
              );
            } else if (!reg.test(value)) {
              callback(
                new Error("密码必须由数字，大小写英文字母和特殊字符组成!")
              );
            } else {
              callback();
            }
          }
        }
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.form.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      form: {
        name: "",
        ID: "",
        eamil: "",
        phone: "", //手机号
        verifyCode: "", //验证码
        btnTitle: "获取验证码",
        password: "",
        password2: "",
      },
      rules: {
        password: [{ validator: validatePass, trigger: "blur" }],
        password2: [{ validator: validatePass2, trigger: "blur" }],
        phone: [
          {
            required: true,
            pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,
            message: "目前只支持中国大陆的手机号码",
            trigger: "change", //输入时就会验证
          },
        ],
      },
    };
  },
  props: {
    type: {
      type: String,
      default: "text",
    },
    placeholder: String,
    value: String,
    name: String,
    btnTitle: String, //input框中的文字
  },
  methods: {
    onSubmit() {
      console.log("提交成功--------------------------------------------------记得删掉");
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios({
            method: "post",
            url: "",
            data: this.$qs.stringify(this.form),
            headers: {
              "Content-Type":
                "application/x-www-form-urlencoded; charset=UTF-8",
            },
          })
            .then((response) => {
              console.log(response+"--------------------------------------------------记得删掉");
              if (
                response.data.ID == "admin" &&
                response.data.password == "123qwer@"
              ) {
                this.$router.push("/");
              }
            })
            .catch((error) => {
              console.log(error+"--------------------------------------------------记得删掉");
            });
        } else {
          console.log("获取失败，请重试！--------------------------------------------------记得删掉");
          return false;
        }
      });
    },
    getVerrifyCode() {
      //先判断phone
      this.$refs.form.validate((valid) => {
        if (valid) {
          alert("submit!--------------------------------------------------记得删掉");
          //axios传phone
          this.axios({
            method: "get",
            url: "",
            data: this.$qs.stringify(this.form.phone),
            headers: {
              "Content-Type":
                "application/x-www-form-urlencoded; charset=UTF-8",
            },
          })
            .then((response) => {
              console.log(response+"--------------------------------------------------记得删掉");
              this.form.verifyCode = response.data;
              //获取后计时

              let time = 60;
              let timer = setInterval(() => {
                if (time == 0) {
                  clearInterval(timer);
                  this.btnTitle = "重新获取验证码";
                } else {
                  this.btnTitle = time + "秒后重试";
                  time--;
                }
              }, 1000);
            })
            .catch((error) => {
              console.log(error+"--------------------------------------------------记得删掉");
            });
        } else {
          console.log("获取失败，请重试！--------------------------------------------------记得删掉");
          return false;
        }
      });
    },
  },
};
</script>

<style >
.login-form {
  width: 350px;
  margin: 160px auto; /* 上下间距160px，左右自动居中*/
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  padding: 30px;
  border-radius: 20px; /* 圆角 */
}

.login-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("../../assets/img/login-bg.jpg");
  background-size: 100%;
}
.register-btn {
  text-align: center;
}
.register-btn button {
  width: 60%;
  height: 36px;
  margin-bottom: 10px;
}
.login-title {
  color: #303133;
  text-align: center;
}

.button {
  background-color: thistle;
  position: absolute;
  left: 1100px;
  top: 560px;
}
</style> -->
</style>