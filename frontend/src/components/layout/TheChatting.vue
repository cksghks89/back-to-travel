<template>
  <div class="chatting-container">
    <div class="the-chatting" v-show="modalClose">
      <div>
        <h2>Chatting</h2>
        <img
          @click="closeChatting"
          class="close-icon"
          src="@/assets/icon_x.png"
        />
      </div>
      <hr />
      <div class="chat-log" ref="chatContainer">
        <div v-for="(m, idx) in chattingList" :key="idx">
          <div>[{{ m.nickName }}] {{ m.message }}</div>
          <div v-if="isPattern(m.message)">
            <plan-window :message="m.message"></plan-window>
          </div>
        </div>
      </div>
      <div class="input-box">
        <input
          v-if="userInfo"
          type="text"
          v-model="message"
          placeholder="typing message"
          @keyup.enter="sendMessage()"
        />
        <input v-else type="text" placeholder="로그인 후 이용가능" disabled />
        <img
          style="width: 40px; height: 40px; cursor: pointer"
          @click="sendMessage()"
          src="@/assets/send.png"
        />
      </div>
    </div>
    <img
      @click="openChatting"
      class="chatting-btn"
      src="@/assets/chatting_logo.png"
    />
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import http from "@/api/http.js";
import PlanWindow from "./PlanWindow.vue";

export default {
  name: "TheChatting",
  components: { PlanWindow },

  data() {
    return {
      title: "chatting",
      message: "",
      stompClient: null,
      chattingLog: [],
      baseURL: process.env.VUE_APP_AWS_API_URL,
      // baseURL: provess.env.VUE_APP_LOCAL_API_URL,
      modalClose: false,
    };
  },
  computed: {
    ...mapState("userStore", ["userInfo"]),
    chattingList() {
      return this.chattingLog;
    },
  },
  methods: {
    ...mapActions("socketStore", [""]),

    sendMessage() {
      if (this.userInfo == null) {
        alert("로그인 후 이용할 수 있습니다.");
      }
      if (this.message != "" && this.stompClient != null) {
        let chatMessage = {
          nickName: this.userInfo.nickName,
          message: this.message,
          time: new Date(),
        };
        this.stompClient.send("/pub/chat", JSON.stringify(chatMessage), {});
        this.message = "";
      }
    },
    closeChatting() {
      this.modalClose = false;
    },
    openChatting() {
      this.modalClose = !this.modalClose;
      setTimeout(() => {
        this.scrollToBottom();
      }, 50);
    },
    scrollToBottom() {
      this.$refs.chatContainer.scrollTop =
        this.$refs.chatContainer.scrollHeight;
    },
    isPattern(message) {
      const regex = /#(\d+)/; // 정규표현식 패턴

      const match = regex.exec(message); // 정규표현식 매칭
      if (match) {
        console.log("isPattern true");
        return true;
      } else {
        console.log("isPattern false");
        return false;
      }
    },
  },
  async created() {
    await http.get("/notify/chat").then((response) => {
      this.chattingLog = response.data;
    });

    // socket 연결
    let socket = new SockJS(`${this.baseURL}/ws`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect(
      {},
      (frame) => {
        this.stompClient.subscribe("/sub/chat/", (res) => {
          let jsonBody = JSON.parse(res.body);
          let m = {
            nickName: jsonBody.nickName,
            message: jsonBody.message,
            time: jsonBody.time,
          };
          this.chattingLog.push(m);

          setTimeout(() => {
            this.scrollToBottom();
          }, 150);
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
.chatting-container {
  z-index: 555;
}
.the-chatting {
  width: 500px;
  height: 500px;
  border: 2px solid #c7c6c6;
  background-color: rgb(255, 252, 252);
  padding: 10px;
  border-radius: 15px;
}

.input-box {
  position: absolute;
  bottom: 20px;
  left: 15px;
}

.input-box input {
  margin-right: 10px;
  width: 400px;
}

.close-icon {
  width: 20px;
  height: 20px;
  position: absolute;
  right: 10px;
  top: 10px;
  cursor: pointer;
}

.chatting-btn {
  position: fixed;
  bottom: 40px;
  right: 30px;
  width: 60px;
  height: 60px;
  cursor: pointer;
}
.chat-log {
  overflow-y: scroll;
  height: 350px;
}

.chat-log::-webkit-scrollbar {
  width: 10px;
}
.chat-log::-webkit-scrollbar-thumb {
  background-color: #c7c6c6;
  border-radius: 10px;
}
</style>
