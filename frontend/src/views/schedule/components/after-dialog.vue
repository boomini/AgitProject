<template>
  <div v-if="info">
    <el-dialog custom-class="after-dialog" v-model="state.dialogVisible" title="다음 약속" width="30%" style="margin: 50%">
      <h2>팀명: {{ info.teamName }} <span style="font-size: 17px;">(종료 디데이: {{ info.dday }}일)</span></h2>
      <br>
      <h5>약속 제목: {{ info.eventTitle }}</h5>
      <h5>약속 내용: {{ info.eventContent }}</h5>
      <br>
      <p>시작 날짜: {{ info.startDate }}</p>
      <p>종료 날짜: {{ info.endDate }}</p>
      <p>나오냐: {{ info.teamId }}</p>
      <!-- <img src="{{ state.team.teamPicture }}" alt=".."> -->
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleClose()"
            >Confirm</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
  <div v-else-if="info == undefined">
    <el-dialog custom-class="before-dialog" v-model="state.dialogVisible" title="다음 약속" width="30%" style="margin: 50%">
      <p>
      지난 일정이 없습니다.
      </p>
      <br>
      <p>팀 생성 후 일정을 추가할 수 있습니다 <i class="em em-closed_book" aria-role="presentation" aria-label="CLOSED BOOK" style="margin-bottom:4px"></i></p>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleClose()"
            >Confirm</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'


export default {
  name: 'after-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    },
  },
  setup(props, {emit}) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      team: null,
    })

    // const takeTeam = function () {
    //   console.log('ddd')
    //   console.log(props.info.teamId)
    //   store.dispatch('root/getTeamInfoDetail', props.info.teamId)
    //   .then(function(result){
    //     console.log(result.data);
    //     console.log('어디')
    //     state.team.teamPicture = result.data.teamPicture;
    //   })
    // }


    // takeTeam()

  // console.log(state.team.teamPicture)



  // console.log(state.team.teamPicture)
  // console.log('여기일듯')


    const handleClose = function () {
      emit('closeAfterDialog')
    }

    return { state, store, router, handleClose}
  }
}
</script>

<style>

</style>
