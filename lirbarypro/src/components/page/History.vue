<template>
  <div id="div3">
    <div id="div2">
      <div id="div1">
        <div style="padding-top: 25px">
          <el-input
            v-model="tableDataName"
            placeholder="支持模糊查询书名"
            style="width: 360px"
          ></el-input>
          <el-button type="primary" @click="doFilter">搜索</el-button>
        </div>

        <el-table :data="tableDataEnd" border style="margin-top: 25px">
          <el-table-column prop="book_name" label="书名" width="240">
          </el-table-column>
          <el-table-column prop="book_lend_date" label="借阅日期" width="200">
          </el-table-column>
          <el-table-column prop="book_scheduled" label="期望归还日期" width="200">
          </el-table-column>
          <el-table-column prop="book_return_date" label="还书日期" width="200">
          </el-table-column>
          <el-table-column prop="book_return_state" label="是否还了" width="100">
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
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[1, 2, 3, 4, 5, 6]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next"
          :total="totalItems"
          style="margin-top: 25px"
        >
        </el-pagination>
        <el-dialog
          title="借阅详情"
          :visible.sync="dialogTableVisible"
          width="720px"
        >
          <el-table :data="table2">
            <el-table-column
              prop="user_name"
              label="姓名"
              width="80"
            ></el-table-column>
            <el-table-column
              prop="user_id"
              label="ID"
              width="60"
            ></el-table-column>
            <el-table-column
              prop="book_name"
              label="书籍"
              width="160"
            ></el-table-column>
            <el-table-column
              prop="book_lend_date"
              label="借阅日期"
              width="120"
            ></el-table-column>
            <el-table-column
              prop="book_scheduled"
              label="期望日期"
              width="120"
            ></el-table-column>
            <el-table-column label="剩余日期" width="120">{{
              this.lastday
            }}</el-table-column>
          </el-table>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>

import Vue from "vue";
export default {
  data() {
    return {
      
      tableDataBegin: [],
      tableDataName: "",
      tableDataEnd: [], //最终显示数据
      table2: [], //弹窗显示数据
      lastday: "",
      currentPage: 1,
      pageSize: 2,
      totalItems: 0,
      filterTableDataEnd: [],
      flag: false,
      dialogTableVisible: false,
      returnState: [
        {
          value: "ture",
          label: "已还",
        },
        {
          value: "false",
          label: "未还",
        },
      ],
    };
  },
  mounted() {
    Vue.axios({
      method: "get",
      url: this.$host+'/record/getRecordById',
      // url: "../../../static/mock/rentList.json",
      params: {id: window.sessionStorage.getItem("ms_userid")},
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.tableDataBegin = [];
        console.log(response.data);
        this.tableDataBegin = response.data;
        console.log(this.tableDataEnd.length);
        this.totalItems = this.tableDataBegin.length;
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
    //前端搜索功能需要区分是否检索,因为对应的字段的索引不同
    //用两个变量接收currentChangePage函数的参数
    doFilter() {
      if (this.tableDataName == "") {
        this.$message.warning("查询条件不能为空！");
        return;
      }
      this.tableDataEnd = [];
      //每次手动将数据置空,因为会出现多次点击搜索情况
      this.filterTableDataEnd = [];
      this.tableDataBegin.forEach((value, index) => {
        if (value.book_name) {
          if (value.book_name.indexOf(this.tableDataName) >= 0) {
            this.filterTableDataEnd.push(value);
          }
        }
      });
      //页面数据改变重新统计数据数量和当前页
      this.currentPage = 1;
      this.totalItems = this.filterTableDataEnd.length;
      //渲染表格,根据值
      this.currentChangePage(this.filterTableDataEnd);
      //页面初始化数据需要判断是否检索过
      this.flag = true;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.handleCurrentChange(this.currentPage);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      //需要判断是否检索
      if (!this.flag) {
        //tableDataBegin不能写成tableDataEnd，不然在没有进行搜索功能的时候，不能进行分页操作，数据丢失
        this.currentChangePage(this.tableDataBegin);
      } else {
        //关键词检索过，传入检索后的结果
        this.currentChangePage(this.filterTableDataEnd);
      }
    }, //组件自带监控当前页码
    currentChangePage(list) {
      //根据第几页，有多少条，填入该页合适的数据
      let from = (this.currentPage - 1) * this.pageSize;
      let to = this.currentPage * this.pageSize;
      this.tableDataEnd = [];
      for (; from < to; from++) {
        if (list[from]) {
          this.tableDataEnd.push(list[from]);
        }
      }
    },
    info(index, row) {
      this.table2 = [];
      this.table2.push(this.tableDataEnd[index]);
      this.dialogTableVisible = true;
    },
  },
};
</script>

<style scoped>
#div1 {
  padding-left: auto;
  padding-right: auto;
  margin: auto;
  width: 90%;
}
#div2 {
  margin: auto;
  background-color: rgba(207, 235, 247, 0.8);
  width: 90%;
  padding-bottom: 50px;
}
#div3 {
  background-image: url(../../assets/img/login-bg.jpg);
  background-size: 100%;
  height: 780px;
}
</style>