<template>
  <div id="div1">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <div id="div2">
        <el-row>
          <el-col :span="6">
            <div style="float: right">
              <h4>读者信息</h4>
            </div>
          </el-col>
          <el-col :span="12" :offset="6">
            <div>
              <el-form-item>
                <el-button type="primary" @click="edit()">开始编辑</el-button>
                <el-button @click="submitForm(ruleForm)">提交</el-button>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div>
              <el-form-item label="姓名">
                {{ruleForm.user_name}}
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div>
              <el-form-item label="性别" prop="user_sex">
                <el-select
                  style="width: 100%"
                  v-model="ruleForm.sex"
                  placeholder="选择性别"
                  :disabled="isAble"
                >
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="0"></el-option>
                </el-select>
              </el-form-item>
            </div>
          </el-col>
        </el-row>

        <el-row><el-col :span="24">
          <el-form-item label="出生年月" required>
            
              <el-form-item prop="date1">
                <el-date-picker
                  type="month"
                  placeholder="选择年月"
                  v-model="ruleForm.user_birthday"
                  :disabled="isAble"
                  style="width: 100%"
                ></el-date-picker>
              </el-form-item>
          </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <div>
              <el-form-item prop="tel" label="电话">
                <el-input v-model="ruleForm.user_phone" :disabled="isAble"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div>
              <el-form-item prop="email" label="邮箱">
                <el-input
                  v-model="ruleForm.user_email"
                  :disabled="isAble"
                ></el-input>
              </el-form-item>
            </div>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="居住地址" prop="address">
              <el-input
                type="textarea"
                v-model="ruleForm.user_address"
                :disabled="isAble"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="个人描述" prop="user_self_desc">
              <el-input
                type="textarea"
                v-model="ruleForm.user_self_desc"
                :disabled="isAble"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-switch
            v-model="value1"
            active-text="修改密码"
            inactive-text="不修改密码"
            :disabled="isAble"
            @change="edit2"
          >
          </el-switch>
        </el-form-item>
        <el-form-item label="新密码" prop="user_password">
          <el-input
            type="password"
            v-model="ruleForm.user_password"
            :disabled="value2"
          ></el-input>
        </el-form-item>
        <el-form-item label="重复新密码" prop="checkPassword">
          <el-input
            type="password"
            v-model="ruleForm.checkPassword"
            :disabled="value2"
          ></el-input>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import Vue from "vue";
export default {
  
  data() {
    let validatePw1 = (rule, value, callback) => {
            if (value !== this.ruleForm.checkPassword) {
                callback(new Error('两次密码不一致!'))
            } else {
                callback()
            }
    }
    let validatePw2 = (rule, value, callback) => {
            if (value !== this.ruleForm.user_password) {
                callback(new Error('两次密码不一致!'))
            } else {
                callback()
            }
        }
    return {
      isAble: true,
      value1: false,
      value2: true,
      ruleForm: {
        user_id:"",
        user_name: "",
        user_sex: "",
        user_birthday: "",
        user_phone: 0,
        user_email: "",
        user_address: "",
        user_self_desc: "",
        user_password: "",
        checkPassword: "",
      },
      //验证
      rules: {
        user_name: [
          { required: true, message: "输入名字", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" },
        ],
        user_sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        user_birthday: [
          {
            type: "date",
            required: true,
            format: "yyyy-MM-dd",
            message: "请选择日期",
            trigger: "change",
          },
        ],
        user_phone: [
          {
            required: true,
            pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,
            message: "目前只支持中国大陆的手机号码",
            trigger: "change",
          },
        ],
        user_email: [{ type: "email", required: true, trigger: "change" }],
        user_address: [{ required: true, message: "请填写地址", trigger: "blur" }],
        user_self_desc: [{ required: false, message: "自我介绍", trigger: "blur" }],
        user_password: [
          {
            message: "请输入密码",
            trigger: "blur",
          },
          {
            pattern: /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/,
            message: "密码必须由数字、字母、特殊字符组合,请输入8-30位",
            trigger: "blur",
          }, { validator: validatePw1, trigger: "change" },
        ],
        checkPassword: [
          {
            message: "请输入密码",
            trigger: "blur",
          },
          {
            pattern: /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/,
            message: "密码必须由数字、字母、特殊字符组合,请输入8-30位",
            trigger: "change",
          },
          { validator: validatePw2, trigger: "change" },
        ],
      },
    };
  },
  mounted() {
    this.ruleForm.user_id = window.sessionStorage.getItem("ms_userid");
    this.ruleForm.user_name = window.sessionStorage.getItem("ms_username");
    this.ruleForm.user_sex = window.sessionStorage.getItem("ms_user_sex");
    this.ruleForm.user_birthday = window.sessionStorage.getItem("ms_user_birthday");
    this.ruleForm.user_phone = window.sessionStorage.getItem("ms_user_phone");
    this.ruleForm.user_email = window.sessionStorage.getItem("ms_user_email");
    this.ruleForm.user_address = window.sessionStorage.getItem("ms_user_address");
    this.ruleForm.user_self_desc = window.sessionStorage.getItem("ms_user_self_desc");
    this.ruleForm.user_password = window.sessionStorage.getItem("ms_user_password");
    this.ruleForm.checkPassword = window.sessionStorage.getItem("ms_user_password");
  },
  methods: {
    edit() {
      if (this.isAble) {
        this.isAble = false;
      } else alert("已是可编辑状态");
    },
    edit2() {
      console.log(this.value1);
      console.log(this.value2);
      this.value2 = !this.value2;
    },
    submitForm() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          console.log(this.ruleForm);
          alert("submit!--------------------------------------------------记得删掉");
          //异步请求修改
          Vue.axios({
            method: "post",
            url: "http://10.10.102.142:8080/user/alterDetail",
            data: this.ruleForm,
            headers: {
              "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
            },
          })
          .then((response) => {
            console.log(response);
            // this.ruleForm.name = response.data.name;
            // this.ruleForm.sex = response.data.sex;
            // this.ruleForm.date = response.data.date;
            // this.ruleForm.tel = response.data.tel;
            // this.ruleForm.email = response.data.email;
            // this.ruleForm.address = response.data.address;
            // this.ruleForm.resume = response.data.resume;
          })
          .catch((error) => {
            console.log(error);
          });

        } else {
          console.log("error submit!!--------------------------------------------------记得删掉");
          return false;
        }
      });
    },
    resetForm(ruleForm) {
      this.$refs[ruleForm].resetFields();
    },
  },
};
</script>

<style scoped>
#div1 {
  background-image: url(../../assets/img/login-bg.jpg);
  background-size: 100%;
  height: 780px;
}
.demo-ruleForm {
  padding-left: auto;
  padding-right: auto;
  padding-top: 50px;
  padding-bottom: 50px;
  margin: auto;
  background-color: rgba(207, 235, 247, 0.8);
  width: 70%;
}
#div2 {
  margin: auto;
  width: 75%;
  height: 100%;
}
</style>