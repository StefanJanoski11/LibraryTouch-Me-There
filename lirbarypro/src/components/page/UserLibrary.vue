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
        <el-select v-model="formInline.region" placeholder="国家">
          <el-option label="中国" value="china"></el-option>
          <el-option label="日本" value="japan"></el-option>
          <el-option label="北美" value="noramerica"></el-option>
          <el-option label="欧洲" value="europe"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="类型">
        <el-select v-model="formInline.sort" placeholder="类型">
          <el-option label="历史" value="history"></el-option>
          <el-option label="文学" value="literature"></el-option>
          <el-option label="军事" value="military"></el-option>
          <el-option label="科学" value="science"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="篇幅">
        <el-select v-model="formInline.para" placeholder="篇幅">
          <el-option label="短篇" value="short"></el-option>
          <el-option label="中篇" value="mid"></el-option>
          <el-option label="长篇" value="long"></el-option>
          <el-option label="超长篇" value="splong"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="主题">
        <el-select v-model="formInline.topic" placeholder="主题">
          <el-option label="一" value="one"></el-option>
          <el-option label="二" value="two"></el-option>
          <el-option label="三" value="three"></el-option>
          <el-option label="四" value="four"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit()" class="firstSearch"
          >查询</el-button
        >
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

    <el-table :data="tableDataEnd" style="width: 90%" class="detail">
      <el-table-column
        fixed
        prop="books_createTime"
        label="上架日期"
        width="150"
      >
      </el-table-column>
      <el-table-column prop="books_name" label="书名" width="120">
      </el-table-column>
      <el-table-column prop="books_author" label="作者" width="120">
      </el-table-column>
      <el-table-column prop="books_last" label="库存" width="120">
      </el-table-column>
      <el-table-column prop="books_publisherId" label="出版社" width="300">
      </el-table-column>
      <el-table-column prop="books_id" label="ID" width="120">
      </el-table-column>
      <el-table-column fixed="right" label="详情" width="120">
        <el-popover
          placement="left"
          width="600px"
          trigger="click"
          
        >
          <el-table :data="gridData">
            <el-table-column width="600" label="描述">
              <!-- 国家：{{info_country}} 类型：{{books_type}}<br>
             篇幅：{{info_length}} 主题：{{info_theme}} -->
            </el-table-column>
          </el-table>
          <template slot-scope="scope">
          <el-button slot="reference" @click="bookInfo(scope.row)">详情</el-button>
          </template>
        </el-popover>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="120">
        <!-- scope就相当于是tableData的一行,scope.row 就能拿到整行的值，scope.$index就能代表当前行的下标 -->
        <template slot-scope="scope">
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
  </div>
</template>


<script>
export default {
  data() {
    return {
      gridData: [
        {
          description: "",
        },
      ],
      imgSrc: require("../../assets/img/2.jpg"),
      tableDataEnd: [],
      tableDataBegin: [],
      input: "",
      totalItems: 0,
      currentPage: 0,
      pageSize: 8,
      formInline: {
        user: "",
        region: "",
        sort: "",
        para: "",
        tpic: "",
        name,
      },
    };
  },
  mounted() {
    this.axios({
      method: "get",
      url: "http://10.10.102.143:8080/books/BooksList",
      //url: "/user.json",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        console.log(response);
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
      // console.log(row.books_id);
      // console.log(row);
      // console.log(window.sessionStorage.getItem("ms_userid"));
      this.$confirm("你是否确定借阅此书籍?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.axios({
            method: "post",
            url: "http://10.10.102.143:8080/record/insertNotReturn",
            //url: "/user.json",
            data: {
              "book_id": row.books_id,
              "book_name": row.books_name,
              "user_id": window.sessionStorage.getItem("ms_userid"),
              "user_name": window.sessionStorage.getItem("ms_username"),

            },
            headers: {
              "Content-Type":
                "application/json; charset=UTF-8",
            }
          })
          .then((response) => {
        console.log(response);
        if(response.data.state == 406){
            this.$message({
            type: "warn",
            message: "无法借阅!",
          });
        }else if(response.data.state == 200){
          this.$message({
            type: "success",
            message: "已预约借阅!",
          });
        }
      })
        })
        .catch(() => {
          
        });
    },

    onSubmit() {
      console.log("submit!");
    },

    inittable() {
      this.axios({
        method: "get",
        url: "http://10.10.102.142:8080/books/quaryName",
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
          console.log(response);
          this.tableDataBegin = response.data.object;
          console.log(this.tableDataBegin);
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
    bookInfo(row) {
      this.axios({
        method: "get",
        url: "http://10.10.102.143:8080/books/getBookAllInfo",
        //url: "/user.json",
        params: {
          id:row.book_id
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        },
      })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
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
  z-index: -1;
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
}
.searchButton {
  margin: 0;
  border: 0;
  opacity: 0.7;
}

.detail {
  left: 60px;
  opacity: 0.8;
  border-radius: 10px;
}
.page {
  padding: 0 0 0 65px;
  margin: 10px;
  opacity: 0.8;
}
</style>

