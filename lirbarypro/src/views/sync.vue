<template>
 <div>
 <comp :money.sync="total"></comp> 
 </div>
</template>
<script>
import Vue from "vue";
Vue.component("comp", {
 template: `<div>
      {{ money }}
      <button @click="$emit('update:money':money-100)">cost</button> //定义新的事件监听
</div>`,
 props: ["money"], //子组件的外部属性
});  //子组件
export default {
 data() {
 return { total: 1000 };
  },
};
</script>