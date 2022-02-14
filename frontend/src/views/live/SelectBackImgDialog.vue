
<template>
  <el-dialog
    custom-class="screen-share"
    v-model="state.dialogVisible"
    @close="handleClose"
    title="Tips"
    width="30%"
  >
  <template #title>
    <span>
      배경 화면 선택
    </span>
  </template>
  <el-form :model="state.form" status-icon ref="registerTeamForm" :label-position="state.form.align">
  <p style="text-align:center;"> 배경화면을 변경해보세요.</p>
<div class="d-flex flex-wrap mx-3">
          <div :class="{selectedActive:getActive(index)}" class = "d-flex flex-column align-items-center mx-3 my-2 backimglist" v-for="(item, index) in state.imageList"
          :key="item">
            <img :src=item style="width:150px; height:100px;"  @click="imageSelect(index,item)"/>
          </div>
          <div class = "d-flex flex-column align-items-center mx-3 my-2">
            <el-upload
              class="avatar-uploader-backimg"
              action="https://jsonplaceholder.typicode.com/posts/"
              ref="inputImage"
              :before-upload="handleUploadbefore"
              :on-success="handleAvatarSuccess"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar"/>
              <div v-else class="baseicon"><i class="el-icon-plus"></i></div>
              <!-- <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
              <el-icon class="avatar-uploader-icon"></el-icon> -->
          </el-upload>
          </div>
        </div>

  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="handleClose">취소</el-button>
      <el-button @click="confirmBackImg" type="primary"
        >변경</el-button
      >
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'ScreenShare',

  props: {
    open: {
      type: Boolean,
      default: false
    },
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const inputImage = ref(null)
    const imageUrl = ref('')
    const state = reactive({
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      form: {
        align: 'left',
        teamPicture: null,
      },
      imageList:[
        'http://localhost:8080/dist/img/backImg1.png',
        'http://localhost:8080/dist/img/backImg2.jpg',
        'http://localhost:8080/dist/img/backImg3.PNG'
      ],
      selectedImg:{
        index:-1,
        src:''
      },
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })
    const handleClose = function (){
      emit('closeBackImgDialog')
    }

    const handleAvatarSuccess  = function() {
      console.log(inputImage.value.uploadFiles[0].raw)
      console.log(URL.createObjectURL(inputImage.value.uploadFiles[0].raw));
      imageUrl.value = URL.createObjectURL(inputImage.value.uploadFiles[0].raw);
    }

    const handleUploadbefore = (file)=>{
      console.log(file.name)
      inputImage.value.clearFiles()
      console.log(inputImage.value.uploadFiles[0].raw)
      console.log(URL.createObjectURL(inputImage.value.uploadFiles[0].raw));
      imageUrl.value = URL.createObjectURL(inputImage.value.uploadFiles[0].raw);
      state.form.teamPicture = inputImage.value.uploadFiles[0].raw;
    }
    const imageSelect = function(index,item){
      state.selectedImg.index = index;
      state.selectedImg.src = item;
      console.log(state.selectedImg.src);
    }
    const getActive = function(index){
      if(index==state.selectedImg.index){
        return true;
      }else{
        return false;
      }
    }

    const confirmBackImg = function(){
      emit('backImg',state.selectedImg.src);
      handleClose();
    }
    return { state, handleClose, handleAvatarSuccess, handleUploadbefore, getActive, imageSelect, confirmBackImg }
  }
}
</script>

<style>
  .screen-share{
    position: relative;
    background-color: aliceblue;
    width: 42vw;
    height: 70vh;
    transform: translate(0, -20%);
  }

  /* upload image css */
.avatar-uploader-backimg .el-upload {
  border: 0.5vh dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  text-align: center;
}
.avatar-uploader-backimg .el-upload:hover {
  border-color: #409eff;

}

.baseicon:hover{
  color: #409eff;
}
.baseicon{
  width: 150px;
  height: 100px;
  color: #8c939d;
}
.el-upload i{
  font-size: 28px;
  vertical-align: bottom;
  font-weight: 900;
  font-style: normal;
  position: absolute;
  left:50%;
  top:50%;
  transform: translate(-50%, -50%);
}
.avatar {
  width: 150px;
  height: 100px;
}

.backimglist{
  border-width: 0.5vh;
    border-style: solid;
    border-color: #fff;
}
.backimglist:hover{
    border-color: #409eff;
}
.backimglist:focus{
    border-color: #409eff;
}
.backimglist:active{
    border-color: #409eff;
}
.selectedActive{
  border-color: #409eff;
}
</style>
