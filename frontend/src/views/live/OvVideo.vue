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
    console.log(this.streamManager)
	},
};
</script>

<style scoped>
video {
  width: 100%;
}
</style>
