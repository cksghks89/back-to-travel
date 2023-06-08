<template>
  <div class="container">
    <div class="btn-group">
      <router-link class="link-btn" to="notice" tag="button"
        >공지사항</router-link
      >
      <router-link class="link-btn" to="board" tag="button"
        >공유게시판</router-link
      >
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "NoticeBoard",
  components: {},
  data() {
    return {
      notice: "공지사항",
      board: "공유게시판",
      text: "공지사항",
    };
  },

  mounted() {},

  methods: {
    ftoggle(text) {
      if (text === this.notice) {
        this.text = this.notice;
      } else if (text === this.board) {
        this.text = this.board;
      }
    },
  },
};
</script>

<style scoped>
.btn-group {
  position: absolute;
  top: 0;
  margin-top: 140px;
  margin-left: 10px;
  background-color: none;
  color: black;
}
.link-btn {
  background-color: none;
  border: none;
  border-radius: 10px;
  color: black;
  font-size: 12px;
  margin-right: 10px;
}
.link-btn:hover {
  background-color: #caa7fe;
  color: white;
}
</style>
