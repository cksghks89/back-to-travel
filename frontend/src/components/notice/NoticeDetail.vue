<template>
  <div class="notice-detail-container">
    <div class="notice-info">
      <h1>{{ getNoticeDetail.title }}</h1>
      <p>글쓴이 {{ getNoticeDetail.userId }}</p>
      <p>등록일 {{ getNoticeDetail.createTime }}</p>
      <p>조회수 {{ getNoticeDetail.hit }}</p>
    </div>
    <div class="notice-content" v-html="getNoticeDetail.content"></div>
    <div class="button-container">
      <router-link to="/noticeboard/notice">
        <button>목록으로</button>
      </router-link>
      <div class="admin-button" v-if="getUserId == getNoticeDetail.userId">
        <button class="delete-btn" @click="remove">삭제</button>

        <router-link
          :to="{
            path: `/notice/modify/${noticeId}`,
          }"
        >
          <button>수정</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "NoticeDetail",
  data() {
    return {
      noticeId: this.$route.params.id,
    };
  },
  watch: {
    getNoticeDetail() {
      this.fetchNoticeDetail(this.noticeId);
    },
  },
  methods: {
    ...mapActions("noticeStore", ["fetchNoticeDetail", "deleteNotice"]),
    remove() {
      if (confirm("삭제하시겠습니까?")) {
        this.deleteNotice(this.noticeId)
          .then(() => {
            this.$router.push("/notice/list");
          })
          .catch(() => {
            alert("삭제 실패");
          });
      }
    },
  },
  computed: {
    ...mapGetters("noticeStore", ["getNoticeDetail"]),
    ...mapGetters("userStore", ["getUserId"]),
  },
  created() {
    this.fetchNoticeDetail(this.noticeId);
  },
  mounted() {
    this.fetchNoticeDetail(this.noticeId);
  },
};
</script>

<style scoped>
.notice-detail-container {
  margin: 0 auto;
  margin-left: 0px;
  margin-right: 0px;
  margin-top: 100px;
}
.notice-detail-title {
  margin-bottom: 20px;
  font-size: 35px;
  font-weight: bold;
}
.notice-info {
  margin-bottom: 20px;
}
.notice-content {
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
.admin-button {
  display: flex;
  justify-content: space-between;
  width: 150px;
}
button.delete-btn:hover {
  background-color: #e53935;
}
</style>
