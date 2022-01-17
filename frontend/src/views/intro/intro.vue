<template>
    <div data-aos="fade-in">이거는</div>
</template>


<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'



export default {
  name: 'Intro',
  setup () {
    const store = useStore()

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'intro')

    })
  }
}
</script>

<style>

</style>
