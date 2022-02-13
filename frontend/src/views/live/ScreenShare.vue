<template>
  <el-dialog
    v-model="state.dialogVisible"
    title="Tips"
    width="30%"
  >
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


  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const router = useRouter()
    // const dialogVisible = ref(false)

    console.log(props.open)
    const state = reactive({
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    return { state }
  }
}
</script>

<style>

</style>
