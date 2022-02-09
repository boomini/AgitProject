<template>
  <el-drawer
    v-model="state.boardVisible"
    :title= "state.title"
    :direction="rtl"
    :before-close="handleClose"
  >
    <div style="border: 1px solid gray;">
      일정
      <el-scrollbar>
        <div class="scrollbar-flex-content">
          <p v-for="item in 50" :key="item" class="scrollbar-demo-item">
            {{ item }}
          </p>
          <!-- <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3" v-for="item in 50" :key="item"/> -->
          <!-- <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
          <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/> -->
        </div>
      </el-scrollbar>
      <el-scrollbar native>
        <div class="scrollbar-flex-content">
          <p v-for="item in 50" :key="item" class="scrollbar-demo-item">
            {{ item }}
          </p>
        </div>
      </el-scrollbar>

    </div>
    <div style="border: 1px solid gray;">
      <div>사진</div>
      <image-page
      :uploaddate = state.uploadDate
      :teamId = state.teamId></image-page>
    </div>
    <div style="border: 1px solid gray;">
      <div>동영상</div>
      <video-page
      :uploaddate = state.uploadDate
      :teamId = state.teamId></video-page>
    </div>
    <div style="border: 1px solid gray;">
      게시판
      <el-scrollbar max-height="400px">
          <p v-for="item in state.articleList" :key="item.id" class="scrollbar-demo-item">
            {{ item.title }}
          </p>
      </el-scrollbar>
    </div>
  </el-drawer>
</template>

<script>
import { reactive, computed, onBeforeMount } from 'vue'
import ImagePage from './image.vue'
import VideoPage from './video.vue'
export default {
  name: 'board',
  components:{
    ImagePage,
    VideoPage
  },
  props: {
    data: {
      type: Object,
    },
    open: {
      type: Boolean,
      default: false,
    },
  },

  setup(props, { emit }) {
    onBeforeMount(() => {
      const teamId = props.teamId
      const uploadDate = props.date
    })
    console.log('ㄴㅁㅇ라ㅣㅁ니;러')
    console.log(props.data)

    const state = reactive({
      boardVisible: computed(() => props.open),
      // teamId: props.teamId,
      // articleList: props.data.articleList,
      // imageList: props.data.imageList,
      // videoList: props.data.videoList,
      // eventResList: props.data.eventResList,
      uploadDate: computed(()=>props.data.uploadDate),
      teamId: computed(() => props.data.teamId),
      articleList: computed(() => props.data.articleList),
      imageList: computed(() => props.data.imageList),
      videoList: computed(() => props.data.videoList),
      eventResList: computed(() => props.data.eventResList),
      YMD: computed(() => props.data.uploadDate.split('-')),
      title: computed(() => `${state.YMD[0]}년 ${state.YMD[1]}월 ${state.YMD[2]}일 게시판`)
    })

    console.log(state.teamId);
    const handleClose = function () {
      emit('closeBoard')
    }

    return { state, handleClose }
  }
}
</script>

<style>
.scrollbar-flex-content {
  display: flex;
}
.scrollbar-demo-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 50px;
  margin: 10px;
  text-align: center;
  border: 1px solid black;
  border-radius: 4px;
  background: var(--el-color-danger-lighter);
  color: var(--el-color-danger);
}
</style>
