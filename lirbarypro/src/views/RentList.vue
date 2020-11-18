<template>
  <div>
    <el-input
      v-model="tableDataName"
      placeholder="支持模糊查询书名"
      style="width: 240px"
    ></el-input>
    <el-button type="primary" @click="doFilter">搜索</el-button>
    <el-table :data="tableDataEnd" border style="width: 100%">
      <el-table-column prop="BookName" label="书名" width="220">
      </el-table-column>
      <el-table-column prop="StarDate" label="借阅日期" width="180">
      </el-table-column>
      <el-table-column prop="DueDate" label="期望归还日期" width="180">
      </el-table-column>
      <el-table-column prop="ReturnDate" label="还书日期" width="180">
      </el-table-column>
      <el-table-column prop="Overdue" label="是否逾期" width="100">
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
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalItems"
    >
    </el-pagination>
    <el-dialog title="借阅详情" :visible.sync="dialogTableVisible">
      <el-table :data="table2">
        <el-table-column
          prop="UserName"
          label="姓名"
          width="150"
        ></el-table-column>
        <el-table-column
          prop="UserID"
          label="ID"
          width="200"
        ></el-table-column>
        <el-table-column prop="BookName" label="书籍"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      table2: [
        {
          BookName: "1",
          StarDate: "",
          DueDate: "",
          ReturnDate: "",
          Overdue: "",
          UserName: "1",
          UserID: "1",
        },
      ],
      tableDataBegin: [
        {
          BookName: "高数上（同济版）",
          StarDate: "2016-05-01",
          DueDate: "2016-06-01",
          ReturnDate: "2016-05-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "高数下（同济版）",
          StarDate: "2016-05-01",
          DueDate: "2016-06-01",
          ReturnDate: "2016-05-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "广东富婆通信录",
          StarDate: "2016-05-01",
          DueDate: "2016-06-01",
          ReturnDate: "2016-05-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "凡尔赛文学经典",
          StarDate: "2016-05-01",
          DueDate: "2016-06-01",
          ReturnDate: "2016-05-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "酸菜鱼修炼手册",
          StarDate: "2016-05-01",
          DueDate: "2016-06-01",
          ReturnDate: "2016-05-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "颈椎腰椎保养",
          StarDate: "2016-05-01",
          DueDate: "2016-06-01",
          ReturnDate: "2016-05-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "黑百合5天速成",
          StarDate: "2020-02-01",
          DueDate: "2020-03-01",
          ReturnDate: "2020-02-26",
          Overdue: "否",
          UserName: "小明",
          UserID: "1101",
        },
        {
          BookName: "如何成为粥粥",
          StarDate: "2019-11-01",
          DueDate: "2019-12-01",
          ReturnDate: "2019-12-26",
          Overdue: "是",
          UserName: "小明",
          UserID: "1101",
        },
      ],
      tableDataName: "",
      tableDataEnd: [], //最终显示数据
      currentPage: 1,
      pageSize: 5,
      totalItems: 0,
      filterTableDataEnd: [],
      flag: false,
      dialogTableVisible: false,
    };
  },
  created() {
    this.totalItems = this.tableDataBegin.length;
    if (this.totalItems > this.pageSize) {
      //如果有好多，只需要第一页的数据
      for (let index = 0; index < this.pageSize; index++) {
        this.tableDataEnd.push(this.tableDataBegin[index]);
      }
    } else {
      this.tableDataEnd = this.tableDataBegin;
    }
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
        if (value.BookName) {
          if (value.BookName.indexOf(this.tableDataName) >= 0) {
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
      // this.table2 = [
      //   row.BookName,
      //   row.StarDate,
      //   row.DueDate,
      //   row.ReturnDate,
      //   row.Overdue,
      //   row.UserName,
      //   row.UserID,
      // ];
      // this.table2.UserName = row.UserName;
      // this.table2.UserID = row.UserID;
      // this.table2.BookName = row.BookName;
      console.log(row.BookName);
      console.log(this.table2.BookName);
      //alert(row.data);
      this.dialogTableVisible = true;
    },
  },
};
</script>