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
          title="图书详情"
          :visible.sync="dialogTableVisible"
          width="720px"
        >
          <el-row :model="bookDetail" label-width="70px">
            <el-col :span="12"> 书名：{{ bookDetail.books_name }} </el-col>
            <el-col :span="12"> 作者:{{ bookDetail.books_author }} </el-col>
            <el-col :span="12"> 剩余数量:{{ bookDetail.books_last }} </el-col>
            <el-col :span="24">
              上架日期:{{ bookDetail.books_create_date }}
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
      url: this.$host+"/books/getDailyHot",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        if (response.data.object == null) {
          return false;
        }
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
      });
  },
  methods: {
    info(index, row) {
      this.bookDetail = row;
      this.dialogTableVisible = true;
    },
    daily() {
      document.getElementById("hotTitle").innerHTML = "本日热门图书";
      this.$message.success('获取本日热门图书成功');
      Vue.axios({
        method: "get",
        url: this.$host+"/books/getDailyHot",
        data: "",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.data.object == null) {
            return false;
          }
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
        });
    },
    weekly() {
      document.getElementById("hotTitle").innerHTML = "本周热门图书";
      this.$message.success("获取本周热门图书成功");
      Vue.axios({
        method: "get",
        url: this.$host+"/books/getWeeklyHot",
        data: "",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.data.object == null) {
            return false;
          }
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
        });
    },
    monthly() {
      document.getElementById("hotTitle").innerHTML = "本月热门图书";
      this.$message.success("获取本月热门图书成功");
      Vue.axios({
        method: "get",
        url: this.$host+"/books/getWeeklyHot",
        data: "",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          if (response.data.object == null) {
            return false;
          }
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