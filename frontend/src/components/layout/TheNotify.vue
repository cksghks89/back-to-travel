<template>
  <div class="the-notify">
    <div class="notificationIcon" @click="toggleEvent">
      <img class="icon-image" src="@/assets/bell-icon2.png" alt="알림 아이콘" />
      <span class="notificationCount">{{ getNoReadCount }}</span>
    </div>

    <div class="notify-modal" :class="{ isNone: toggle }">
      <template v-if="notifyList.length != 0">
        <div v-for="(item, index) in notifyList" :key="index">
          <hr v-if="index != 0" />
          <img
            src="@/assets/mail.png"
            style="width: 20px; height: 20px; margin-right: 10px"
          />
          <span style="font-weight: bold">{{ item.senderNickName }}</span
          >님이 나의
          <router-link :to="generateLink(item.planLink)"
            ><span>{{ item.planId }}번</span></router-link
          >
          여행계획을 퍼갔습니다!
        </div>
      </template>
      <template v-else> 알람이 존재하지 않습니다. </template>
    </div>
    <audio v-show="false" controls ref="audioEl">
      <source src="@/assets/beep.mp3" type="audio/ogg" />
    </audio>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "TheNotify",
  data() {
    return {
      toggle: true,
      baseURL: process.env.VUE_APP_AWS_API_URL,
      // baseURL: provess.env.VUE_APP_LOCAL_API_URL,
      message: null,
      autoplayVideoInterval: null,
    };
  },
  methods: {
    ...mapMutations("socketStore", ["SET_STOMP_CLIENT", "ADD_NOTIFY_LIST"]),
    ...mapActions("userStore", ["fetchUserInfo"]),
    ...mapActions("socketStore", ["getNotification", "readNotification"]),
    async toggleEvent() {
      if (this.toggle) {
        console.log("a");
        await this.readNotification();
        await this.getNotification();
      }
      this.toggle = !this.toggle;
    },
    playBeep() {
      this.autoplayVideoInterval = setInterval(this.autoplayVideo(), 200);
    },
    autoplayVideo() {
      var promise = this.$refs.audioEl.play();
      if (promise !== undefined) {
        promise
          .then(function (_) {
            // Autoplay started!
            clearInterval(this.autoplayVideoInterval);
          })
          .catch(function (error) {});
      }
    },
  },
  computed: {
    ...mapState("userStore", ["userId", "userInfo"]),
    ...mapState("socketStore", ["stompClient", "notifyList", "audio"]),
    ...mapGetters("userStore", ["getUserInfo"]),
    getNoReadCount() {
      let cnt = 0;
      for (let item of this.notifyList) {
        if (!item.view) {
          cnt++;
        }
      }
      return cnt;
    },
    generateLink() {
      return function (link) {
        return `${link}`;
      };
    },
  },
  async created() {
    await this.getNotification();
    await this.fetchUserInfo(this.userId);
    console.log(this.notifyList);

    // socket 연결
    let socket = new SockJS(`${this.baseURL}/ws`);
    let stompClient = Stomp.over(socket);

    stompClient.connect(
      {},
      (frame) => {
        stompClient.subscribe(`/sub/`, (res) => {
          let json = JSON.parse(res.body);
          if (json.receiverNickName != this.userInfo.userId) return;
          this.ADD_NOTIFY_LIST(JSON.parse(res.body));
          this.playBeep();
        });
      },
      (err) => {
        console.log("fail", err);
      }
    );
  },
};
</script>

<style scoped>
.the-notify {
  position: relative;
  margin: auto auto;
  margin-right: 10px;
}

.isNone {
  display: none;
}

.notify-modal {
  position: absolute;
  top: 40px;
  right: 0;
  width: 500px;
  border: 1px solid rgb(206, 203, 203);
  background-color: white;
  z-index: 9999;
  padding: 10px; /* 패딩 추가 */
}

.notificationIcon {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

.notificationCount {
  position: absolute;
  top: -1px;
  right: -1px;
  background-color: red;
  color: white;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  font-size: 12px;
  text-align: center;
  line-height: 20px;
  border: 1px solid #ffffff; /* 테두리 색 추가 */
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
}

.icon-image {
  width: 35px;
  height: 35px;
}
</style>
