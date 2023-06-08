<template>
  <div class="room">
    <h1>{{ title }}</h1>
    <hr />
    <div v-for="(m, idx) in msg" :key="idx">
      <div v-bind:class="m.style">
        <h5 style="margin: 3px">
          {{ m.senderNickname }}
        </h5>
        {{ m.content }}
      </div>
    </div>
    <hr />
    <input
      type="text"
      v-model="content"
      placeholder="보낼 메세지"
      size="100"
      @keyup.enter="sendMessage()"
    />
    <button @click="sendMessage()">SEND</button>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "ChatView",
  data: () => {
    return {
      id: -1, 
      nickname: "",
      title: "",
      roomid: -1,
      idx: 0,
      msg: [],
      content: "",
      // baseURL: "http://localhost",
      baseURL: "http://3.34.77.175",
    };
  },
  created() {
    this.id = this.$route.params.id;
    this.roomid = this.$route.params.roomid;
    this.nickname = this.$route.params.nickname;

    // socket 연결
    let socket = new SockJS(`${this.baseURL}/ws`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect(
      {},
      (frame) => {
        this.stompClient.subscribe("/sub/chat", (res) => {
          let jsonBody = JSON.parse(res.body);
          let m = {
            senderNickname: jsonBody.senderNickname,
            content: jsonBody.content,
            style: jsonBody.senderId == this.id ? "myMsg" : "otherMsg",
          };
          this.msg.push(m);
        });
      },
      (err) => {
        console.log("fail", err);
      }
    );
  },
  methods: {
    sendMessage() {
      if (this.content.trim() != "" && this.stompClient != null) {
        let chatMessage = {

        };
        this.stompClient.send("/pub/chat", JSON.stringify(chatMessage), {});

        this.content = "";
      }
    },
  },
};
</script>
<style scoped>
.myMsg {
  text-align: right;
  color: gray;
}
.otherMsg {
  text-align: left;
}
</style>
