<template>
  <div>
    <el-input v-model="tableDataName" placeholder="请输入书名" style="width:240px"></el-input>
    <el-button type="primary"  @click="doFilter">搜索</el-button>
    <el-table :data="tableDataEnd" border style="margin-top: 25px">
          <el-table-column prop="book_name" label="书名" width="240">
          </el-table-column>
          <el-table-column prop="user_name" label="借阅人" width="200">
          </el-table-column>
          <el-table-column prop="book_lend_date" label="借出日期" width="200">            
          </el-table-column>
          <el-table-column prop="book_return_date" label="归还日期" width="200">            
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
          title="书本详情"
          :visible.sync="dialogTableVisible"
          width="720px"
        >
          <el-row :model="bookDetail" label-width="70px">
        <el-col :span="12"> 书名：{{ bookDetail.books_name }} </el-col>
        <el-col :span="12"> 作者:{{ bookDetail.books_author }} </el-col>
        <el-col :span="12"> 国家：{{ bookDetail.info_country }} </el-col>
        <el-col :span="12"> 类型:{{ bookDetail.info_theme }} </el-col>
        <el-col :span="12"> 剩余数量{{ bookDetail.books_last }} </el-col>
        <el-col :span="24"> 上架日期:{{ bookDetail.books_registerDate }} </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogTableVisible = false">关 闭</el-button>
        
      </span>
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
        pageSize: 4,
        totalItems: 0,
        filterTableDataEnd:[],
        flag:false,
        dialogTableVisible: false,
        bookDetail:{}
      };
    },
    mounted() {
    Vue.axios({
      method: "get",
      url: "http://10.10.102.143:8080/record/getAll",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        console.log(response.data)
        this.tableDataBegin = [];
        this.tableDataBegin = response.data;
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
        Vue.axios({
      method: "get",
      url: "http://10.10.102.143:8080/book/quaryName",
      params: {name:this.tableDataName},
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        console.log(response.data)
        this.tableDataBegin = [];
        this.tableDataBegin = response.data;
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

        // if (this.tableDataName == "") {
        //   this.$message.warning("查询条件不能为空！");
        //   return;
        // }
        // this.tableDataEnd = []
        // //每次手动将数据置空,因为会出现多次点击搜索情况
        // this.filterTableDataEnd=[]
        // this.tableDataBegin.forEach((value, index) => {
        //   if(value.name){
        //     if(value.name.indexOf(this.tableDataName)>=0){
        //       this.filterTableDataEnd.push(value)
        //     }
        //   }
        // });
        // //页面数据改变重新统计数据数量和当前页
        // this.currentPage=1
        // this.totalItems=this.filterTableDataEnd.length
        // //渲染表格,根据值
        // this.currentChangePage(this.filterTableDataEnd)
        // //页面初始化数据需要判断是否检索过
        // this.flag=true
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
         console.log(row.book_id);
         Vue.axios({
      method: "get",
      url: "http://10.10.102.143:8080//book/getBookAllInfo",
      params: {id:row.book_id},
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        console.log(response.data)
        this.bookDetail = response.data.object;
      })
      .catch((error) => {
        console.log(error);
      });

      
      this.dialogTableVisible = true;
    },
    }
  };
</script>