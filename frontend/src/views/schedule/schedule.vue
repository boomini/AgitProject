<template>
  <div style="hegint: 80%; margin-bottom: 100px;" >
    <div id="scape">
      <div class="landing">
        <div class="mountain-scene">
          <div id="clouds">
            <div class="cloud"></div>
            <div class="cloud"></div>
            <div class="cloud"></div>
            <div class="cloud"></div>

          </div>
          <!-- background mountains -->
          <div class="small-mountains">
            <div class="tri"></div>
            <div class="tri"></div>
            <div class="tri"></div>
          </div>
          <div class="tall-mountains">
            <div class="tri2"></div>
            <div class="tri2"></div>
            <div class="tri2"></div>
          </div>
          <!-- foreground mountains -->
          <div class="tiny-mountains">
            <div class="tri3"></div>
            <div class="tri3"></div>
            <div class="tri3"></div>
            <div class="tri3"></div>
            <div class="tri3"></div>
          </div>
        </div>
        <div class="ground d-flex justify-content-evenly">
          <div>
            <el-button type="text" @click="centerDialogVisible = true" style="margin-left:100px; font-size: 40px;"
              >이전 약속</el-button
            >

            <el-dialog v-model="centerDialogVisible" title="Warning" width="30%" style="margin: 50%">
              <span
                >It should be noted that the content will not be aligned in center by
                default</span
              >
              <template #footer>
                <span class="dialog-footer">
                  <el-button type="primary" @click="centerDialogVisible = false"
                    >Confirm</el-button
                  >
                </span>
              </template>
            </el-dialog>
          </div>
            <div class="page-wrapper">
              <div class="loader">
                <div class="jelly">
                  <div class="body"></div>
                  <div class="eye"></div>
                  <div class="eye"></div>
                  <div class="mouth"></div>
                </div>
                <div class="shadow"></div>
              </div>
            </div>
          <div>
            <el-button type="text" @click="centerDialogVisible = true" style="margin-right:100px; font-size: 40px;"
              >다음 약속</el-button
            >

            <el-dialog v-model="centerDialogVisible" title="Warning" width="30%" style="margin: 50%">
              <span
                >It should be noted that the content will not be aligned in center by
                default</span
              >
              <template #footer>
                <span class="dialog-footer">
                  <el-button type="primary" @click="centerDialogVisible = false"
                    >Confirm</el-button
                  >
                </span>
              </template>
            </el-dialog>
          </div>
        </div>

      </div>
    </div>







    <div style="margin-bottom: 100px; dispaly: flex; justify-content: center; align-items:center;" class="my-2">
      <el-carousel :interval="4000" type="card" height="200px" style=" z-index: -1" >
        <el-carousel-item v-for="item in 10" :key="item">
          <h3>{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'


export default {
  name: 'schedule',
  setup() {

  const centerDialogVisible = ref(false)


    return { centerDialogVisible }
  }
}


</script>

<style>
  /* test */
  .el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
  z-index: -3;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
  }

  #scape {
    max-width: 100%;
    max-height: 80%;
    overflow-x: hidden;
    margin: 0px;
  }

  .page-wrapper {
  width: 100%;
  height: 3%;
  display: flex;
  align-items: center;
  justify-content: center;
  }

  .loader {
    position: relative;
    width: 100px;
    margin: 50px;
  }

  .loader .jelly {
    animation: jumping 1s infinite;
  }

  .loader .body {
    width: 100px;
    height: 200px;
    border-radius: 50px;
    background: linear-gradient( to bottom,#32BEBE 60%, yellow);
    animation: body 1s infinite;
  }

  .loader .mouth {
    position: absolute;
    top: 85px;
    left: 27px;
    width: 40px;
    height: 20px;
    border: 3px solid #2a333b;
    border-radius: 0 0 40px 40px;
    background-color: #363b2a;
    overflow: hidden;
    z-index: 1;
  }

  .loader .mouth:before {
    content: '';
    display: block;
    position: absolute;
    top: 10px;
    left: 0;
    width: 40px;
    height: 20px;
    border-radius: 50%;
    background-color: #ff4081;
  }

  .loader .eye {
    position: absolute;
    top: 45px;
    width: 10px;
    height: 20px;
    border-radius: 50%;
    background-color: #2a333b;
    animation: eye 6s infinite;
  }

  .loader .eye:nth-child(even) {
    left: 30px;
  }

  .loader .eye:nth-child(odd) {
    right: 30px;
  }

  .loader .shadow {
    position: absolute;
    bottom: -10px;
    z-index: -1;
    width: 100px;
    height: 20px;
    border-radius: 50%;
    background-color: rgba(42,51,59,0.2);
    animation: shadow 1s infinite;
  }

  @-moz-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-webkit-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-o-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-moz-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-webkit-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-o-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-moz-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-webkit-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-o-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-moz-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @-webkit-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @-o-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }


  .landing {
  position: relative;
  margin: auto;
  display: block;
  width: 100%;
  height: 60vh;
  background: rgb(190, 231, 232);
  background: linear-gradient(
    158deg,
    rgba(190, 231, 232, 1) 0%,
    rgba(255, 212, 201, 1) 55%
  );
}
.mountain-scene {
  position: relative;
  height: 75%;
  /* border-style: solid; */
  /* overflow:hidden; */
  /* margin: 0; */
}

/* Background Mountains

Triangles made using transform rotate
width = 1.41 x height(padding-bottom) */

.tri {
  width: 30%;
  padding-bottom: 21%;
  position: relative;
  overflow: hidden;
}
.tri:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  background: rgb(254, 194, 178);
  background: linear-gradient(
    180deg,
    rgba(254, 194, 178, 1) 0%,
    rgba(235, 148, 126, 1) 55%
  );
  border-radius: 20px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.small-mountains .tri:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: -7%;
}

.small-mountains .tri:nth-child(2) {
  bottom: 0;
  position: absolute;
  left: 40%;
}

.small-mountains .tri:nth-child(3) {
  bottom: 0;
  position: absolute;
  right: -10%;
}

.tri2 {
  width: 48%;
  padding-bottom: 33%;
  position: relative;
  overflow: hidden;
}
.tri2:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  /* background: #726DA8; */
  background: rgb(254, 194, 178);
  background: linear-gradient(
    180deg,
    rgba(254, 194, 178, 1) 0%,
    rgba(235, 148, 126, 1) 55%
  );
  border-radius: 20px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.tall-mountains .tri2:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: 0%;
}

.tall-mountains .tri2:nth-child(2) {
  bottom: 0;
  position: absolute;
  left: 14%;
}

.tall-mountains .tri2:nth-child(3) {
  bottom: 0;
  position: absolute;
  left: 50%;
}

/* Foreground mountains */

.tri3 {
  width: 19%;
  padding-bottom: 13%;
  position: relative;
  overflow: hidden;
}

.tri3:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  background: rgb(233, 243, 244);
  background: linear-gradient(
    180deg,
    rgba(233, 243, 244, 1) 0%,
    rgba(190, 231, 232, 1) 72%
  );
  border-radius: 18px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.tiny-mountains .tri3:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: 9%;
}
.tiny-mountains .tri3:nth-child(2) {
  bottom: -3%;
  position: absolute;
  left: 31%;
}

.tiny-mountains .tri3:nth-child(3) {
  bottom: -7%;
  position: absolute;
  left: 42%;
}

.tiny-mountains .tri3:nth-child(4) {
  bottom: 0;
  position: absolute;
  left: 72%;
}

.tiny-mountains .tri3:nth-child(5) {
  bottom: -5%;
  position: absolute;
  left: 62%;
}
.ground {
  position: relative;
  height: 25%;
  background: rgb(255, 246, 202);
  background: linear-gradient(
    180deg,
    rgba(255, 246, 202, 1) 0%,
    rgba(255, 229, 164, 1) 72%
  );
}

#clouds {
  padding: 4vw 0;
}

/* base of cloud */
.cloud {
  height: 4vw;
  width: 13vw;
  background: white;
  border-radius: 40vw;
  position: relative;
}

/* smaller circle within cloud */
.cloud:before {
  content: "";
  position: absolute;
  top: -2vw;
  height: 6vw;
  width: 6vw;
  left: 2vw;
  border-radius: 50%;
  background: white;
}

/* larger circle within cloud */
.cloud:after {
  content: "";
  position: absolute;
  height: 6vw;
  width: 6vw;
  top: -3vw;
  left: 5vw;
  border-radius: 50%;
  background: white;
}

#clouds .cloud:nth-child(1) {
  opacity: 0.7;
  animation: moveclouds 17s linear infinite;
  -webkit-animation: moveclouds 17s linear infinite;
  -moz-animation: moveclouds 17s linear infinite;
  -o-animation: moveclouds 17s linear infinite;
}
#clouds .cloud:nth-child(2) {
  left: 75%;
  opacity: 0.5;
  animation: moveclouds 22s linear infinite;
  -webkit-animation: moveclouds 22s linear infinite;
  -moz-animation: moveclouds 22s linear infinite;
  -o-animation: moveclouds 22s linear infinite;
}
#clouds .cloud:nth-child(3) {
  left: 50%;
  z-index: 1;
  animation: moveclouds 15s linear infinite;
  -webkit-animation: moveclouds 15s linear infinite;
  -moz-animation: moveclouds 15s linear infinite;
  -o-animation: moveclouds 15s linear infinite;
}
#clouds .cloud:nth-child(4) {
  left: 90%;
  opacity: 0.7;
  transform: scale(0.6);
  -webkit-transform: scale(0.6);
  -moz-transform: scale(0.6);
  -o-transform: scale(0.6);
  animation: moveclouds 16s linear infinite;
  -webkit-animation: moveclouds 16s linear infinite;
  -moz-animation: moveclouds 16s linear infinite;
  -o-animation: moveclouds 16s linear infinite;
}
#clouds .cloud:nth-child(5) {
  left: 100%;
  opacity: 0.4;
  z-index: 1;

  transform: scale(0.8);
  -webkit-transform: scale(0.8);
  -moz-transform: scale(0.8);
  -o-transform: scale(0.8);

  animation: moveclouds 22s linear infinite;
  -webkit-animation: moveclouds 22s linear infinite;
  -moz-animation: moveclouds 22s linear infinite;
  -o-animation: moveclouds 22s linear infinite;
}
@keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}

@-webkit-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}
@-moz-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}
@-o-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}




</style>
