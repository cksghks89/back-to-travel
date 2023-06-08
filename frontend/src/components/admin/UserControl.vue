<template>
  <div class="container">
    <div class="personal__title">
      <h2>사용자 관리</h2>
    </div>
    <div class="personal__body">
      <div>
        <b-table-simple hover small class="list-table">
          <b-thead head-variant="dark">
            <b-tr>
              <b-th class="first">아이디</b-th>
              <b-th>이메일</b-th>
              <b-th>닉네임</b-th>
              <b-th>성별</b-th>
              <b-th>emailAuth</b-th>
              <b-th>관리</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <b-tr v-for="(item, index) in userList" :key="index">
              <b-td>{{
                item.userId.length > 10 ? item.userId.slice(0, 10) : item.userId
              }}</b-td>
              <b-td>{{ item.email }}</b-td>
              <b-td>{{ item.nickName }}</b-td>
              <b-td>{{ item.gender }}</b-td>
              <b-td>{{ item.emailAuth }}</b-td>
              <b-td><button @click="removeUserEvent(item)">제거</button></b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "UserControl",

  data() {
    return {};
  },

  computed: {
    ...mapState("adminStore", ["userList"]),
  },

  methods: {
    ...mapActions("adminStore", ["getUserList", "removeUser"]),
    async removeUserEvent(user) {
      if (confirm("정말 제거하시겠습니까?")) {
        await this.removeUser(user);
        this.getUserList();
      }
    },
  },

  async created() {
    await this.getUserList();
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

.first {
  width: 150px;
}
</style>
