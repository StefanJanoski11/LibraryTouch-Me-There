<template>
   <el-card shadow="hover" style="height:400px;width:100%" ref="elCard" v-bind:style="elCard">

</el-card>
 

</template>>


<script>
  export default {
    data() {
        return {
            elCard: {
                background: "#c32623"
            },
            level: 1,
            hexArr: ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e']
        }
    },
 

    created() {
        bus.$emit('showMonitor', this.nav);
        this.flashColor();
    },
 

    methods: {
        flashColor() {
            var color="#FF00";
            this.elCard.background = color+this.hexArr[this.level]+this.hexArr[this.level];
            console.log(this.elCard.background);
            this.level++;
            if(this.level >= 16){
                clearTimeout(this.flashColor);
            }else{
                setTimeout(this.flashColor,300);
            }
        }
    }
}
</script>