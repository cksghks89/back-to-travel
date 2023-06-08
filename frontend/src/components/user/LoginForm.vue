<template>
  <div class="login-out-container">
    <div class="img-container">
      <img src="@/assets/login_logo.png" />
    </div>
    <div class="login-container">
      <div class="login-form">
        <h2 class="title">LOGIN</h2>
        <input
          class="login-input"
          v-model="id"
          type="text"
          @keyup.enter="loginEvent"
          placeholder="아이디"
        />
        <input
          class="login-input"
          v-model="password"
          @keyup.enter="loginEvent"
          type="password"
          placeholder="비밀번호"
        />
        <button class="login-button" @click="loginEvent">로그인</button>

        <div class="social-login">
          <hr />
          <span class="social-login-text">or</span>
          <hr />
        </div>

        <div class="social-login-form">
          <button type="button" class="image-button">
            <img src="@/assets/google.png" alt="구글 로그인" />
          </button>
          <button type="button" class="image-button">
            <img
              src="@/assets/naver.png"
              alt="네이버 로그인"
              @click="naverLogin"
            />
          </button>
          <button type="button" class="image-button">
            <img src="@/assets/kakao.png" alt="카카오 로그인" />
          </button>
        </div>
        <div class="find">
          비밀번호를 잊어버리셨습니까?
          <router-link to="/user/findpassword">
            <button class="find-button">비밀번호 찾기</button>
          </router-link>
        </div>
        <div class="regist">
          회원이 아니십니까?
          <router-link to="/user/signup">
            <button class="regist-button">가입하기</button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "LoginPage",
  data() {
    return {
      id: "",
      password: "",
    };
  },

  computed: {
    ...mapState("userStore", ["isLogin", "userId"]),
  },

  methods: {
    ...mapActions("userStore", [
      "login",
      "getRequiredNaverLoginInfo",
      "fetchUserInfo",
    ]),

    async loginEvent() {
      await this.login({ userId: this.id, password: this.password })
        .then(() => {
          this.$router.push("/");
        })
        .catch(() => {
          this.id = "";
          this.password = "";
          alert("아이디, 비밀번호를 다시 확인해 주세요");
        });
      await this.fetchUserInfo(this.userId);
    },
    naverLogin() {
      this.getRequiredNaverLoginInfo().then((response) => {
        console.log(response);
        var authUrl = "https://nid.naver.com/oauth2.0/authorize";
        authUrl += "?response_type=code";
        authUrl += "&client_id=" + encodeURIComponent(response.clientId);
        authUrl += "&redirect_uri=" + encodeURIComponent(response.callbackURL);
        // window.open(authUrl, "Popup", "width=500,height=400");
        window.location.href = authUrl;
      });
    },
  },
};
</script>

<style scoped>
.login-out-container {
  display: flex;
}
.img-container {
  /* display: flex; */
  margin: 250px 200px 0 60px;
}
.img-container img {
  width: 350px;
  align-items: center;
}
.title {
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 20px;
}
.login-container {
  width: 100%;
  height: 100%;
  display: flex;
  margin-top: 70px;
}

.login-form {
  width: 500px;
  height: 630px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-bottom: 0px;
  padding: 50px;
  /* 테두리 스타일링 */
  /* border: 2px solid #af5cd4; */
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
}

.login-input {
  border-radius: 4px;
  border: 1px solid #b2b2b2;
  width: 100%;
  height: 45px;
  margin-top: 25px;
  padding-left: 10px;
}
.social-login {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 100px;
}

.social-login hr {
  flex-grow: 1;
  height: 1px;
  width: 175px;
  margin: 0 10px;
  color: black;
}

.social-login .social-login-text {
  font-size: 14px;
  color: #757575;
  text-transform: uppercase;
  font-weight: 500;
  letter-spacing: 1px;
}

.login-button {
  width: 100%;
  height: 50px;
  border-radius: 4px;
  border: none;
  color: white;
  background-color: #b2b2b2;
  margin-top: 30px;
  font-weight: bold;
  background: #af5cd4;
}
.login-form div:first-of-type {
  margin-top: 40px;
  font-weight: bold;
  font-size: 18px;
}
.social-login-form {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.image-button {
  border: none;
  background-color: transparent;
  margin: 0 10px;
}

.image-button img {
  transition: transform 0.2s;
  width: 43px;
  height: 43px;
  border-radius: 6px;
}

.image-button:hover img {
  transform: scale(1.1);
}
.find {
  margin-top: 40px;
}
.find-button,
.regist-button {
  border: none;
  background-color: transparent;
  color: #af5cd4;
  margin-left: 5px;
  font-weight: bold;
  margin-top: 20px;
}
</style>
