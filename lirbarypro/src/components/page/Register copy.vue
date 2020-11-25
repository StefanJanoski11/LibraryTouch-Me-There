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

      <input type="text" hidden v-model="submit_isAbled"/>

      <el-form-item label="姓名" placeholder="姓名" prop="user_name">
        <el-input v-model="form.user_name" maxlength="30" show-word-limit>
        </el-input>
      </el-form-item>

      <el-form-item label="邮箱" placeholder="邮箱" prop="user_email">
        <el-input v-model="form.user_email" maxlength="30"> </el-input>
      </el-form-item>

      <el-form-item label="性别" prop="user_sex">
        <el-select
          style="width: 100%"
          v-model="form.user_sex"
          placeholder="选择性别"
        >
          <el-option label="男" value="1"></el-option>
          <el-option label="女" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="出生年月" required>
        <el-form-item prop="user_birthday">
          <el-date-picker
            type="date"
            placeholder="选择年月"
            v-model="form.user_birthday"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
      </el-form-item>

      <el-form-item prop="user_address" label="地址" placeholder="地址">
        <el-input v-model="form.user_address" maxlength="30"> </el-input>
      </el-form-item>

      <el-form-item prop="user_phone" label="手机" placeholder="手机号码">
        <el-input v-model="form.user_phone" @blur="checkPhoneExist()">
        </el-input>
        <el-button :disabled="msg_isAbled" @click="getVerrifyCode">{{
          form.btnTitle
        }}</el-button>
      </el-form-item>

      <el-form-item prop="telCode" label="短信验证码" placeholder="验证码">
        <el-input v-model="form.verifyCode"> </el-input>
      </el-form-item>

      <el-form-item
        label="输入密码"
        placeholder="输入密码"
        prop="user_password"
      >
        <el-input
          type="password"
          v-model="form.user_password"
          autocomplete="off"
          maxlength="30"
          show-password
        >
        </el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="checkPassword">
        <el-input
          type="password"
          v-model="form.checkPassword"
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
      } else if (value !== this.form.user_password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      submit_isAbled : false,//?????
      msg_isAbled: true,
      form: {
        user_name: "",
        user_email: "",
        user_sex: "",
        user_birthday: "",
        user_phone: "", //手机号
        verifyCode: "", //验证码
        user_address: "",
        user_password: "",
        checkPassword: "",
        btnTitle: "获取验证码"
      },
      realVerifyCode: "",
      rules: {
        verifyCode: [{ required: true, message: "输入名字", trigger: "blur" }],
        user_name: [
          { required: true, message: "输入名字", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur"
          }
        ],
        user_password: [
          { validator: validatePass, trigger: "blur" },
          { required: true, message: "输入密码", trigger: "blur" }
        ],
        checkPassword: [
          { validator: validatePass2, trigger: "blur" },
          { required: true, message: "输入确认密码", trigger: "blur" }
        ],
        user_address: [
          {
            required: true,
            message: "请输入地址",
            trigger: "blur"
          }
        ],
        user_email: [
          { type: "email", required: true, trigger: "change" },
          {
            required: true,
            message: "请输入邮箱地址",
            trigger: "blur"
          }
        ],
        user_sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        user_birthday: [
          {
            type: "date",
            format: "yyyy-MM-dd",
            required: true,
            message: "请选择日期",
            trigger: "blur"
          }
        ],
        user_phone: [
          {
            required: true,
            pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,
            message: "请输入中国大陆的手机号码",
            trigger: "change" //输入时就会验证
          },
          {
            required: true,
            message: "请输入手机号",
            trigger: "blur"
          }
        ]
      },
      verifyCode: null
    };
  },
  props: {
    type: {
      type: String,
      default: "text"
    },
    placeholder: String,
    value: String,
    name: String,
    btnTitle: String //input框中的文字
  },
  methods: {
    checkUsernameExist() {
      me=this;
      this.axios({
        method: "get",
        url:  this.$host+"/user/checkUsernameIsExist",
        params: {
          user_name: this.form.user_name
        }
      })
        .then(response => {
          if (response.data.code == 406) {
            console.log(me);
            me.submit_isAbled = false;
            alert("已经存在用户注册使用该用户名");
          } else {
            me.submit_isAbled = true;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    checkPhoneExist() {
      this.axios({
        method: "get",
        url:  this.$host+"/user/checkPhoneIsExist",
        params: {
          user_phone: this.form.user_phone
        }
      })
        .then(response => {
          if (response.data.code == 406) {
            this.msg_isAbled = true;
            alert("已经存在用户注册使用该手机号码");
          } else {
            this.msg_isAbled = false;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    onSubmit() {
      this.$refs.form.validate(valid => {
        if (true && submit_isAbled) {
          console.log(this.realVerifyCode);
          if (this.realVerifyCode == this.form.verifyCode) {
            console.log(this.form);
            this.axios({
              method: "post",
              url:  this.$host+"/user/register",
              params: {
                user_name: this.form.user_name,
                user_email: this.form.user_email,
                user_sex: this.form.user_sex,
                user_birthday: this.form.user_birthday,
                user_phone: this.form.user_phone, //手机号
                verifyCode: this.form.verifyCode, //验证码
                user_address: this.form.user_address, //地址
                user_password: this.form.user_password,
                checkPassword: this.form.checkPassword
              },
              headers: {
                "Content-Type":
                  "application/x-www-form-urlencoded; charset=UTF-8"
              }
            })
              .then(response => {
                console.log(
                  response +
                    "注册成功-------------------------------------------------记得删掉"
                );
                this.open();
              })
              .catch(error => {
                console.log(
                  error +
                    "--------------------------------------------------记得删掉"
                );
              });
          } else {
            alert(
              "验证码错误--------------------------------------------------记得删掉"
            );
          }
        } else {
          console.log(
            "获取失败，请重试！--------------------------------------------------记得删掉"
          );
          return false;
        }
      });
    },
    open() {
      this.$alert(
        "您已经注册成功，点击确认可返回登录。",
        "好果汁图书馆欢迎您",
        {
          confirmButtonText: "确定",
          callback: action => {
            this.$router.push("/");
          }
        }
      );
    },
    getVerrifyCode() {
      //先判断user_phone
      this.$refs.form.validate(valid => {
        if (true) {
          //axios传user_phone
          this.axios({
            method: "get",
            url:  this.$host+"/message/send",
            params: {
              user_phone: this.form.user_phone
            },
            headers: {
              "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
            }
          })
            .then(response => {
              console.log(response.data.object);
              this.realVerifyCode = response.data.object;
              //获取后计时
              let time = 60;
              let timer = setInterval(() => {
                if (time == 0) {
                  clearInterval(timer);
                  this.form.btnTitle = "重新获取验证码";
                  this.msg_isAbled = false;
                } else {
                  this.form.btnTitle = time + "秒后重试";
                  this.msg_isAbled = true;
                  time--;
                }
              }, 1000);
            })
            .catch(error => {
              console.log(
                error +
                  "--------------------------------------------------记得删掉"
              );
            });
        } else {
          console.log(
            "获取失败，请重试！--------------------------------------------------记得删掉"
          );
          return false;
        }
      });
    }
  }
};
</script>

<style>
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
  overflow: auto;
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
</style>
