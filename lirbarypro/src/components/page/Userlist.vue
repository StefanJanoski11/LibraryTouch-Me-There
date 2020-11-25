<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 基础表格
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
          type="primary"
          icon="el-icon-delete"
          class="handle-del mr10"
          @click="delAllSelection"
          >批量删除</el-button
        >
        <el-input
          v-model="query.name"
          placeholder="用户名"
          class="handle-input mr10"
        ></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch"
          >搜索</el-button
        >
        <el-button type="primary" icon="el-icon-plus" @click="addUser"
          >新增用户</el-button
        >
      </div>
      <el-table
        :data="tableDataEnd"
        border
        class="table"
        ref="multipleTable"
        header-cell-class-name="table-header"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="id"
          label="ID"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column prop="name" label="用户名">aa</el-table-column>
        <el-table-column prop="permission" label="是否管理员"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
        <el-table-column
          prop="record"
          label="不良记录"
          align="center"
        ></el-table-column>
        <el-table-column prop="date" label="注册时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
              >详情</el-button
            >
            <el-button
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :current-page="query.pageIndex"
          :page-size="query.pageSize"
          :total="pageTotal"
          @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="详情" :visible.sync="editVisible" width="30%">
      <el-row :model="form" label-width="70px">
        <el-col :span="12"> 用户名：{{ form.name }} </el-col>
        <el-col :span="12"> 性别:{{ form.sex }} </el-col>
        <el-col :span="12"> 手机号：{{ form.phone }} </el-col>
        <el-col :span="12"> 出生日期:{{ form.birth }} </el-col>
        <el-col :span="12"> 地址{{ form.address }} </el-col>
        <el-col :span="12"> 不良记录:{{ form.record }} </el-col>
        <el-col :span="24"> 自我描述:{{ form.selfDesc }} </el-col>
        <el-col :span="24"> 创建日期：{{ form.date }} </el-col>
        <el-col :span="24"> 修改日期:{{ form.alterDate }} </el-col>
        <el-col :span="24"> 修改人ID:{{ form.alterAdmin }} </el-col>
      </el-row>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editVisible = false">关 闭</el-button>
        <!-- <el-button type="primary" @click="saveEdit">确 定</el-button> -->
      </span>
    </el-dialog>

    <el-dialog title="新增用户" :visible.sync="addVisible" width="40%">
      <el-form
        ref="new_form"
        :model="new_form"
        status-icon
        :rules="rules"
        label-width="100px"
        class="login-form demo-ruleForm"
        label-position="righ"
      >
        <el-form-item label="姓名" placeholder="姓名" prop="user_name">
          <el-input
            v-model="new_form.user_name"
            maxlength="30"
            show-word-limit
            @blur="checkUsernameExist()"
          >
          </el-input>
        </el-form-item>

        <el-form-item label="邮箱" placeholder="邮箱" prop="user_email">
          <el-input v-model="new_form.user_email" maxlength="30"> </el-input>
        </el-form-item>

        <el-form-item label="性别" prop="user_sex">
          <el-select
            style="width: 100%"
            v-model="new_form.user_sex"
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
              v-model="new_form.user_birthday"
              style="width: 100%"
            ></el-date-picker>
          </el-form-item>
        </el-form-item>

        <el-form-item prop="user_address" label="地址" placeholder="地址">
          <el-input v-model="new_form.user_address" maxlength="30"> </el-input>
        </el-form-item>

        <el-form-item prop="user_phone" label="手机" placeholder="手机号码">
          <el-input v-model="new_form.user_phone" @blur="checkPhoneExist()">
          </el-input>
        </el-form-item>

        <el-form-item
          label="输入密码"
          placeholder="输入密码"
          prop="user_password"
        >
          <el-input
            type="password"
            v-model="new_form.user_password"
            autocomplete="off"
            maxlength="30"
            show-password
          >
          </el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="checkPassword">
          <el-input
            type="password"
            v-model="new_form.checkPassword"
            autocomplete="off"
            maxlength="30"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addVisible = false">关 闭</el-button>
        <el-button type="primary" @click="saveAdd()">确 定 添 加</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "userlist",
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
      } else if (value !== this.new_form.user_password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      submit_isAbled : true,
      msg_isAbled: true,
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
      new_form: {
        user_name: "",
        user_email: "",
        user_sex: "",
        user_birthday: "",
        user_phone: "", //手机号
        user_address: "",
        user_password: "",
        checkPassword: "",
      },
      rules: {
        user_name: [
          { required: true, message: "输入名字", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        user_password: [
          { validator: validatePass, trigger: "blur" },
          { required: true, message: "输入密码", trigger: "blur" },
        ],
        checkPassword: [
          { validator: validatePass2, trigger: "blur" },
          { required: true, message: "输入确认密码", trigger: "blur" },
        ],
        user_address: [
          {
            required: true,
            message: "请输入地址",
            trigger: "blur",
          },
        ],
        user_email: [
          { type: "email", required: true, trigger: "change" },
          {
            required: true,
            message: "请输入邮箱地址",
            trigger: "blur",
          },
        ],
        user_sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        user_birthday: [
          {
            type: "date",
            format: "yyyy-MM-dd",
            required: true,
            message: "请选择日期",
            trigger: "blur",
          },
        ],
        user_phone: [
          {
            required: true,
            pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,
            message: "请输入中国大陆的手机号码",
            trigger: "change", //输入时就会验证
          },
          {
            required: true,
            message: "请输入手机号",
            trigger: "blur",
          },
        ],
      },
      query: {
        address: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      tableDataBegin: [],
      tableDataEnd: [],
      multipleSelection: [],
      delList: [],
      editVisible: false,
      addVisible: false,
      pageTotal: 0,
      idx: -1,
      id: -1,
    };
  },
  mounted() {
    this.axios({
      method: "get",
      url: "../../../static/mock/user.json",
      //url: "/user.json",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.tableDataBegin = response.data.userlist;
        this.pageTotal = this.tableDataBegin.length;
        if (this.pageTotal > this.query.pageSize) {
          //如果有好多，只需要第一页的数据
          for (let index = 0; index < this.query.pageSize; index++) {
            this.tableDataEnd.push(this.tableDataBegin[index]);
          }
        } else {
          this.tableDataEnd = this.tableDataBegin;
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    checkUsernameExist() {
      let me = this;
      this.axios({
        method: "get",
        url: "http://10.10.102.142:8080/user/checkUsernameIsExist",
        params: {
          user_name: me.new_form.user_name,
        },
      })
        .then((response) => {
          if (response.data.code == 406) {
            console.log("---- ");
            me.submit_isAbled = false;
            alert("已经存在用户注册使用该用户名");
          } else {
            me.submit_isAbled = true;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    checkPhoneExist() {
      this.axios({
        method: "get",
        url: "http://10.10.102.142:8080/user/checkPhoneIsExist",
        params: {
          user_phone: this.new_form.user_phone,
        },
      })
        .then((response) => {
          if (response.data.code == 406) {
            this.msg_isAbled = true;
            alert("已经存在用户注册使用该手机号码");
          } else {
            this.msg_isAbled = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //提交添加
    saveAdd() {
      let me = this;
      this.$refs.new_form.validate((valid) => {
        if (!me.submit_isAbled) {
          alert("已经存在用户注册使用该手机号码");
          return false;
        }
        if (valid) {
          console.log(this.new_form);
          this.axios({
            method: "post",
            url: "http://10.10.102.142:8080/user/register",
            params: {
              user_name: this.new_form.user_name,
              user_email: this.new_form.user_email,
              user_sex: this.new_form.user_sex,
              user_birthday: this.new_form.user_birthday,
              user_phone: this.new_form.user_phone, //手机号
              user_address: this.new_form.user_address, //地址
              user_password: this.new_form.user_password,
              checkPassword: this.new_form.checkPassword,
            },
            headers: {
              "Content-Type":
                "application/x-www-form-urlencoded; charset=UTF-8",
            },
          })
            .then((response) => {
              console.log(
                response +
                  "注册成功-------------------------------------------------记得删掉"
              );
              this.addVisible = false;
            })
            .catch((error) => {
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
    },
    //添加用户按钮
    addUser() {
      this.addVisible = true;
    },
    // 触发搜索按钮
    handleSearch() {
      this.$set(this.query, "pageIndex", 1);
      this.getData();
    },
    // 删除操作
    handleDelete(index, row) {
      // 二次确认删除
      this.$confirm("确定要删除吗？", "提示", {
        type: "warning",
      })
        .then(() => {
          this.$message.success("删除成功");
          this.tableData.splice(index, 1);
        })
        .catch(() => {});
    },
    // 多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delAllSelection() {
      const length = this.multipleSelection.length;
      let str = "";
      this.delList = this.delList.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].name + " ";
      }
      this.$message.error(`删除了${str}`);
      this.multipleSelection = [];
    },
    // 编辑操作
    handleEdit(index, row) {
      this.idx = index;
      this.form = row;
      this.editVisible = true;
    },
    // 保存编辑
    // saveEdit() {
    //     this.editVisible = false;
    //     this.$message.success(`修改第 ${this.idx + 1} 行成功`);
    //     this.$set(this.tableData, this.idx, this.form);
    // },
    // 分页导航
    handlePageChange(val) {
      this.$set(this.query, "pageIndex", val);
      this.getData();
    },
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.table {
  width: 100%;
  font-size: 14px;
}
.red {
  color: #ff0000;
}
.mr10 {
  margin-right: 10px;
}
.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
