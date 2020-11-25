<template>
  <div id="hotdiv2">
    <div id="hotdiv1">
      <div id="hotdiv0">
        <div id="hotdiv-1">
          <p id="hotTitle">本日热门图书</p>
        </div>
        <el-table :data="tableDataEnd" border style="margin-top: 25px">
          <el-table-column prop="books_name" label="书名" width="240">
          </el-table-column>
          <el-table-column prop="books_author" label="作者" width="240">
          </el-table-column>
          <el-table-column prop="books_daily" label="日点击量" width="100">
          </el-table-column>
          <el-table-column prop="books_weekly" label="周点击量" width="100">
          </el-table-column>
          <el-table-column prop="books_monthly" label="月点击量" width="100">
          </el-table-column>
          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <!-- scope就相当于是tableData的一行,scope.row 就能拿到整行的值，scope.$index就能代表当前行的下标 -->
              <el-button type="info" @click="info(scope.$index, scope.row)"
                >详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-dialog
          title="书本详情"
          :visible.sync="dialogTableVisible"
          width="720px"
        >
          <el-row :model="bookDetail" label-width="70px">
            <el-col :span="12"> 书名：{{ bookDetail.books_name }} </el-col>
            <el-col :span="12"> 作者:{{ bookDetail.books_author }} </el-col>
            <el-col :span="12"> 国家：{{ bookDetail.info_country }} </el-col>
            <el-col :span="12"> 类型:{{ bookDetail.info_theme }} </el-col>
            <el-col :span="12"> 剩余数量:{{ bookDetail.books_last }} </el-col>
            <el-col :span="24">
              上架日期:{{ bookDetail.books_registerDate }}
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogTableVisible = false"
              >关 闭</el-button
            >
          </span>
        </el-dialog>
        <div style="height: 25px"></div>

        <el-button type="primary" @click="daily()">本日热门</el-button>

        <el-button type="primary" @click="weekly()">本周热门</el-button>

        <el-button type="primary" @click="monthly()">本月热门</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
export default {
  data() {
    return {
      tableDataBegin: [], //初始数据
      tableDataName: "",
      tableDataEnd: [], //最终显示
      table2: [], //弹窗显示
      currentPage: 1,
      pageSize: 5,
      totalItems: 0,
      filterTableDataEnd: [],
      flag: false,
      dialogTableVisible: false,
      bookDetail: [],
    };
  },
  mounted() {
    Vue.axios({
      method: "get",
      url: "http://10.10.102.142:8080/books/getDailyHot",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        if (response.data.object == null) {
          return false;
        }
        console.log(response.data.object[0]);
        let array = response.data.object;
        this.totalItems = array.length;
        for (let i = 0; i < this.totalItems; i++) {
          this.tableDataBegin[i] = array[i].value;
        }

        if (this.totalItems > this.pageSize) {
          //如果有好多，只需要第一页的数据
          for (let index = 0; index < this.pageSize; index++) {
            this.tableDataEnd.push(this.tableDataBegin[index]);
          }
        } else {
          this.tableDataEnd = this.tableDataBegin;
        }
      })
      .catch((error) => {
        console.log(error);
        console.log("报错了");
      });
  },
  methods: {
    // doFilter() {
    //   if (this.tableDataName == "") {
    //     this.$message.warning("查询条件不能为空！");
    //     return;
    //   }
    //   this.tableDataEnd = [];
    //   //每次手动将数据置空,因为会出现多次点击搜索情况
    //   this.filterTableDataEnd = [];
    //   this.tableDataBegin.forEach((value, index) => {
    //     if (value.book_name) {
    //       if (value.book_name.indexOf(this.tableDataName) >= 0) {
    //         this.filterTableDataEnd.push(value);
    //       }
    //     }
    //   });
    //   //页面数据改变重新统计数据数量和当前页
    //   this.currentPage = 1;
    //   this.totalItems = this.filterTableDataEnd.length;
    //   //渲染表格,根据值
    //   this.currentChangePage(this.filterTableDataEnd);
    //   //页面初始化数据需要判断是否检索过
    //   this.flag = true;
    // },

    // handleSizeChange(val) {
    //   console.log(`每页 ${val} 条`);
    //   this.pageSize = val;
    //   this.handleCurrentChange(this.currentPage);
    // },
    // handleCurrentChange(val) {
    //   console.log(`当前页: ${val}`);
    //   this.currentPage = val;
    //   //需要判断是否检索
    //   if (!this.flag) {
    //     //tableDataBegin不能写成tableDataEnd，不然在没有进行搜索功能的时候，不能进行分页操作，数据丢失
    //     this.currentChangePage(this.tableDataBegin);
    //   } else {
    //     this.currentChangePage(this.filterTableDataEnd);
    //   }
    // }, //组件自带监控当前页码
    // currentChangePage(list) {
    //   let from = (this.currentPage - 1) * this.pageSize;
    //   let to = this.currentPage * this.pageSize;
    //   this.tableDataEnd = [];
    //   for (; from < to; from++) {
    //     if (list[from]) {
    //       this.tableDataEnd.push(list[from]);
    //     }
    //   }
    // },
    info(index, row) {
      console.log(row);
      this.bookDetail = row;
      this.dialogTableVisible = true;
    },
    daily() {
      document.getElementById("hotTitle").innerHTML = "本日热门图书";
      this.$message('获取本日热门图书成功');
      Vue.axios({
        method: "get",
        url: "http://10.10.102.142:8080/books/getDailyHot",
        data: "",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.data.object == null) {
            return false;
          }
          console.log(response.data.object[0]);
          let array = response.data.object;
          this.totalItems = array.length;
          for (let i = 0; i < this.totalItems; i++) {
            this.tableDataBegin[i] = array[i].value;
          }

          if (this.totalItems > this.pageSize) {
            //如果有好多，只需要第一页的数据
            for (let index = 0; index < this.pageSize; index++) {
              this.tableDataEnd.push(this.tableDataBegin[index]);
            }
          } else {
            this.tableDataEnd = this.tableDataBegin;
          }
        })
        .catch((error) => {
          console.log(error);
          console.log("报错了");
        });
    },
    weekly() {
      document.getElementById("hotTitle").innerHTML = "本周热门图书";
      this.$message("获取本周热门图书成功");
      Vue.axios({
        method: "get",
        url: "http://10.10.102.142:8080/books/getWeeklyHot",
        data: "",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.data.object == null) {
            return false;
          }
          console.log(response.data.object[0]);
          let array = response.data.object;
          this.totalItems = array.length;
          for (let i = 0; i < this.totalItems; i++) {
            this.tableDataBegin[i] = array[i].value;
          }

          if (this.totalItems > this.pageSize) {
            //如果有好多，只需要第一页的数据
            for (let index = 0; index < this.pageSize; index++) {
              this.tableDataEnd.push(this.tableDataBegin[index]);
            }
          } else {
            this.tableDataEnd = this.tableDataBegin;
          }
        })
        .catch((error) => {
          console.log(error);
          console.log("报错了");
        });
    },
    monthly() {
      document.getElementById("hotTitle").innerHTML = "本月热门图书";
      this.$message("获取本月热门图书成功");
      Vue.axios({
        method: "get",
        url: "http://10.10.102.142:8080/books/getWeeklyHot",
        data: "",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.data.object == null) {
            return false;
          }
          console.log(response.data.object[0]);
          let array = response.data.object;
          this.totalItems = array.length;
          for (let i = 0; i < this.totalItems; i++) {
            this.tableDataBegin[i] = array[i].value;
          }

          if (this.totalItems > this.pageSize) {
            //如果有好多，只需要第一页的数据
            for (let index = 0; index < this.pageSize; index++) {
              this.tableDataEnd.push(this.tableDataBegin[index]);
            }
          } else {
            this.tableDataEnd = this.tableDataBegin;
          }
        })
        .catch((error) => {
          console.log(error);
          console.log("报错了");
        });
    },
  },
};
</script>

<style >
#hotTitle {
  color: rgb(65, 74, 102);
  text-align: center;
}
#hotdiv-1 {
  width: 50%;
  margin: auto;
}
#hotdiv0 {
  width: 90%;
  margin: auto;
}

#hotdiv1 {
  width: 85%;
  padding-left: auto;
  padding-right: auto;
  padding-top: 50px;
  padding-bottom: 50px;
  margin: auto;
  background-color: rgba(207, 235, 247, 0.8);
}

#hotdiv2 {
  background-image: url(../../assets/img/login-bg.jpg);
  height: 100%;
}
</style>