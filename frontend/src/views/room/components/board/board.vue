<template>
  <el-drawer
    v-model="state.boardVisible"
    :title= "state.title"
    :direction="rtl"
  >
    <div style="border: 1px solid gray; height: 200px;">
      사진
      <div class="d-flex">
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
      </div>
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      동영상
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      TO-DO
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      회의 메모
    </div>
  </el-drawer>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import jwt_decode from 'jwt-decode'

export default {
  name: 'board',

  props: {
    title: {
      type: String,
    }
  },

  setup(props, { emit }) {
    const state = reactive({
      boardVisible: computed(() => props.open),
    })
    return { state }
  }
}
</script>

<style>

</style>
