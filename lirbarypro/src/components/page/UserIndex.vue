<template>
  <div class="images">
    <el-carousel
      :interval="4000"
      type="card"
      arrow="hover"
      style="height: 350px"
    >
      <el-carousel-item
        v-for="(img, index) in imgList"
        :key="index"
        style="height: 350px"
      >
        <img v-bind:src="img.url" />
      </el-carousel-item>
    </el-carousel>
    
    <div id="div1">
    <p style="text-indent: 2em ">{{ this.notice }}</p>
  </div>
  </div>
  
</template>

<script>
import Vue from "vue";
export default {
  data() {
    return {
      notice: "",
      imgList: [
        { url: require("../../assets/img/4.png") },
        { url: require("../../assets/img/5.png") },
        { url: require("../../assets/img/6.png") },
        { url: require("../../assets/img/7.png") },
        { url: require("../../assets/img/8.png") },
      ],
    };
  },
  methods: {},
  mounted() {
    this.axios({
      method: "get",
      url:  this.$host+"/record/quaryNotice",
      data: "",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      },
    })
      .then((response) => {
        this.notice = response.data.object.notice_text;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style >
  #div1 {
  background-color: rgba(207, 235, 247, 0.8);
  height: 230px;
}
</style>
