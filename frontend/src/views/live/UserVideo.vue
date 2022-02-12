<template>
<div v-if="streamManager">
  <div class="d-flex flex-column align-items-center mx-2 my-2" id="user-video" :style="{ borderColor: this.borderColor}">
    <ov-video :stream-manager="streamManager"/>
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
    padding: 0.3vh;
    border-radius: 2%;
    border-width: 2px;
    border-style: solid;
  }

  #user-video p{
    display: inline-block;
    background: #f8f8f8;
    color: #3e3b3b;
    font-weight: bold;
    border-bottom-right-radius: 4px;
  }

  #user-bar{
    margin-top: 1vh;
    width: 40vh;
    height: 5vh;
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
