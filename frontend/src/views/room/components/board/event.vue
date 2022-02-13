<template>
  <div>
    <p v-for="(item, index) in eventList" :key="item" :class="[uploadDate === item.endDate ? 'event-item deadline-item' : 'event-item normal-item']" style="text-align: center;" @click="onCellClick(item)">
    <!-- <p v-for="(item, index) in eventList" :key="item" :class="{'event-item end-item': getToday() > item.endDate, 'event-item deadline-item': getToday() === item.endDate, 'event-item normal-item': getToday() < item.endDate}" style="text-align: center;" @click="onCellClick(item)"> -->
      <p>
        {{ item.eventTitle }}
      </p>
      <p v-if="uploadDate === item.endDate" class="ms-2">
        <img :src="require(`@/assets/images/deadline.png`)" alt="" height="30">
      </p>
    </p>
  </div>
  <event-view-dialog
    :open="state.eventViewOpen"
    :info="state.event"
    @closeEventView="onCloseEventView"/>

</template>

<script>
import { reactive } from 'vue'
import EventViewDialog from './dialog/event-view-dialog.vue'

export default {
  name: 'event',
  components: {
    EventViewDialog,
  },
  props: {
    eventList: {
      type: Array
    },
    uploadDate: {
      type: String
    }
  },
  setup(props){
    const state = reactive({
      eventViewOpen: false,
      event: {
        'dday': '',
        'endDate': '1970-01-01',
        'eventContent': '',
        'startDate': '1970-01-01',
        'teamId': '',
        'teamName': ''
      }
    })

    const onCellClick = function (val) {
      state.event = val
      state.eventViewOpen = true
    }

    const onCloseEventView = function () {
      state.eventViewOpen = false
    }

    const getToday = function () {
      const today = new Date()
      const year = today.getFullYear()
      const month = ("0" + (1 + today.getMonth())).slice(-2);
      const day = ("0" + today.getDate()).slice(-2);

      return `${year}-${month}-${day}`
    }

    return { state, onCellClick, getToday }
  }
}
</script>

<style>
.event-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: calc(100% - 20px);
  height: 50px;
  margin: 10px;
  text-align: center;
  border: 1px solid #d1d1d1;
  border-radius: 4px;
  /* background-color: #fff9e4; */
  color: var(--el-text-color-regular);
}

.event-item:active {
  opacity: 0.5;
}

.normal-item {
  background-color: #fff9e4;
}

.normal-item:hover {
  background-color: #e3ddca;
  transition: 0.1s;
}

.deadline-item {
  background-color: #ff8181;
  color: white;
  border: 1px solid #ff7482;
}

.deadline-item:hover {
  background-color: #cb6666;
  transition: 0.1s;
}

.end-item {
  background-color: green;
}

.end-item:hover {
  background-color: black;
  transition: 0.1s;
}

.deadline-item:
</style>
