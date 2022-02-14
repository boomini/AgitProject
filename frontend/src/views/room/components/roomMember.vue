<template>


  <div id="memberboard" class="col-2 d-flex flex-column justify-content-start" style="border: 1px solid; border-color:#dadadb; border-radius: 10px; margin-top: 85px;margin-bottom: 180px;">
    <div style="text-align: center; margin-bottom: 1rem" class="d-flex justify-content-center mt-3">
      <div>
        <el-button style="width: 100%;" @click="state.inviteDialogOpen = true">
          초대하기
        </el-button>
      </div>
      <div>
        <el-button style="width: 100%" @click="joinConference(state.teamId)">
          회의하기
        </el-button>
      </div>
    </div>
      <div>
        <!-- <div>멤 버</div> -->
        <div v-for="member in state.teamMembers" :key="member.id" class="d-flex align-items-center">
          <div class="d-flex align-items-center mt-2" style="margin-left:10px">
            <el-avatar :size="43" :src=member.profileImg></el-avatar>
            <span style="height: 50px; line-height: 50px; margin-left:10px">
              {{ member.name }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- 초대코드 전송 다이얼로그 -->
  <invite-dialog
    :open="state.inviteDialogOpen"
    :info="state.teamId"
    @closeInviteDialog="onCloseInviteDialog"/>
</template>

<script>
import {reactive, computed, onBeforeMount} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import InviteDialog from './invite-dialog.vue'

export default {
  name:'room-member',
  components:{
    InviteDialog,
  },
  props:{
    teamId:{
      type: Number,
    }
  },
  setup(props){
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      teamMembers: null,
      teamId: computed(()=> props.teamId),
      inviteDialogOpen: false,
    })
    function takeMember () {
      const token = store.getters['root/getJWTToken']
      const body = {
        'teamId': state.teamId
      }
      store.dispatch('root/takeMember', {'body': body, 'token': token})
      .then(res=> {
        console.log(res.data)
        state.teamMembers = res.data
        for(let i=0; i<state.teamMembers.length; i++){
          if(state.teamMembers[i].profileImg==null){
            state.teamMembers[i].profileImg = 'http://localhost:8080/api/v1/user/profileimg/0'
          }
        }
        console.log(state.teamMembers);
        console.log(state.teamMembers[0].name)
      })
      .catch(err => {
        console.log(err)
        // console.log('기달')
      })
    }
    const joinConference = function (roomId) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: roomId
        }
      })
    }

    const onCloseInviteDialog = function () {
      state.inviteDialogOpen = false
    }
    onBeforeMount(()=>{
      takeMember();
    })

    return{onBeforeMount, state, joinConference, onCloseInviteDialog}
  }
}
</script>

<style>
#memberboard{
  /* background-color: #dfdfdf; */
  color:#858687;
}
</style>
