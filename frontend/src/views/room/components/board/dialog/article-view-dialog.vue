<template>
  <!-- <p>{{ info }}</p> -->
  <el-dialog
    custom-class="article-view-dialog"
    v-model="open"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        게시글 상세보기
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around">
      <h1 style="text-align: center; margin-bottom: 5rem;">{{ info.title }}</h1>
      <div class="d-flex justify-content-between">
        <div>
          <p style="font-size: 1.1rem;">
            {{ info.writer }}
          </p>
        </div>
        <div style="font-size: 40%;">
          <p>
            최초 작성일 : {{ timeFormat(info.createdTime) }}
          </p>
          <p>
            최종 수정일 : {{ timeFormat(info.updatedDate) }}
          </p>
        </div>
      </div>

      <div style="border-top: 1px solid black; padding-top: 1.5rem; height: 370px;">
        <p style="font-size: 20px;">
          {{ info.content }}
          {{ info}}
        </p>
      </div>
    </div>
    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="updateArticle">수정</el-button>
        <el-button>삭제</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import jwt_decode from 'jwt-decode'

export default {
  name: 'article-view-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      userId: computed(() => jwt_decode(store.getters['root/getJWTToken']).sub),
    })

    const handleClose = function () {
      emit('closeArticleView')
    }

    const timeFormat = function (time) {
      const now = new Date().getTime()
      const createdAt = new Date(time)

      const timeDiff = Math.floor((now - createdAt.getTime()) / 1000 / 60)
      const dayDiff = Math.floor(timeDiff / 60)
      if (timeDiff <= 5) return '방금전'
      else if (timeDiff < 60) return `${timeDiff}분 전`
      else if (dayDiff < 24) return `${dayDiff}시간 전`
      else {
        const year = createdAt.getFullYear()
        const month = createdAt.getMonth() + 1
        const day = createdAt.getDate()
        const hour = createdAt.getHours()
        const minute = createdAt.getMinutes()
        return `${year}.${month}.${day} ${hour}:${minute}`
      }
    }

    const updateArticle = function () {
      emit('updateArticle', props.info)
    }

    return { state, handleClose, timeFormat, updateArticle }
  }

}
</script>

<style>
.article-view-dialog {
  width: 700px;
  height: 700px;
}

.article-view-dialog .el-overlay-dialog {
  background-color: rgba(0,0,0,.5) !important;
}

.article-view-dialog .el-dialog,
.article-view-dialog .el-dialog__body,
.article-view-dialog .el-dialog__body div {
  background-color: white !important;
}

.article-view-dialog .el-dialog__body {
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
}

.article-view-dialog .el-dialog__footer {
  background-color: transparent;
}

/* .el-dialog__footer {
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  background-color: white !important;
}

.el-dialog__footer > div {
  background-color: white !important;
} */

</style>
