<template>
<div v-if="streamManager">
  <div class="d-flex flex-column align-items-center mx-2" id="user-video">
    <ov-video :stream-manager="streamManager"/>
    <div><p>{{ clientData }}</p></div>
  </div>
</div>
</template>

<style scoped>
  #user-video{
    width: 50vh;
    padding: 0.3vh;
    border-radius: 2%;
    border-color: black;
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
