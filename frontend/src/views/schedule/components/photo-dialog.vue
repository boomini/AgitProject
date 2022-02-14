<template>
  <div>
    <el-dialog
    custom-class="photo-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        사진 등록
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 150px;">
      <div style="border: 1px solid black; background-color: black; border-radius: 5px; font-size: 20px; padding: 5px 1rem;">
        이미지를 등록해주세요.
      </div>
      <div class="d-flex justify-content-between">
        <input
            type="file"
            accept="image/*"
            multiple
            @change="selectedImage"
            ref="inputImage"
          />
        <div v-for="img in state.form.images" width=150 :key="img.name">
          <img :src="img.preview" alt="" width=100>
        </div>
      </div>
    </div>
    <!-- footer -->
    <template #footer>
      <span>
        <el-button @click="editMyPhoto">사진 수정</el-button>
      </span>
    </template>
  </el-dialog>
  </div>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'photo-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
  },
  setup(props, { emit }) {
    const inputImage = ref(null)
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      form: {
        images: [],
      },

    })
    const handleClose = function () {
      emit('closePhotoDialog')
    }

    // 사진 등록 함수
    const selectedImage = function () {
      console.log('이미지등록')
      console.log(URL.createObjectURL(inputImage.value.files[0]))
      // console.log(inputImage.value);
      console.log(state.form.images)
      state.form.images.push({
        file: inputImage.value.files[0],
        preview: URL.createObjectURL(inputImage.value.files[0])
      })
      // blob:http://localhost:8083/e8fade0c-2908-4ac6-9ea8-f322fd0da008
      console.log(state.form.images[0].preview.slice(5,))
    }


    // 개인 이미지 등록
    // const uploadImage = function(){
    //     let formData = new FormData();
    //     for (let i=0; i<state.form.images.length; i++){
    //       formData.append('upfile', state.form.images[i].file);
    //     }
    //     // formData.append('uploadDate',state.form.schedule);
    //     // formData.append('teamId',props.info)

    //     // store.dispatch('root/uploadImage',{ 'formData': formData, 'token': state.isLogin})
    //   .then(res => {
    //       setTimeout(() => {
    //             swal({
    //               title: '사진 등록',
    //               text: '사진이 일정에 등록되었습니다.',
    //               icon: 'success',
    //               button: '확인',
    //             });
    //           }, 500)

    //           // router.go(router.currentRoute)
    //           console.log(res)
    //           // console.log(swal)
    //           // emit('createImage')
    //           handleClose()
    //     })
    //     .catch(err => {
    //       console.log(err)
    //     })
    //   }

    return { store, router, state, handleClose, selectedImage, inputImage }

  }
}
</script>

<style>
  .photo-dialog {
    width: 700px;
    height: 300px;
  }
</style>
