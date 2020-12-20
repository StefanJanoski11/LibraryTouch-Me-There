<template>
  <div class="box">
    <div class="background">
      
    </div>

    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline"
      label-width="50px"
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
        <el-select v-model="formInline.info_type" placeholder="类型">
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
          <el-option
            label="反映底层人民现实"
            value="反映底层人民现实"
          ></el-option>
          <el-option label="追寻自我价值" value="追寻自我价值"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit" class="firstSearch"
          >查询</el-button
        >
      </el-form-item>

      <el-dialog title="书籍上架" :visible.sync="addBook">
        <el-form :model="form">
          <el-form-item label="书　名">
            <el-input v-model="form.books_name" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="　作　者">
            <el-input v-model="form.books_author" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="上架数量">
            <el-input v-model="form.books_last" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="上传图片" ref="uploadElement" prop="books_img">
            <form enctype="multipart/form-data" id="uploadform">
  	          <input  type="file" id="img_file" @change="upTemp($event)" name="books_img"><br><br>
  	          
            </form>
          </el-form-item>
        </el-form>
        
        <div slot="footer" class="dialog-footer">
          <el-button @click="addBook = false">取 消</el-button>
          <el-button type="primary" @click="uploadBook">添 加</el-button>
        </div>
      </el-dialog>
    </el-form>

    <el-input
      v-model="input"
      placeholder="请输入内容"
      class="searchBox"
    ></el-input>
    <el-button
      type="primary"
      icon="el-icon-search"
      class="searchButton"
      @click="inittable()"
      >搜索</el-button
    >
    <el-button type="primary" icon="el-icon-plus" @click="bookDownload" class="downLoad">批量导出</el-button>
    <el-button type="primary" size="small" @click="addBook = true" icon="el-icon-circle-plus-outline" 
    class="settingButton">添加书籍</el-button>

      <el-table :data="tableDataEnd" style="width: 80%" class="detail">     
      <el-table-column prop="books_name" label="书名" width="120">
      </el-table-column>
      <el-table-column prop="books_author" label="作者" width="120">
      </el-table-column>
      <el-table-column prop="books_last" label="库存" width="100">
      </el-table-column>
      <el-table-column prop="books_state" :formatter="Formate" label="是否上架" width="90">
      </el-table-column>
      <el-table-column prop="books_id" label="ID" width="120">
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

      <el-table-column  label="操作" width="160" fixed="right">
        <template slot-scope="scope">
          <el-button @click="deleteBook(scope.$index, scope.row)" 
          v-if="scope.row.books_state == 0"
          type="primary" size="small" >上架</el-button>
          <el-button @click="deleteBook(scope.$index, scope.row)"
          v-else 
          type="primary" size="small" >下架</el-button>

          <el-button
            @click="clickSetting(scope.$index, scope.row)"
            type="text"
            size="small"
            >编辑</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑书籍信息" :visible.sync="settingVisible">
      <el-form :model="form">

        <el-form-item label="国家" :label-width="formLabelWidth">
        <el-select v-model="ffInline.info_country" placeholder="国家" class=“searchList>
          
          <el-option label="中国" value="中国"></el-option>
          <el-option label="法国" value="法国"></el-option>
          <el-option label="德国" value="德国"></el-option>
          <el-option label="印度" value="印度"></el-option>
        </el-select>
      </el-form-item>

        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="ffInline.info_type" placeholder="类型" class=“searchList>
          
          <el-option label="现实主义" value="现实主义"></el-option>
          <el-option label="哲学主义" value="哲学主义"></el-option>
          <el-option label="社会主义" value="社会主义"></el-option>
          <el-option label="资本主义" value="资本主义"></el-option>
        </el-select>
        </el-form-item>

        <el-form-item label="篇幅" :label-width="formLabelWidth">
        <el-select v-model="ffInline.info_length" placeholder="篇幅" class=“searchList>
          
          <el-option label="短篇" value="1"></el-option>
          <el-option label="中篇" value="2"></el-option>
          <el-option label="长篇" value="3"></el-option>
          <el-option label="超长篇" value="4"></el-option>
        </el-select>
        </el-form-item>

        <el-form-item label="主题" :label-width="formLabelWidth">
          <el-select v-model="ffInline.info_theme" placeholder="主题" class=“searchList>
          
          <el-option label="反映社会现实" value="反映社会现实"></el-option>
          <el-option label="思考人生哲学" value="思考人生哲学"></el-option>
          <el-option label="反映底层人民现实" value="反映底层人民现实"></el-option>
          <el-option label="追寻自我价值" value="追寻自我价值"></el-option>
        </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="settingVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()"
          >保 存</el-button
        >
      </div>
    </el-dialog>

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
import Vue from 'vue'
export default {
  data() {
    return {
      title: "",
      addBook: false,
      form: {
        books_publisherId: window.sessionStorage.getItem("ms_userid"),
        books_name: "",
        books_author: "",
        books_last: "",
        books_img: "",
      },
      formLabelWidth: "120px",
      settingVisible: false,

        // formLabelWidth: '120px',
        // settingVisible:false,
      gridData: [{
          description: '',
        }],
    
      tableDataEnd: [],
      tableDataBegin: [],
      input: "",
      booknum: "",
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
      ffInline:{},
    };
  },
  mounted() {
    this.axios({
      method: "get",
      url:  this.$host+"/books/booksList",
      //url: "/user.json",
      data: "",
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
    Formate(row, index) {
      if (row.books_state == 0) {
        return "否";
      } else {
        return "是";
      }
    },
    bookDownload() {
      this.axios({
        method: "get",
        url:  this.$host+"/books/download",
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
              "_books.xlsx"
          );
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onSubmit() {
      this.axios({
        method: "post",
        url:  this.$host+"/books/quaryType",
        //url: "/user.json",
        data: {
          info_theme: this.formInline.info_theme,
          info_type: this.formInline.info_type,
          info_length: this.formInline.info_length,
          info_country: this.formInline.info_country,
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
    clickSetting(index, row) {
      this.settingVisible = true;     
      this.booknum = row.books_id;
    },

    save(){
      this.settingVisible = false;
      this.axios({
        method: "post",
        url: this.$host+"/books/updateBookType",
        //url: "/user.json",
        data: {
              "info_id":this.booknum,
              "info_theme":this.ffInline.info_theme,
              "info_type":this.ffInline.info_type,
              "info_length":this.ffInline.info_length,
              "info_country":this.ffInline.info_country
            },
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
    })
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
    getfile(e){
      this.file = e.target.files[0];
    },
    upTemp(e){
      e.preventDefault();//取消默认操作
      var bookImg=new FormData();
      this.file=document.getElementById('img_file').files[0];//此方法也可用
      bookImg.append("books_img",this.file);
      this.axios({
              method: "post",
              url: this.$host+"/books/upload",
              data:bookImg,
              headers: {
                 "Content-Type":"multipart/form-data",
              },
            })
              .then((response) => {
                this.form.books_img = response.data.object;
              })
              .catch((error) => {
                console.log(error);
              });
    },
    uploadBook(){
      this.axios({
              method: "post",
              url: this.$host+"/books/insert",
              data:{
                "books_pic":this.form.books_img,
                "books_author":this.form.books_author,
                "books_name":this.form.books_name,
                "books_last":this.form.books_last,
                "books_publisherId":window.sessionStorage.getItem('ms_userid')
              },
              headers: {
                "Content-Type":"application/json; charset=UTF-8"
              },
            })
              .then((response) => {
              })
              .catch((error) => {
                console.log(error);
              });


      this.addBook = false;
    },
    deleteBook(index, row) {
      this.$confirm("你是否确定上下架此书籍?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.axios({
          method: "get",
          url: this.$host+"/books/setBookStatus",
          //url: "/user.json",
          params: {
            bookId: row.books_id,
            status: row.books_state == 1 ? 0 : 1,
          },
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
        })
          .then((response) => {
            //重新加载
            this.axios({
      method: "get",
      url:  this.$host+"/books/booksList",
      //url: "/user.json",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.tableDataBegin = [];
        this.tableDataEnd = [];
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
          })
          .catch(() => {});
      });
    },
  },
};
</script>

<style>
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  /*z-index: -1;*/
  position: absolute;
}

.searchList {
  opacity: 0.8;
}

.downLoad {
  opacity: 0.7;
}

.upLoad {
  opacity: 0.7;
}

.firstSearch {
  opacity: 0.7;
}

.settingButton {
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
  left:65px;
}
.searchButton {
  margin: 0 0 10px 65px;
  border: 0;
  opacity: 0.8;
}

.detail {
  left: 125px;
  opacity: 0.8;
  border-radius: 10px;
}
.page {
  padding: 0 0 0 120px;
  margin: 10px;
  opacity: 0.8;
}
</style>

