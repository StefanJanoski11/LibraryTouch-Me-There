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
        <el-input
          v-model="query.name"
          placeholder="用户名"
          class="handle-input mr10"
        ></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch()"
          >搜索</el-button
        >
        <el-button type="primary" icon="el-icon-plus" @click="addUser"
          >新增用户</el-button
        >
        <el-button type="primary" icon="el-icon-plus" @click="userDownload"
          >批量导出</el-button
        >
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        header-cell-class-name="table-header"
        @selection-change="handleSelectionChange"
      >
        
        <el-table-column
          prop="user_id"
          label="ID"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column prop="user_name" label="用户名"></el-table-column>
        <el-table-column
          prop="user_identity_id"
          width="100s"
          label="是否管理员"
        ></el-table-column>
        <el-table-column
          prop="user_sex"
          width="55"
          label="性别"
        ></el-table-column>
        <el-table-column
          prop="user_sincerity"
          label="不良记录"
          width="155"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="user_create_date"
          label="注册时间"
          width="210"
        ></el-table-column>
        <el-table-column label="操作" width="230" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="history(scope.row)"
              >借阅记录</el-button
            >
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

    <!-- 详情弹出框 -->
    <el-dialog title="详情" :visible.sync="editVisible" width="30%">
      <el-row :model="user" label-width="70px">
        <el-col :span="12"> 用户名：{{ usera.user_name }} </el-col>
        <el-col :span="12"> 性别:{{ usera.user_sex }} </el-col>
        <el-col :span="12"> 手机号：{{ usera.user_phone }} </el-col>
        <el-col :span="12"> 出生日期:{{ usera.user_birthday }} </el-col>
        <el-col :span="12"> 地址{{ usera.user_address }} </el-col>
        <el-col :span="12"> 不良记录:{{ usera.user_sincerity }} </el-col>
        <el-col :span="24"> 自我描述:{{ usera.user_self_desc }} </el-col>
        <el-col :span="24"> 创建日期：{{ usera.user_create_date }} </el-col>
        <el-col :span="24"> 修改日期:{{ usera.user_alter_date }} </el-col>
        <el-col :span="24"> 修改人ID:{{ usera.user_alter_admin }} </el-col>
      </el-row>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editVisible = false">关 闭</el-button>
        <!-- <el-button type="primary" @click="saveEdit">确 定</el-button> -->
      </span>
    </el-dialog>

    <el-dialog title="新增用户" :visible.sync="addVisible" width="40%">
      <el-form
        ref="form"
        :model="form"
        status-icon
        :rules="rules"
        label-width="100px"
        class="login-form demo-ruleForm"
        label-position="righ"
      >
        <el-form-item label="ID" placeholder="ID">
          <el-input v-model="form.ID" maxlength="30" show-word-limit>
          </el-input>
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
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addVisible = false">关 闭</el-button>
        <el-button type="primary" @click="saveAdd">确 定 添 加</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="借阅记录"
      :visible.sync="dialogTableVisible"
      width=720px
    >
      <el-table :data="tableDataEnd" border style="margin-top: 25px">
        <el-table-column prop="book_name" label="书名" width="180">
        </el-table-column>
        <el-table-column prop="book_lend_date" label="借阅日期" width="150">
        </el-table-column>
        <el-table-column prop="book_scheduled" label="期望归还日期" width="150">
        </el-table-column>
        <el-table-column prop="book_return_date" label="还书日期" width="150">
        </el-table-column>
        <el-table-column prop="book_return_state" label="是否还了" width="50">
        </el-table-column>
      </el-table>
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
      } else if (value !== this.form.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
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
        btnTitle: "获取验证码",
      },
      user: {
        user_name: "",
        sex: "",
        phone: "",
        birth: "",
        address: "",
        record: "",
        selfDesc: "",
        date: "",
        alterDate: "",
        alterAdmin: "",
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
      query: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      usera: [],
      tableData: [],
      tableDataBegin: [],
      tableDataEnd: [],
      multipleSelection: [],
      delList: [],
      editVisible: false,
      addVisible: false,
      pageTotal: 0,
      form: {},
      idx: -1,
      id: -1,
      dialogTableVisible: false,
    };
  },
  mounted() {
    this.axios({
      method: "get",
      url: this.$host + "/user/getAll",
      params: {
        pageNum: this.pageIndex,
      },
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.tableData = response.data.object.list;

        this.pageTotal = response.data.object.total;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    //提交添加
    saveAdd() {
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
              console.log(
                response +
                  "--------------------------------------------------记得删掉"
              );
              //提交完成

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
    userDownload() {
      this.axios({
        method: "get",
        url: this.$host + "/user/download",
        responseType: "blob",
      })
        .then((response) => {
          let url = window.URL.createObjectURL(response.data);
          let link = document.createElement("a");
          link.style.display = "none";
          link.href = url;
          let date = new Date();
          link.setAttribute(
            "download",
            date.getFullYear() +
              "_" +
              date.getMonth() +
              "_" +
              date.getDate() +
              "_user.xlsx"
          );
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //添加用户按钮
    addUser() {
      this.addVisible = true;
    },
    // 触发搜索按钮
    handleSearch() {
      // console.log(query.name);
      this.axios({
        method: "get",
        url: this.$host + "/user/quaryUser",
        params: {
          name: this.query.name,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          this.tableData = response.data.object;

          //this.pageTotal = response.data.object.total;
        })
        .catch((error) => {
          console.log(error);
        });

      // this.$set(this.query, "pageIndex", 1);
      // this.getData();
    },
    // 删除操作
    handleDelete(index, row) {
      // 二次确认删除
      this.$confirm("确定要删除吗？", "提示", {
        type: "warning",
      })
        .then(() => {
          this.axios({
            method: "get",
            url: this.$host + "/user/deleteUser",
            params: {
              id: row.user_id,
            },
            headers: {
              "Content-Type":
                "application/x-www-form-urlencoded; charset=UTF-8",
            },
          })
            .then((response) => {
              this.axios({
                method: "get",
                url: this.$host + "/user/quaryExistUser",
                params: {},
                headers: {
                  "Content-Type":
                    "application/x-www-form-urlencoded; charset=UTF-8",
                },
              })
                .then((response) => {
                  // this.tableData = response.data.object;
                  this.tableData = response.data.object.list;
                  this.$message("删除该用户成功");

                  this.pageTotal = response.data.object.total;
                })
                .catch((error) => {
                  console.log(error);
                });
            })
            .catch((error) => {
              console.log(error);
            });
        })
        .catch(() => {});
    },
    // 多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 编辑操作
    handleEdit(index, row) {
      //this.idx = index;
      //this.form = row;
      console.log(index);
      console.log(row);
      this.axios({
        method: "get",
        url: this.$host + "/user/detail",
        params: {
          user_id: row.user_id,
          user_name: row.user_name,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          console.log(response);
          this.usera = response.data.object;
        })
        .catch((error) => {
          console.log(error);
        });

      this.editVisible = true;
    },
    // 分页导航
    handlePageChange(val) {
      this.axios({
        method: "get",
        url: this.$host + "/user/getAll",
        params: {
          pageNum: val,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          console.log(response.data.object.list);
          this.tableData = response.data.object.list;
          this.pageTotal = response.data.object.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    history(row) {
      console.log(row);
      this.axios({
        method: "get",
        url: this.$host + "/record/getRecordById",
        // url: "../../../static/mock/rentList.json",
        params: { id: row.user_id },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          this.tableDataBegin = [];
          console.log(response.data);
          this.tableDataBegin = response.data;
          console.log(this.tableDataEnd.length);
          this.tableDataEnd = this.tableDataBegin;
        })
        .catch((error) => {
          console.log(error);
        });
      this.dialogTableVisible = true;
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
