<template>
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
<el-row>
    <el-col :span="8">
  <el-form-item label="姓名" prop="name">
    <el-input v-model="ruleForm.name" :disabled="true"></el-input>
  </el-form-item>
</el-col>
<el-col :span="8">
    <el-form-item label="性别" prop="sex">
    <el-select v-model="ruleForm.sex" placeholder="选择性别" :disabled="isAble">
      <el-option label="男" value="M"></el-option>
      <el-option label="女" value="F"></el-option>
    </el-select>
  </el-form-item>
</el-col>
</el-row>

  <el-form-item label="出生年月" required>
    <el-col :span="15">
      <el-form-item prop="date1">
        <el-date-picker type="month" placeholder="选择年月" v-model="ruleForm.date" :disabled="isAble" style="width: 100%;"></el-date-picker>
      </el-form-item>
    </el-col>
  </el-form-item>
  <el-row>
    <el-col :span="8">
    <el-form-item prop="tel" label="电话">
    <el-input v-model="ruleForm.tel" :disabled="isAble"></el-input>
    </el-form-item>
    </el-col>
    <el-col :span="8">
    <el-form-item prop="email" label="邮箱">
    <el-input v-model="ruleForm.email" :disabled="isAble"></el-input>
    </el-form-item>
    </el-col>
  </el-row>

<el-row>
<el-col :span="16">
    <el-form-item label="居住地址" prop="address">
    <el-input type="textarea" v-model="ruleForm.address" :disabled="isAble"></el-input>
  </el-form-item>
</el-col>
</el-row>

<el-row>
<el-col :span="16">
 <el-form-item label="个人描述" prop="resume">
    <el-input type="textarea" v-model="ruleForm.resume" :disabled="isAble"></el-input>
  </el-form-item>
</el-col>
</el-row>

  <el-form-item>
    <el-button type="primary" @click="edit()">开始编辑</el-button>
    <el-button @click="submitForm(formName)">提交</el-button>
  </el-form-item>
</el-form>
</template>

<script>
  export default {
    data() {
      return {
          isAble: true, 
        ruleForm: { 
          name: '大熊',
          sex: 'M',
          date: '',
          tel: 13427136666,
          email:'12345@qq.com',
          resource: '',
          address: '五邑大学玫瑰园504',
          resume: ''
        },
        //验证
        rules: {
          name: [
            { required: true, message: '输入名字', trigger: 'blur' },
            { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          date: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
          ],
          tel: [
              { required: true, pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,message: '目前只支持中国大陆的手机号码',trigger: 'change' }
          ],
          email: [
              { type: 'email', required: true,trigger: 'change'}
          ],
          address: [
            { required: true, message: '请填写地址', trigger: 'blur' }
          ],
          resume: [
            { required: false, message: '自我介绍', trigger: 'blur'}
          ]
        }
      };
    },
    methods: {
        edit(){
            this.isAble = !this.isAble;
        },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {


            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>

.demo-ruleForm {
    padding-left: 170px;
    padding-right: auto;
    padding-top: 50px;
    padding-bottom: 50px;
    margin-left: auto;
    margin-right: auto;
    //background-color: rgb(124, 136, 136);
    width: 70%;
}
</style>>