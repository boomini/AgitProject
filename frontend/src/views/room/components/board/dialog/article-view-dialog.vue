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
            최초 작성일 : {{ info.createdTime }}
          </p>
          <p>
            최종 수정일 : {{ info.updatedDate }}
          </p>
        </div>
      </div>

      <div style="border-top: 1px solid black; padding-top: 1.5rem;">
        <p style="font-size: 20px;">
          {{ info.content }}
        </p>
      </div>
    </div>
    <!-- footer -->
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

    return { state, handleClose }
  }

}
</script>

<style>
  .birthday-dialog {
    width: 700px;
    height: 300px;
  }

.el-overlay-dialog {
  background-color: rgba(0,0,0,.5) !important;
}

.el-dialog,
.el-dialog__body,
.el-dialog__body div {
  background-color: white !important;
}
/*
.el-dialog__body {
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
} */

.el-dialog__footer {
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  background-color: white !important;
}

.el-dialog__footer > div {
  background-color: white !important;
}

</style>
