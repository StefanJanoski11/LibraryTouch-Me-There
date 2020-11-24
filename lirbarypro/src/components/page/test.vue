<template>
<div >
    <el-input v-model="bid" placeholder="请输入内容"></el-input>
    <el-button type="" @click="query()">an </el-button>

  
    <el-image :src="books.books_pic" class="image" slot="reference"></el-image>
     
   
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
      bid: '',
      
      
      
     
      currentDate: new Date()
    };
  },
 mounted() {
   
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
    },

    query(){
       Vue.axios({
      method: "get",
      url: "http://10.10.102.143:9090/books/getBookAllInfo",
      params: {id : this.bid},
      headers: {
      // "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
    .then((response) => {
        console.log(response);
        this.books = response.data.object;
    })
    .catch((error) => {
        this.$message.error("错误");
        console.log(error);
    });
  }
  },
  
 
}
</script>