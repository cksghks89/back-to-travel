<template>
  <div class="container">
    <div class="personal__title">
      <h2>Delete Account</h2>
    </div>
    <div class="personal__body">
      <div class="warning">
        <h6>
          경고: 이 작업은 되돌릴 수 없습니다. 계정을 영구히 삭제하려면 '계정
          삭제' 버튼을 누르세요.
        </h6>
        <b-button @click="deleteWarning" variant="danger">계정 삭제</b-button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "DeleteProfile",

  data() {
    return {};
  },

  mounted() {},
  methods: {
    ...mapActions("userStore", ["fetchUserInfo", "deleteAccount"]),
    deleteWarning() {
      if (confirm("정말로 계정을 삭제하시겠습니까?") == true) {
        this.deleteAccount();
        alert("계정이 삭제되었습니다.");
        this.$router.push("/");
      } else {
        alert("취소되었습니다.");
      }
    },
  },
};
</script>

<style scoped>
.container {
  padding-top: 30px;
  padding-left: 30px;
  padding-right: 30px;
  margin-left: 30px;
  border: 1px solid #e5e5e5;
  border-radius: 10px;
}
.btn-change {
  margin-top: 10px;
  margin-left: 30px;
}
.btn-change:hover {
  background-color: blue;
  color: black;
}
.warning h6 {
  color: red;
  font-weight: bold;
  margin-bottom: 30px;
}
.warning {
  margin-top: 30px;
}
.personal__body {
  margin-top: 50px;
  margin-bottom: 30px;
}
label {
  color: #85878a;
}
.gender-label {
  color: #85878a;
  margin-bottom: 8px;
}
.row {
  margin-bottom: 20px;
}
.submit-button {
  margin-top: 50px;
}
.personal__title h2 {
  font-weight: bold;
  margin-bottom: 30px;
}
</style>
