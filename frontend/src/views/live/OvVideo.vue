<template>
	<video autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style scoped>
video {
  width: 42vh;
  height: 32vh;
}
</style>
