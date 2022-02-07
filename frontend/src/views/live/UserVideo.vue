<template>
<div v-if="streamManager">
  <div class="d-flex flex-column align-items-center">
    <ov-video :stream-manager="streamManager"/>
    <div><p>{{ clientData }}</p></div>
  </div>
</div>
</template>

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
