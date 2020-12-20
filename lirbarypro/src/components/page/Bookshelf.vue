<template>
<div id="bs">
  <el-row >
  <el-col :span="8" v-for="(book,index) in books" :key="index"  >
    <el-card :body-style="{ padding: '50px'}" shadow="hover"  >
    <div class="pp1">
        <el-image :src="$host + book.books_pic" class="image" slot="reference"></el-image>
      <!-- </el-popover> -->
    </div>
      
      <div style="padding: 14px;">
        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title" class="itembg">
              　 　 　 　 　 　 《{{book.record.book_name}}》
            </template>
            <div>期望还书日期：{{book.record.book_scheduled}}</div>
            <div>借书日期：{{book.record.book_lend_date}}</div>
            <el-button type="text" class="button" @click="returnbook(index)">还书</el-button>
          </el-collapse-item>
        </el-collapse>
        <div class="bottom clearfix">
          
          
        </div>
      </div>
    </el-card>
  </el-col>
</el-row>
</div>
  
</template>


<script>
import Vue from "vue";
export default {
  data() {
    return {
      books:[],
      //img : '/img/default.jpg',
      currentDate: new Date()
    };
  },
 mounted() {
   let userid = window.sessionStorage.getItem("ms_userid");
   Vue.axios({
      method: "get",
      url:  this.$host+"/record/getNRById",
      params: {id : userid},
      headers: {
      // "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
    .then((response) => {
        this.books = response.data;
        // for(var i=0;i<this.books.length;i++){
        //   this.books[i].record.book_scheduled = new Date(this.books[i].record.book_lend_date.getTime()+24*60*60*1000*30);
        // }
    })
    .catch((error) => {
        this.$message.error("错误");
    });
 },
  methods:{
    
    returnbook(index){
      this.$confirm("你是否确定归还此书籍?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
           Vue.axios({
      method: "post",
      url:  this.$host+"/record/returnBook",
      data : JSON.stringify(this.books[index].record),
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },

    })
    .then((response) => {
        this.books.splice(index, 1) ;
    })
    .catch((error) => {
        this.$message.error("错误");
    });
        });
     
      
    }
  }
 
}
</script>

<style>
 #bs{
  background-color:skyblue;
  opacity: 50%;
  background-size: 100%;
  height: 780px;
  }
.card{
  background-color:sienna;
  border:2px rebeccapurple;
}
.bookself-wrap {
    width: 100%;
    height: 100%;
    background-repeat: no-repeat;
    background-size: cover;
    background-size: 100%;
}
  .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }
  .button {
    padding: 0;
    float: right;
  }
  .image {
    position:relative;
    width: 342.33px;
    height:350px;
    display: block;
  }
  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>
