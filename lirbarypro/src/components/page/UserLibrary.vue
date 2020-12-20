<template>
  <div class="box">
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
    </div>
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline"
      label-width="60px"
    >
      <el-form-item label="国家">
        <el-select v-model="formInline.info_country" placeholder="国家">
          <el-option label="无" value=""></el-option>
          <el-option label="中国" value="中国"></el-option>
          <el-option label="法国" value="法国"></el-option>
          <el-option label="德国" value="德国"></el-option>
          <el-option label="印度" value="印度"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="类型">
        <el-select v-model="formInline.info_type"  placeholder="类型">
          <el-option label="无" value=""></el-option>
          <el-option label="现实主义" value="现实主义"></el-option>
          <el-option label="哲学主义" value="哲学主义"></el-option>
          <el-option label="社会主义" value="社会主义"></el-option>
          <el-option label="资本主义" value="资本主义"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="篇幅">
        <el-select v-model="formInline.info_length" placeholder="篇幅">
          <el-option label="无" value=""></el-option>
          <el-option label="短篇" value="1"></el-option>
          <el-option label="中篇" value="2"></el-option>
          <el-option label="长篇" value="3"></el-option>
          <el-option label="超长篇" value="4"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="主题">
        <el-select v-model="formInline.info_theme" placeholder="主题">
          <el-option label="无" value=""></el-option>
          <el-option label="反映社会现实" value="反映社会现实"></el-option>
          <el-option label="思考人生哲学" value="思考人生哲学"></el-option>
          <el-option label="反映底层人民现实" value="反映底层人民现实"></el-option>
          <el-option label="追寻自我价值" value="追寻自我价值"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit()" class="firstSearch"
          >查询</el-button>
      </el-form-item>
    </el-form>

    <el-input
      v-model="input"
      placeholder="请输入书名进行搜索"
      class="searchBox"
    ></el-input>
    <el-button
      type="primary"
      icon="el-icon-search"
      class="searchButton"
      @click="inittable()"
      >搜索</el-button
    >

    <el-table :data="tableDataEnd" style="width: 70%" class="detail">
      <el-table-column prop="books_id" label="ID" width="120">
      </el-table-column>
      <el-table-column prop="books_name" label="书名" width="120">
      </el-table-column>
      <el-table-column prop="books_author" label="作者" width="120">
      </el-table-column>
      <el-table-column prop="books_last" label="库存" width="120">
      </el-table-column>
      

      
      <el-table-column
        prop="books_create_date"
        label="上架日期"
        width="150">
      </el-table-column>
      <el-table-column
        prop="books_alter_date"
        label="修改日期"
        width="150">
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <!-- <el-button slot="reference" @click="bookDetail(scope.row)"
            >详情</el-button> -->

          <el-button type="text" @click="bookDetail(scope.row)">详情</el-button>

          <el-button type="text" @click="open(scope.row)">借阅</el-button>
        </template>

      </el-table-column>
    </el-table>

    <div>
      <el-pagination
        class="page"
        background
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        layout="prev, pager, next"
        :total="totalItems"
        :page-size="pageSize"
      >
      </el-pagination>
    </div>

    <el-dialog title="详情" :visible.sync="detailVisible" width="30%">
      <el-row label-width="70px" >
        <el-col :span="12"> 国家：{{ this.booksInfo.info_country }} </el-col>
        <el-col :span="12"> 类型: {{ this.booksInfo.info_type }} </el-col>
        <el-col :span="12"> 篇幅：{{ this.booksInfo.info_length }} </el-col>
        <el-col :span="12"> 主题: {{ this.booksInfo.info_theme }} </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import Vue from 'vue'
export default {
  
  data() {
    return {
      gridData: [
        {
          description: "",
        },
      ],
      imgSrc: require("../../assets/img/login-bg.jpg"),
      tableDataEnd: [],
      tableDataBegin: [],
      input: "",
      detailVisible: false,
      totalItems: 0,
      currentPage: 0,
      pageSize: 8,
      booksInfo :{},
      formInline: {
        country: "",
        type: "",
        length: "",
        theme: "",
        
      },
    };
  },
  mounted() {
    this.axios({
      method: "get",
      url:  this.$host+"/books/booksList",
      //url: "/user.json",
      data: {},
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        let array = response.data;
        this.totalItems = array.length;
        for (let i = 0; i < this.totalItems; i++) {
          this.tableDataBegin[i] = array[i].book;
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
    open(row) {
      this.$confirm("你是否确定借阅此书籍?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.axios({
            method: "post",
            url:  this.$host+"/record/insertNotReturn",
            //url: "/user.json",
            data: {
              book_id: row.books_id,
              book_name: row.books_name,
              user_id: window.sessionStorage.getItem("ms_userid"),
              user_name: window.sessionStorage.getItem("ms_username"),
              book_return_state:0, 
              book_lend_date: "2020-11-11 11:11:11"
            },
            headers: {
              "Content-Type": "application/json; charset=UTF-8",
            },
          }).then((response) => {
            if (response.data.state == 406) {
              this.$message({
                type: "warn",
                message: "无法借阅!",
              });
            } else if (response.data.state == 200) {
                this.axios({
      method: "get",
      url:  this.$host+"/books/booksList",
      //url: "/user.json",
      data: {},
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        let array = response.data;
        this.totalItems = array.length;
        for (let i = 0; i < this.totalItems; i++) {
          this.tableDataBegin[i] = array[i].book;
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

              this.$message({
                type: "success",
                message: "已预约借阅!",
              });
            }
          });
        })
        .catch(() => {});
    },

    onSubmit() {
      this.axios({
        method: "post",
        url:  this.$host+"/books/quaryType",
        //url: "/user.json",
        data: {
              info_theme:this.formInline.info_theme,
              info_type:this.formInline.info_type,
              info_length:this.formInline.info_length,
              info_country:this.formInline.info_country
            },
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
      })
      .then((response) => {
          this.tableDataEnd = [];
          this.tableDataBegin = [];
          this.tableDataBegin = response.data.object;
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

    inittable() {
      this.axios({
        method: "get",
        url:  this.$host+"/books/quaryName",
        //url: "/user.json",
        params: {
          name: this.input,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          this.tableDataEnd = [];
          this.tableDataBegin = [];
          this.tableDataBegin = response.data.object;
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
    bookDetail(row) {
      this.axios({
        method: "get",
        url:  this.$host+"/books/detail",
        params: {
          books_id: row.books_id,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
           this.booksInfo = {};
          this.booksInfo = response.data.object.books_info;
          if(this.booksInfo.info_length == 1){
              this.booksInfo.info_length = "短篇";
          }else if(this.booksInfo.info_length == 2){
              this.booksInfo.info_length = "中篇";
          }
          else if(this.booksInfo.info_length == 3){
              this.booksInfo.info_length = "长篇";
          }else{
              this.booksInfo.info_length = "超长篇";
          }
        })
        .catch((error) => {
          console.log(error);
        });
      this.detailVisible = true;
      
      
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      //tableDataBegin不能写成tableDataEnd，不然在没有进行搜索功能的时候，不能进行分页操作，数据丢失
      this.currentChangePage(this.tableDataBegin);
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
  },
};
</script>

<style>
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  /*index: -1;*/
  position: absolute;
}

.firstSearch {
  opacity: 0.7;
}

.demo-form-inline {
  padding: 35px 0 0 0;
}

.searchBox {
  width: 300px;
  padding: 0 0 0 60px;
  margin: 5px;
  opacity: 0.8;
  left:100px;
}
.searchButton {
  margin: 0 0 0 100px;
  border: 0;
  opacity: 0.7;
  
}

.detail {
  left: 160px;
  opacity: 0.8;
  border-radius: 10px;
}
.page {
  padding: 0 0 0 160px;
  margin: 10px;
  opacity: 0.7;
}
</style>

