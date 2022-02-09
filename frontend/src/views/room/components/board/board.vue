<template>
  <el-drawer
    v-model="state.boardVisible"
    :title= "state.title"
    :direction="rtl"
    :before-close="handleClose"
  >
    <div class="mb-4">
      <h4>
        오늘의 일정
      </h4>
      <div v-if="state.eventResList.length >= 1">
        <el-scrollbar>
          <div>
            <p v-for="(item, index) in state.eventResList" :key="item" class="scrollbar-demo-item">
              <span>
                {{ index + 1 }}. {{ item.eventTitle }}
              </span>
              <span v-if="data.uploadDate === item.endDate">
                D-day
                <!-- {{ data.uploadDate }}
                {{ item.startDate }}
                {{ item.endDate }}
                {{ item.endDate}}
                {{ item.dday }} -->
              </span>
              <span v-else>
                약속 시작
              </span>
            </p>
          </div>
        </el-scrollbar>
      </div>
      <div v-else>
        <el-empty :image-size="100" description="약속이 없어요."/>
      </div>
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
    <div>
      <h4>
        게시판
      </h4>
      <div style="margin: 10px;">
        <el-table :data="state.articleList" height="40vh" style="width: 100%">
          <el-table-column prop="index" label="글번호" width="80" />
          <el-table-column prop="title" label="글제목" width="180" />
          <el-table-column prop="content" label="글내용" />
        </el-table>
      </div>
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
    const state = reactive({
      boardVisible: computed(() => props.open),
      // teamId: props.teamId,
      // articleList: props.data.articleList,
      // imageList: props.data.imageList,
      // videoList: props.data.videoList,
      // eventResList: props.data.eventResList,
      uploadDate: computed(()=>props.data.uploadDate),
      teamId: computed(() => props.data.teamId),
      articleList: computed(function () {
        let articleList = props.data.articleList
        for (let i = 0; i < articleList.length; i++) {
          articleList[i]['index'] = i + 1
        }
        return articleList
      } ),

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
  width: calc(100% - 20px);
  height: 50px;
  margin: 10px;
  text-align: center;
  border: 1px solid black;
  border-radius: 4px;
  background: var(--el-color-danger-lighter);
  color: var(--el-color-danger);
}
.el-drawer {
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
}

h4 {
  overflow: hidden;
  text-align: center;
}

h4:before,
h4:after {
  background-color: #000;
  content: "";
  display: inline-block;
  height: 1px;
  position: relative;
  vertical-align: middle;
  width: 50%;
}

h4:before {
  right: 0.5em;
  margin-left: -50%;
}

h4:after {
  left: 0.5em;
  margin-right: -50%;
}
</style>
