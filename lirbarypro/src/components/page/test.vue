<template>
<div >
  <el-row >
  <el-col :span="8" v-for="(book,index) in books" :key="index"  >
    <el-card :body-style="{ padding: '50px'}" shadow="hover" >
    <div class="pp1">
      
      <el-popover 
        placement="left-end"
        width="400"
        trigger="click"  
      >
          <el-table :data="books" >
            <el-table-column width="100" property="book_id" label="图书编号" ></el-table-column>
            <el-table-column width="100" property="book_name" label="图书名字"></el-table-column>
            <el-table-column width="100" property="book_sche duled" label="期望还书日期"></el-table-column>
            <el-table-column width="100" property="book_lend_date" label="借书日期"></el-table-column>
          </el-table>
        <el-image :src="book.img" class="image" slot="reference"></el-image>
      </el-popover>
    </div>
      
      <div style="padding: 14px;">
        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title" class="itembg">
              {{book.book_name}}<i class="header-icon el-icon-info"></i>
            </template>
            <div>{{book.book_lend_date}}</div>
            
          </el-collapse-item>
        </el-collapse>
        <div class="bottom clearfix">
          <time class="time">{{ currentDate }}</time>
          <el-button type="text" class="button" @click="returnbook(index)">还书</el-button>
        </div>
      </div>
    </el-card>
  </el-col>
</el-row>
</div>
  
</template>


<style>
.card{
  background-color:sienna;
  border:2px rebeccapurple;
}
.bookself-wrap {
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
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

<script>
import Vue from "vue";
export default {
  data() {
    return {
      books:[],
      
      
     
      currentDate: new Date()
    };
  },
 mounted() {
   let userid = window.sessionStorage.getItem("ms_userid");
   console.log(userid);
   Vue.axios({
      method: "get",
      url: "http://10.10.102.143:8080/record/getNRById",
      params: {id : userid},
      headers: {
      // "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
    .then((response) => {
        console.log(response);
        this.books = response.data;
    })
    .catch((error) => {
        this.$message.error("错误");
        console.log(error);
    });
 },
  methods:{
    returnbook(index){
      Vue.axios({
      method: "post",
    
      url: "http://10.10.102.143:8080/record/returnBook",
      params: //JSON.stringify(this.books[index]),
      //this.books[index],
      {
        // record : JSON.stringify(this.books[index])
          //record : this.books[index]
        "user_id": this.books[index].user_id,
        "user_name": this.books[index].user_name,
        "book_id": this.books[index].book_id,
        "book_name": this.books[index].book_name,
        "book_return_state": this.books[index].book_return_state,
        "book_return_date": this.books[index].book_return_date,
        "book_lend_date": this.books[index].book_lend_date,
        "book_scheduled": this.books[index].book_scheduled
      },
      headers: {
      "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
      
    })
    .then((response) => {
        
    })
    .catch((error) => {
      console.log(this.books[index]);
        this.$message.error("错误");
        console.log(error);
    });
      this.books.splice(index, 1) ;
    }
  }
 
}
</script>