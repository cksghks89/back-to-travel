<template>
  <div class="board-detail-container">
    <div class="board-info">
      <h1>{{ getBoardDetail.title }}</h1>
      <p>글쓴이 {{ getBoardDetail.userId }}</p>
      <p>등록일 {{ getBoardDetail.createTime }}</p>
      <p>조회수 {{ getBoardDetail.hit }}</p>
    </div>
    <div class="board-content" v-html="getBoardDetail.content"></div>
    <div class="button-container">
      <router-link to="/noticeboard/board">
        <button>목록으로</button>
      </router-link>
      <div class="user-button" v-if="getUserId == getBoardDetail.userId">
        <button class="delete-btn" @click="remove">삭제</button>

        <router-link :to="`/board/modify/${boardId}`">
          <button>수정</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "BoardDetail",
  data() {
    return {
      boardId: this.$route.params.id,
    };
  },
  methods: {
    ...mapActions("boardStore", ["fetchBoardDetail", "deleteBoard"]),
    remove() {
      if (confirm("삭제하시겠습니까?")) {
        this.deleteBoard(this.boardId)
          .then(() => {
            this.$router.push("/noticeboard/board");
          })
          .catch(() => {
            alert("삭제 실패");
          });
      }
    },
  },
  computed: {
    ...mapGetters("boardStore", ["getBoardDetail"]),
    ...mapGetters("userStore", ["getUserId"]),
  },
  created() {
    this.fetchBoardDetail(this.boardId);
  },
  mounted() {
    this.fetchBoardDetail(this.boardId);
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.fetchBoardDetail(vm.boardId);
    });
  },
};
</script>

<style scoped>
.board-detail-container {
  margin: 0 auto;
  margin-left: 0px;
  margin-right: 0px;
  margin-top: 100px;
}
.board-detail-title {
  margin-bottom: 20px;
  font-size: 35px;
  font-weight: bold;
}
.board-info {
  margin-bottom: 20px;
}
.board-content {
  padding: 20px;
  border: 1px solid #ddd;
}
.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
button {
  background-color: #caa8fe;
  color: white;
  border: none;
  padding: 6px 12px;
  cursor: pointer;
  border-radius: 4px;
}
button:not(.delete-btn) {
  background-color: #caa8fe;
}
button.delete-btn {
  background-color: #f34e42;
}
button:hover {
  background-color: #af5cd4;
}
.user-button {
  display: flex;
  justify-content: space-between;
  width: 150px;
}
button.delete-btn:hover {
  background-color: #e53935;
}
</style>
