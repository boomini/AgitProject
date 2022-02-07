<template class="errorbody container">
        <div class='c'>
            <div class='_404'>Agit 초대장</div>
            <hr>
            <div class='_2'> {{state.team.teamName}}에서 </div>
            <div class='_2'> {{state.team.teamBoss}} 님이 당신을 초대합니다. </div>
            <div class='_2'>초대에 응하시겠습니까?</div>
            <a class='btn' @click = "clickacceptbtn()">수락</a>
            <a class='btn' @click = "clickrejectbtn()">거절</a>
        </div>
</template>

<script>
import {onBeforeMount, reactive,computed} from 'vue'
import { useRoute, useRouter} from 'vue-router'
import { useStore } from 'vuex'
export default {
props:{
  roomId:{
    type: Number,
  }
},
setup() {
  const store = useStore()
  const route = useRoute();
  const router = useRouter();
  const state = reactive({
      team: {
        teamName: '',
        teamBoss: '',
      },
      isLogin: computed(() => store.getters['root/getJWTToken'])
    })
  const getTeamDetail = function(){
      store.dispatch('root/getTeamInfoDetail', route.params.roomId)
      .then(function(result){
        console.log(result.data);
        state.team = result.data;
        console.log(state.team.teamName);
        console.log(state.team.teamBoss);
      })
    }

  const clickrejectbtn = function(){
    console.log('check');
    store.dispatch('root/rejectTeamMember', {teamId: route.params.roomId, token:state.isLogin} )
      .then(function(result){
        console.log(result.data);
        console.log(result);
        setTimeout(() => {
                swal({
                  title: "초대거절",
                  text: "초대를 거절하였습니다.",
                  icon: "success",
                  button: "확인",
                });
              }, 500)
        router.push({
          name: 'intro'
      })
      }).catch(function(err){
        console.log(err.response)

      })

  }

  const clickacceptbtn = function(){
    console.log('check');
    store.dispatch('root/confirmTeamMember', {teamId: route.params.roomId, token:state.isLogin} )
      .then(function(result){
        console.log(result.data);
        console.log(result);
        setTimeout(() => {
                swal({
                  title: "초대승인",
                  text: "초대를 승인하였습니다.",
                  icon: "success",
                  button: "확인",
                });
              }, 500)
        router.push({
          name: 'room-board',
          params: {
            roomId: route.params.roomId,
          },
      })
      }).catch(function(err){
        console.log(err.response)

      })
  }

    onBeforeMount(()=>{
      console.log(route.params.roomId);
      getTeamDetail();
    })

    return {getTeamDetail, state,clickacceptbtn,clickrejectbtn};
  }
}
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Tangerine);
        .errorbody{
                background: #33cc99;
                color:#fff;
                font-family: 'Open Sans', sans-serif;
                max-height:700px;
                overflow: hidden;
            }
            .c{
                text-align: center;
                display: block;
                position: relative;
                width:80%;
                margin:100px auto;
            }
            ._404{
                font-size: 80px;
                position: relative;
                display: inline-block;
                z-index: 2;
                height: 120px;
                letter-spacing: 15px;
            }
            ._1{
                text-align:center;
                display:block;
                position:relative;
                letter-spacing: 12px;
                font-size: 4em;
                line-height: 80%;
            }
            ._2{
                text-align:center;
                display:block;
                position: relative;
                font-size: 20px;
            }
            .text{
                font-size: 70px;
                text-align: center;
                position: relative;
                display: inline-block;
                margin: 19px 0px 0px 0px;
                /* top: 256.301px; */
                z-index: 3;
                width: 100%;
                line-height: 1.2em;
                display: inline-block;
            }


            .btn{
                background-color: rgb( 255, 255, 255 );
                position: relative;
                display: inline-block;
                width: 358px;
                padding: 5px;
                z-index: 5;
                font-size: 25px;
                margin:0 auto;
                color:#33cc99;
                text-decoration: none;
                margin-right: 10px
            }
            .right{
                float:right;
                width:60%;
            }

            hr{
                padding: 0;
                border: none;
                border-top: 5px solid #fff;
                color: #fff;
                text-align: center;
                margin: 0px auto;
                width: 420px;
                height:10px;
                z-index: -10;
            }

            hr:after {
                content: "\2022";
                display: inline-block;
                position: relative;
                top: -0.75em;
                font-size: 2em;
                padding: 0 0.2em;
                background: #33cc99;
            }



            .x1 {
                top:-50px;
                left:100px;
                -webkit-transform: scale(0.3);
                -moz-transform: scale(0.3);
                transform: scale(0.3);
                opacity: 0.9;
                -webkit-animation: moveclouds 15s linear infinite;
                -moz-animation: moveclouds 15s linear infinite;
                -o-animation: moveclouds 15s linear infinite;
            }

            .x1_5{
                top:-80px;
                left:250px;
                -webkit-transform: scale(0.3);
                -moz-transform: scale(0.3);
                transform: scale(0.3);
                -webkit-animation: moveclouds 17s linear infinite;
                -moz-animation: moveclouds 17s linear infinite;
                -o-animation: moveclouds 17s linear infinite;
            }

            .x2 {
                left: 250px;
                top:30px;
                -webkit-transform: scale(0.6);
                -moz-transform: scale(0.6);
                transform: scale(0.6);
                opacity: 0.6;
                -webkit-animation: moveclouds 25s linear infinite;
                -moz-animation: moveclouds 25s linear infinite;
                -o-animation: moveclouds 25s linear infinite;
            }

            .x3 {
                left: 250px; bottom: -70px;

                -webkit-transform: scale(0.6);
                -moz-transform: scale(0.6);
                transform: scale(0.6);
                opacity: 0.8;

                -webkit-animation: moveclouds 25s linear infinite;
                -moz-animation: moveclouds 25s linear infinite;
                -o-animation: moveclouds 25s linear infinite;
            }

            .x4 {
                left: 470px; botttom: 20px;

                -webkit-transform: scale(0.75);
                -moz-transform: scale(0.75);
                transform: scale(0.75);
                opacity: 0.75;

                -webkit-animation: moveclouds 18s linear infinite;
                -moz-animation: moveclouds 18s linear infinite;
                -o-animation: moveclouds 18s linear infinite;
            }

            .x5 {
                left: 200px; top: 300px;

                -webkit-transform: scale(0.5);
                -moz-transform: scale(0.5);
                transform: scale(0.5);
                opacity: 0.8;

                -webkit-animation: moveclouds 20s linear infinite;
                -moz-animation: moveclouds 20s linear infinite;
                -o-animation: moveclouds 20s linear infinite;
            }

            @-webkit-keyframes moveclouds {
                0% {margin-left: 1000px;}
                100% {margin-left: -1000px;}
            }
            @-moz-keyframes moveclouds {
                0% {margin-left: 1000px;}
                100% {margin-left: -1000px;}
            }
            @-o-keyframes moveclouds {
                0% {margin-left: 1000px;}
                100% {margin-left: -1000px;}
            }
</style>
