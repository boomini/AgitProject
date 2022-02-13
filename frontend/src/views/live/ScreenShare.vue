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
      공유 화면 선택
    </span>
  </template>

  <span>This is a message</span>
  <template #footer>
    <span class="dialog-footer">
      <el-button>Cancel</el-button>
      <el-button type="primary"
        >Confirm</el-button
      >
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
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

    const state = reactive({
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })
    const handleClose = function (){
      emit("closeShareDialog")
    }

    return { state, handleClose }
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
</style>
