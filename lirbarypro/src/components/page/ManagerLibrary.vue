<template>
  <div class="box">
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
    </div>
    
    <el-form
      :inline="true"
      :model="formInline"
      class="demo-form-inline"
      label-width="60px">
  
      <el-form-item label="国家" >
        <el-select v-model="formInline.region" placeholder="国家" class="searchList">
          <el-option label="中国" value="china"></el-option>
          <el-option label="日本" value="japan"></el-option>
          <el-option label="北美" value="noramerica"></el-option>
          <el-option label="欧洲" value="europe"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="类型">
        <el-select v-model="formInline.sort" placeholder="类型" class="searchList">
          <el-option label="历史" value="history"></el-option>
          <el-option label="文学" value="literature"></el-option>
          <el-option label="军事" value="military"></el-option>
          <el-option label="科学" value="science"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="篇幅">
        <el-select v-model="formInline.para" placeholder="篇幅" class="searchList">
          <el-option label="短篇" value="short"></el-option>
          <el-option label="中篇" value="mid"></el-option>
          <el-option label="长篇" value="long"></el-option>
          <el-option label="超长篇" value="splong"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="主题">
        <el-select v-model="formInline.topic" placeholder="主题" class="searchList">
          <el-option label="一" value="one"></el-option>
          <el-option label="二" value="two"></el-option>
          <el-option label="三" value="three"></el-option>
          <el-option label="四" value="four"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit" class="firstSearch"
          >查询</el-button>
      </el-form-item>

      <el-form-item>
      <el-button type="primary" 
      size="small" @click="addBook=true" icon="el-icon-circle-plus-outline"  class="settingButton"></el-button>
      </el-form-item>

<el-dialog title="书籍上架" :visible.sync="addBook">
  <el-form :model="form">
    <el-form-item label="书　名" >
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="　作　者" >
      <el-input v-model="form.author" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="出版社 " >
      <el-input v-model="form.public" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="上架数量" >
      <el-input v-model="form.counter" autocomplete="off"></el-input>
    </el-form-item>


    <el-form-item label="国家" >
        <el-select v-model="formInline.region" placeholder="国家" class="searchList">
          <el-option label="中国" value="china"></el-option>
          <el-option label="日本" value="japan"></el-option>
          <el-option label="北美" value="noramerica"></el-option>
          <el-option label="欧洲" value="europe"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="类型" >
        <el-select v-model="formInline.sort" placeholder="类型" class="searchList">
          <el-option label="历史" value="history"></el-option>
          <el-option label="文学" value="literature"></el-option>
          <el-option label="军事" value="military"></el-option>
          <el-option label="科学" value="science"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="篇幅" >
        <el-select v-model="formInline.para" placeholder="篇幅" class="searchList">
          <el-option label="短篇" value="short"></el-option>
          <el-option label="中篇" value="mid"></el-option>
          <el-option label="长篇" value="long"></el-option>
          <el-option label="超长篇" value="splong"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="主题" >
        <el-select v-model="formInline.topic" placeholder="主题" class="searchList">
          <el-option label="一" value="one"></el-option>
          <el-option label="二" value="two"></el-option>
          <el-option label="三" value="three"></el-option>
          <el-option label="四" value="four"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="上架日期" :label-width="formLabelWidth">
      <el-input v-model="form.date" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="简介" :label-width="formLabelWidth">
      <el-input v-model="form.detail" autocomplete="off"></el-input>
      </el-form-item>
    
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="addBook = false">取 消</el-button>
    <el-button type="primary" @click="addBook = false">上 架</el-button>
  </div>
</el-dialog>      

    </el-form>

    <el-input
      v-model="input"
      placeholder="请输入内容"
      class="searchBox"
    ></el-input>
    <el-button type="primary" icon="el-icon-search" class="searchButton" @click="inittable()"
      >搜索</el-button>

      

    <el-table :data="tableDataEnd" style="width: 90%" class="detail">
      <el-table-column fixed prop="date" label="上架日期" width="150">
      </el-table-column>
      <el-table-column prop="name" label="书名" width="120"> </el-table-column>
      <el-table-column prop="province" label="作者" width="120">
      </el-table-column>
      <el-table-column prop="city" label="库存" width="120"> </el-table-column>
      <el-table-column prop="address" label="出版社" width="300">
      </el-table-column>
      
<el-table-column prop="city" label="下架" width="120" fixed="right"> 
<el-button icon="el-icon-delete"
          @click="deleteBook"
          type="primary"
          size="small"></el-button>
</el-table-column>

      <el-table-column fixed="right" label="操作" width="120">
        <el-button @click="setting=true"
          type="text"
          size="small">编辑</el-button>
      </el-table-column>
    </el-table>
  
  <el-dialog title="编辑书籍信息" :visible.sync="setting">
  <el-form :model="form">
    <el-form-item label="书名" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="简介" :label-width="formLabelWidth">
      <el-input v-model="form.detail" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="国家" :label-width="formLabelWidth">
        <el-select v-model="formInline.region" placeholder="国家" class="searchList">
          <el-option label="中国" value="china"></el-option>
          <el-option label="日本" value="japan"></el-option>
          <el-option label="北美" value="noramerica"></el-option>
          <el-option label="欧洲" value="europe"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="类型" :label-width="formLabelWidth">
        <el-select v-model="formInline.sort" placeholder="类型" class="searchList">
          <el-option label="历史" value="history"></el-option>
          <el-option label="文学" value="literature"></el-option>
          <el-option label="军事" value="military"></el-option>
          <el-option label="科学" value="science"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="篇幅" :label-width="formLabelWidth">
        <el-select v-model="formInline.para" placeholder="篇幅" class="searchList">
          <el-option label="短篇" value="short"></el-option>
          <el-option label="中篇" value="mid"></el-option>
          <el-option label="长篇" value="long"></el-option>
          <el-option label="超长篇" value="splong"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="主题" :label-width="formLabelWidth">
        <el-select v-model="formInline.topic" placeholder="主题" class="searchList">
          <el-option label="一" value="one"></el-option>
          <el-option label="二" value="two"></el-option>
          <el-option label="三" value="three"></el-option>
          <el-option label="四" value="four"></el-option>
        </el-select>
      </el-form-item>
    
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="setting = false">取 消</el-button>
    <el-button type="primary" @click="setting = false">保 存</el-button>
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
export default {
  data() {
    return {
      addBook:false,
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        formLabelWidth: '120px',
    
      gridData: [{
          description: '全球最大的中文搜索引擎、致力于让网民更便捷地获取信息，找到所求。百度超过千亿的中文网页数据库，可以瞬间找到相关的搜索结果。',
        }],
      imgSrc: require("../../assets/img/3.jpg"),
      tableDataEnd: [],
      tableDataBegin: [
        {
          date: "2016-05-03",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-02",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-08",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-06",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
      ],
      input: "",
      totalItems: 0,
      currentPage: 0,
      pageSize:8,
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
  methods: {
    onSubmit() {
      console.log("submit!");
    },

    inittable(){
      this.tableDataEnd= [],
       this.totalItems = this.tableDataBegin.length;
        if (this.totalItems > this.pageSize) {
          //如果有好多，只需要第一页的数据
          this.currentPage=1;
          for (let index = 0; index < this.pageSize; index++) {
            this.tableDataEnd.push(this.tableDataBegin[index]);
          }
        } else {
          this.tableDataEnd = this.tableDataBegin;
        }
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

.searchList{
  opacity: 0.8;
}

.firstSearch {
  opacity: 0.7;
}

.settingButton{
  opacity: 0.7;
}

.demo-form-inline {
  padding: 35px 0 0 0;
}

.searchBox {
  width: 300px;
  padding:0 0 0 60px;
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

