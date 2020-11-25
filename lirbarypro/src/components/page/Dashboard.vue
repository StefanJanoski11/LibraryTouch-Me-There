<template>
  <div id="div2">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-1">
                <i class="el-icon-top-left grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ this.summary.currentDayLend }}</div>
                  <div>今日借出统计</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-2">
                <i class="el-icon-top grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ this.summary.currentWeekLend }}</div>
                  <div>今周借出统计</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-3">
                <i class="el-icon-top-right grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ this.summary.currentMonthLend }}</div>
                  <div>今月借出统计</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-1">
                <i class="el-icon-bottom-left grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ this.summary.currentDayReturn }}</div>
                  <div>今日归还统计</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-2">
                <i class="el-icon-bottom grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ this.summary.currentWeekReturn }}</div>
                  <div>今周归还统计</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-3">
                <i class="el-icon-bottom-right grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ this.summary.currentMonthReturn }}</div>
                  <div>今月归还统计</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="4">
       
          <div class="grid-content">
            <el-button type="primary" icon="el-icon-edit" @click="editNotice()"
              >编辑公告</el-button
            >
            <!-- <h3 :model="notice" style="margin: 70px">{{ this.notice }}</h3> -->
          </div>
        
      </el-col>
      <el-col :span="20">
      <div id="div1">
      <p style="text-indent: 2em">{{ this.notice }}</p>
      </div>
      
      </el-col>
    </el-row>

    <el-dialog title="修改公告" :visible.sync="editVisible" width="60%">
      <el-input
        type="textarea"
        :rows="6"
        placeholder="请输入内容"
        v-model="notice"
      >
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onCommit">确 认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Vue from "vue";
import Schart from "vue-schart";
import bus from "../common/bus";
export default {
  name: "dashboard",
  data() {
    return {
      name: localStorage.getItem("ms_username"),
      notice: "",
      editVisible: false,
      summary: {
        currentDayLend: 0,
        currentWeekLend: 0,
        currentMonthLend: 0,
        currentDayReturn: 0,
        currentWeekReturn: 0,
        currentMonthReturn: 0,
      },
      
    };
  },
  methods: {
    // 编辑操作
    editNotice() {
      this.editVisible = true;
    },
    onCommit() {
      //修改公告

      Vue.axios({
      method: "post",
      url: "http://10.10.102.142:8080/record/addNotice",
      params:{
        notice:this.notice
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
      this.editVisible = false;
    },
  },
  mounted() {
    //获取统计
    Vue.axios({
      method: "get",
      url: "http://10.10.102.143:8080/record/getStatistic",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        console.log(response);
        this.summary.currentDayLend = response.data.object.currentDayLend;
        this.summary.currentWeekLend = response.data.object.currentWeekLend;
        this.summary.currentMonthLend = response.data.object.currentMonthLend;
        this.summary.currentDayReturn = response.data.object.currentDayReturn;
        this.summary.currentWeekReturn = response.data.object.currentWeekReturn;
        this.summary.currentMonthReturn = response.data.object.currentMonthReturn;
        console.log(this.summary.dayReturn);
        console.log(response.data.dayReturn);
      })
      .catch((error) => {
        console.log(error);
    });
    //获取公告
    Vue.axios({
      method: "get",
      url: "http://10.10.102.142:8080/record/quaryNotice",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.notice = response.data.object.notice_text;
        console.log(this.notice);
        
      })
      .catch((error) => {
        console.log(error);
    });
  },
};
</script>


<style scoped>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 60px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 10px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 10px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

#div1 {
  background-color: rgba(207, 235, 247, 0.8);
  height: 166px;
  width: 95%;
}

#div2{
  height: 100%;
  // background-image: url("../../assets/img/login-bg.jpg");
}

</style>
