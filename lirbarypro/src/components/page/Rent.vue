<template>
  <div>
    <el-input v-model="tableDataName" placeholder="请输入姓名" style="width:240px"></el-input>
    <el-button type="primary" @click="doFilter">搜索</el-button>
    <el-table :data="tableDataEnd" border style="margin-top: 25px">
          <el-table-column prop="BookName" label="书名" width="240">
          </el-table-column>
          <el-table-column prop="Author" label="作者" width="200">
          </el-table-column>
          <el-table-column prop="type" label="类型" width="200">
          </el-table-column>
          <el-table-column prop="registerDate" label="上架日期" width="200">            
          </el-table-column>
          <el-table-column prop="last" label="剩余数量" width="100">
          </el-table-column>
          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <!-- scope就相当于是tableData的一行,scope.row 就能拿到整行的值，scope.$index就能代表当前行的下标 -->
              <el-button type="info" @click="info(scope.$index, scope.row)"
                >详情</el-button>
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
              prop="BookName"
              label="书籍"
              width="160"
            ></el-table-column>
            <el-table-column
              prop="Author"
              label="作者"
              width="120"
            ></el-table-column>
          </el-table>
        </el-dialog>
  </div>
</template>
<script>
import Vue from "vue";
  export default {
    data() {
      return {
        tableDataBegin: [],//初始数据
        tableDataName: "",
        tableDataEnd: [],//最终显示
        table2:[],//弹窗显示
        currentPage: 1,
        pageSize: 2,
        totalItems: 0,
        filterTableDataEnd:[],
        flag:false,
        dialogTableVisible: false,
      };
    },
    created() {
      this.totalItems = this.tableDataBegin.length;
      if (this.totalItems > this.pageSize) {
        for (let index = 0; index < this.pageSize; index++) {
          this.tableDataEnd.push(this.tableDataBegin[index]);
        }
      } else {
        this.tableDataEnd = this.tableDataBegin;
      }
    },
    mounted() {
    Vue.axios({
      method: "get",
      url: "../../../static/mock/BookBAR.json",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.tableDataBegin = [];
        this.tableDataBegin = response.data.tableDataBegin;
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
        this.tableDataEnd = []
        //每次手动将数据置空,因为会出现多次点击搜索情况
        this.filterTableDataEnd=[]
        this.tableDataBegin.forEach((value, index) => {
          if(value.name){
            if(value.name.indexOf(this.tableDataName)>=0){
              this.filterTableDataEnd.push(value)
            }
          }
        });
        //页面数据改变重新统计数据数量和当前页
        this.currentPage=1
        this.totalItems=this.filterTableDataEnd.length
        //渲染表格,根据值
        this.currentChangePage(this.filterTableDataEnd)
        //页面初始化数据需要判断是否检索过
        this.flag=true
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
        if(!this.flag){
        //tableDataBegin不能写成tableDataEnd，不然在没有进行搜索功能的时候，不能进行分页操作，数据丢失
          this.currentChangePage(this.tableDataBegin)
        }else{
          this.currentChangePage(this.filterTableDataEnd)
        }
      }, //组件自带监控当前页码
      currentChangePage(list) {
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
    }
  };
</script>