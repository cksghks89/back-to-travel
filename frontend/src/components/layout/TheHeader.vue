<template>
  <div class="header-container">
    <div class="header">
      <div class="logo-container">
        <router-link to="/">
          <img class="logo" src="@/assets/chatting_logo.png" alt="logo" />
        </router-link>
      </div>
      <nav>
        <ul class="nav">
          <li>
            <router-link to="/">홈</router-link>
          </li>
          <li>
            <router-link to="/attraction">인기여행지</router-link>
          </li>
          <li>
            <router-link to="/plan">여행계획</router-link>
          </li>
          <li>
            <router-link to="/noticeboard">게시판</router-link>
          </li>
        </ul>
      </nav>
      <div class="sign-container">
        <div v-if="!isLogin">
          <span><router-link to="/user/login">로그인</router-link></span>
          <span>&#183;</span>
          <span><router-link to="/user/signup">회원가입</router-link></span>
        </div>
        <div v-else class="d-flex">
          <the-notify-vue></the-notify-vue>
          <b-dropdown
            variant="link"
            toggle-class="text-decoration-none"
            no-caret
            class="my-profile"
          >
            <template #button-content>
              <img class="profile-img" :src="getProfile" alt="" />
            </template>

            <router-link to="/profile" tag="b-dropdown-item"
              >마이페이지</router-link
            >
            <b-dropdown-item href="#" @click="logout">로그아웃</b-dropdown-item>
            <router-link
              v-if="userId == 'admin'"
              to="/admin"
              tag="b-dropdown-item"
              style="margin-top: 30px"
              class="alert-info"
              >관리자</router-link
            >
          </b-dropdown>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import TheNotifyVue from "./TheNotify.vue";

export default {
  name: "TheHeader",
  components: { TheNotifyVue },
  data() {
    return {
      noProfile: require("@/assets/no-profile.png"),
    };
  },

  computed: {
    ...mapState("userStore", ["isLogin", "userId", "userProfile"]),
    getProfile() {
      if (this.userProfile) {
        return this.userProfile;
      } else {
        return require("@/assets/no-profile.png");
      }
    },
  },
  methods: {
    ...mapMutations("userStore", ["LOGOUT"]),

    loginForm() {
      this.$router.push("/user/login");
    },

    logout() {
      this.LOGOUT();
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  list-style: none;
  text-decoration: none;
  box-sizing: border-box;
}

.header-container {
  padding: 10px 0;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1200px;
  margin: 0 auto;
}

.logo {
  width: 60px;
}

.logo-container {
  display: flex;
  align-items: center;
  width: 150px;
}

.nav {
  display: flex;
  align-items: center;
}

.nav li {
  margin: 15px;
  font-size: 16px;
  cursor: pointer;
  font-family: "Noto Sans KR", sans-serif;
}

.sign-container {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  width: 150px;
}

.sign-container span {
  margin: 5px;
  font-family: "Noto Sans KR", sans-serif;
}

.my-profile {
  width: 60px;
}

.profile-img {
  width: 45px;
  height: 45px;
  border-radius: 10px;
}

/* Fonts */
@import url("https://fonts.googleapis.com/css2?family=Gmarket+Sans:wght@500&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Cafe24+Ssurround:wght@400;700&display=swap");

/* Custom Styles */
body {
  font-family: "Gmarket Sans", sans-serif;
}

.nav li:hover {
  color: #007bff;
}

.profile {
  width: 43px;
  height: 43px;
  border-radius: 8px;
  cursor: pointer;
}
</style>
