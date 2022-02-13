<template>
  <el-table :data="state.articleTable" style="width: 100%" @cell-click="onCellClick">
    <template #empty>
      <img :src="require(`@/assets/images/article-empty.jpg`)">
      <p>텅</p>
      새 게시글을 작성해보세요.<br>
    </template>
    <el-table-column prop="index" label="글번호" width="80"/>
    <el-table-column prop="title" label="글제목" width="180" />
    <el-table-column prop="writer" label="작성자" />
  </el-table>
  <el-pagination layout="prev, pager, next" :total="articleList.length" :page-size="state.perPage" @current-change="setCurrentPage" class="mt-3" hide-on-single-page></el-pagination>
  <article-view-dialog
    :open="state.articleViewOpen"
    :info="state.article"
    @closeArticleView="onCloseArticleView"/>
</template>

<script>
import { computed, reactive } from 'vue'
import ArticleViewDialog from './dialog/article-view-dialog.vue'

export default {
  name: 'article',
  components: {
    ArticleViewDialog,
  },
  props: {
    articleList:{
      type: Array
    },
  },
  setup(props){
    const state = reactive({
      currentPage: 1,
      perPage: 5,
      articleTable: computed(function () {
        return props.articleList.slice(state.perPage * (state.currentPage -  1), state.perPage * state.currentPage)
      }),
      articleViewOpen: false,
      article: {
        'id': '',
        'title': '',
        'content': '',
        'writer': '',
        'teamName': '',
        'uploadDate': '1970-01-01',
        'updatedDate': new Date(),
        'createdTime': new Date(),
        'index': ''
      }
    })

    const setCurrentPage = function (val) {
      state.currentPage = val
    }

    const onCellClick = function (val) {
      state.article = val
      state.articleViewOpen = true
    }

    const onCloseArticleView = function () {
      state.articleViewOpen = false
    }

    return { state, setCurrentPage, onCellClick, onCloseArticleView }
  }
}
</script>

<style>
.el-table {
  border-radius: 10px;
  border: 1px solid black;
  --el-table-row-hover-background-color: #fff9e4;
}

.el-table tr th .cell{
  text-align: center;
}

.el-table .cell {
  background-color: transparent;
}

.el-table .el-table__body tr:hover {
  transition: 0.1s;
  font-size: 15px;
}

.el-pagination {
  text-align: center;
  --el-pagination-background-color: transparent;
  --el-pagination-hover-color: #f1c900;
}

.el-pagination button:disabled {
  background-color: transparent;
}

.el-pager li.active {
  color: #fae164;
  text-shadow:
    -1px -1px 0 #fae164,
    1px -1px 0 #a0a0a0,
    -1px 1px 0 #a0a0a0,
    1px 1px 0 #a0a0a0;
}

.el-table__empty-text {
  width: 100%;
  background-color: white !important;
}

.el-table__empty-text p {
  font-size: 50px;
  margin: 5px auto 5px;
  font-weight: bold;
}

.el-table__empty-text img {
  float: none;
  height: 20vh;
  margin: 10px;
}
</style>
