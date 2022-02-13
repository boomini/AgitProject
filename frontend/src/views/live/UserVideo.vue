<template>
<div v-if="streamManager">
  <div class="d-flex flex-column align-items-center mx-2 my-2" id="user-video" :style="{ borderColor: this.borderColor}">
    <div class="video-mask">
      <ov-video :stream-manager="streamManager"/>
    </div>
    <div class="d-flex justify-content-center" id="user-bar">
      <div>
       <p>이름: {{ clientData }}</p>
      </div>
    </div>
  </div>
</div>
</template>

<style scoped>
  #user-video{
    width: 45vh;
    padding: 0.5vh;
    border-radius: 2%;
    border-width: 2.5px;
    border-style: solid;
    background-color:   rgb(88, 85, 85);
    box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
  }

  #user-video p{
    display: inline-block;
    color: #131313;
    font-weight: bold;
  }

  #user-bar{
    margin-top: 1vh;
    width: 40vh;
    height: 5vh;
  }
  .video-mask{
    border-radius: 2%;
    height: 32vh;
    overflow: hidden;
  }
</style>

<script>
import OvVideo from '@/views/live/OvVideo.vue';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
    borderColor: String,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
