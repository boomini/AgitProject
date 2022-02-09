<template>
  <el-scrollbar>
        <div class="scrollbar-flex-content">
          <p v-for="item in state.size" :key="item" class="scrollbar-demo-video-item">
            <div class="demo-image__preview">
              <video width="450" height="300" controls>
            <source type="video/mp4">
          </video>
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
        state.size = result.data.length;
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
.scrollbar-demo-video-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 450px;
  height: 250px;
  margin: 1px;
  text-align: center;
  border: 0px solid black;
  border-radius: 4px;
  background: var(--el-color-danger-lighter);
  color: var(--el-color-danger);
}
</style>
