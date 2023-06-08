<template>
  <div class="login-out-container">
    <div class="img-container">
      <img src="@/assets/login_logo.png" />
    </div>
    <div class="login-container">
      <div class="login-form">
        <h2 class="title">비밀번호 찾기</h2>
        <input
          class="login-input"
          v-model="id"
          type="text"
          placeholder="아이디를 입력해주세요"
        />
        <input
          class="login-input"
          v-model="email"
          type="email"
          placeholder="이메일을 입력해주세요"
        />
        <button class="login-button" @click="findPasswordEvent">
          임시 비밀번호 발급
        </button>
        <p class="description" style="text-align: center">
          가입하신 아이디와 이메일을 입력하면<br />
          입력한 이메일로 임시 비밀번호가 발송됩니다.
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "FindPassword",
  data() {
    return {
      id: "",
      email: "",
    };
  },

  computed: {},

  methods: {
    ...mapActions("userStore", ["findPassword"]),
    findPasswordEvent() {
      if (!this.id || !this.email) {
        alert("아이디, 이메일을 모두 입력해 주세요.");
        return;
      }

      this.findPassword({ userId: this.id, email: this.email })
        .then(() => {
          alert("임시 비밀번호가 발송되었습니다.");
          this.$router.push("/user/login");
        })
        .catch(() => {
          alert("아이디, 이메일을 다시 확인해 주세요.");
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

.description {
  margin-top: 50px;
}
</style>
