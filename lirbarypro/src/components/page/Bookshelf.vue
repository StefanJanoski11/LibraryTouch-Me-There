<template>
<div >
  <el-row >
  <el-col :span="8" v-for="(book,index) in books" :key="index"  >
    <el-card :body-style="{ padding: '50px'}" shadow="hover" >
    <div class="pp1">
      
      <el-popover 
        placement="top"
        width="400"
        trigger="click">
          <el-table :data="gridBook" >
            <el-table-column width="100" property="id" label="图书编号" ></el-table-column>
            <el-table-column width="100" property="type" label="图书类型"></el-table-column>
            <el-table-column width="100" property="author" label="图书作者"></el-table-column>
          </el-table>
        <el-image :src="book.img" class="image" slot="reference"></el-image>
      </el-popover>



      

    </div>
      
      <div style="padding: 14px;">



        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title" class="itembg">
              {{book.name}}<i class="header-icon el-icon-info"></i>
            </template>
            <div>{{book.info}}</div>
            
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
export default {
  data() {
    return {
      books:[
        {
          name:'唐诗三百首',
          img: require('../../assets/img/唐诗三百首.png'),
          info: '《唐诗三百首》是一部流传很广的唐诗选集。',
        
        },
        {
          name:'时间简史',
          img: require('../../assets/img/时间简史.png')
        }
      ],
      
     
      currentDate: new Date()
    };
  },
  methods:{
    returnbook(index){
      
      this.books.splice(index, 1) ;
    }
  }
 
}
</script>