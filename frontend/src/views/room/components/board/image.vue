<template>
  <el-scrollbar>
        <div class="scrollbar-flex-content">
          <p v-for="item in state.size" :key="item" class="scrollbar-demo-image-item">
            <div class="demo-image__preview">
              <el-image
                style="width: 300px; height: 250px"
                :src='state.srcList[item]'
                :preview-src-list="state.srcList"
                fit="fill"
              >
              </el-image>
            </div>
          </p>
        </div>

      </el-scrollbar>
</template>

<script>
import {onBeforeMount,reactive} from 'vue'
import { useStore } from 'vuex'
export default {
  props:{
    uploaddate:{
      type:String
    },
    teamId:{
      type:Number
    }
  },
  setup(props){
    const store = useStore()
    const state = reactive({
      size:'',
      srcList:[],
    })

    console.log(props.uploaddate);
    console.log(props.teamId);

    onBeforeMount(()=>{
      store.dispatch('root/getListImage', {'uploadDate':props.uploaddate,'teamId':props.teamId})
      .then(function (result){
        console.log(result.data);
        console.log(result.data.length);
        state.srcList = result.data;
        state.size = result.data.length-1;
        if(state.size==-1){
          state.size = 0;
        }
      }).catch(function(error){
        console.log(error.response);
      })

      console.log(state.srcList);
    })
    return {state};
  }
}
</script>

<style>
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
.scrollbar-demo-image-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 250px;
  margin: 1px;
  text-align: center;
  border: 0px solid black;
  border-radius: 4px;
  background: var(--el-color-danger-lighter);
  color: var(--el-color-danger);
}
</style>
