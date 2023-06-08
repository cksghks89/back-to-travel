<template>
  <div class="container">
    <div class="personal__title">
      <h2>사용자 로그 통계</h2>
    </div>
    <div class="personal__body">
      <div>
        <b-table striped hover :items="logList"></b-table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "TheStats",

  data() {
    return {};
  },
  computed: {
    ...mapState("adminStore", ["userLog"]),
    logList() {
      let result = [];
      for (let i = 0; i < this.userLog.length; i++) {
        let temp = {
          id: this.userLog[i].id,
          사용자아이디: this.userLog[i].userId,
          시간: this.userLog[i].loginTime,
          결과: `${this.userLog[i].result == true ? "성공" : "실패"}`,
        };
        result.push(temp);
      }
      result.reverse();
      return result;
    },
  },
  methods: {
    ...mapActions("adminStore", ["getUserLog"]),
  },
  created() {
    this.getUserLog();
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
h2 {
  font-weight: bold;
  margin-bottom: 30px;
}
.personal__body__img {
  margin-top: 30px;
  margin-bottom: 30px;
}
label {
  color: #85878a;
}
</style>
