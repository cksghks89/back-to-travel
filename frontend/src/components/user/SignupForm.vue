<template>
  <div class="login-out-container">
    <div class="img-container">
      <img src="@/assets/login_logo.png" />
    </div>
    <div class="login-container">
      <div class="login-form">
        <h2 class="title">SIGN UP</h2>

        <div class="login-row">
          <label>아이디</label>
          <input class="login-input" type="text" v-model="id" />
        </div>
        <div class="login-row">
          <label>비밀번호</label>
          <input class="login-input" type="password" v-model="password" />
        </div>
        <div class="login-row">
          <label>비밀번호 확인</label>
          <input
            class="login-input"
            :class="{ 're-password': isCorrect }"
            type="password"
            v-model="rePassword"
            @keyup="inputRePassword"
          />
        </div>
        <div class="login-row">
          <label>닉네임</label>
          <input class="login-input" type="text" v-model="nickName" />
        </div>
        <div class="login-row">
          <label>이메일</label>
          <input class="login-input" type="email" v-model="email" />
        </div>

        <div class="login-row">
          <div class="selection-heading">성별 선택</div>
          <div class="button-group">
            <label class="button-label">
              <input type="radio" name="gender" value="M" v-model="gender" />
              <span class="button">남</span>
            </label>
            <label class="button-label">
              <input type="radio" name="gender" value="F" v-model="gender" />
              <span class="button">여</span>
            </label>
          </div>
        </div>
        <button class="login-button" @click="signupEvent">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "SignupForm",
  data() {
    return {
      id: "",
      password: "",
      rePassword: "",
      nickName: "",
      email: "",
      gender: "",
      isCorrect: false,
    };
  },

  computed: {},

  methods: {
    ...mapActions("userStore", ["signup"]),
    signupEvent() {
      if (
        !(
          this.id &&
          this.password &&
          this.rePassword &&
          this.nickName &&
          this.email &&
          this.gender
        )
      ) {
        alert("모든 값을 입력해주세요");
        return;
      }

      const user = {
        userId: this.id,
        password: this.password,
        nickName: this.nickName,
        email: this.email,
        gender: this.gender,
      };

      this.signup(user)
        .then((response) => {
          alert("회원가입 완료! 계정 활성화 메일이 발송되었습니다");
          this.$router.push("/user/login");
        })
        .catch(() => {
          alert("회원가입에 실패하였습니다.");
        });
    },

    inputRePassword() {
      if (this.password !== this.rePassword) {
        this.isCorrect = true;
      } else {
        this.isCorrect = false;
      }
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
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 50px;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
}

.login-row {
  display: flex;
  width: 100%;
  margin-bottom: 30px;
}

.login-row label {
  font-size: 15px;
  font-weight: bold;
  margin-bottom: 5px;
  width: 30%;
}

.login-input {
  border-radius: 4px;
  border: 1px solid #b2b2b2;
  width: 70%;
  height: 45px;
  padding-left: 10px;
  font-size: 16px;
}

.gender-selection {
  display: flex;
  flex-direction: row;
  margin-top: 20px;
}

.selection-heading {
  margin-bottom: 10px;
  text-align: center;
  color: #333333;
  font-size: 15px;
  font-weight: bold;
  margin-right: 80px;
}

.button-group {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button-label {
  display: flex;
  align-items: center;
  margin-right: 10px;
  margin-bottom: 10px;
}

.button-label input[type="radio"] {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

.button-label .button {
  display: inline-block;
  padding: 8px 16px;
  border: 1px solid #b2b2b2;
  border-radius: 4px;
  color: #333333;
  background-color: #ffffff;
  cursor: pointer;
}

.button-label .button:hover {
  border-color: #af5cd4;
  color: #af5cd4;
  background-color: #ffffff;
}

.button-label input[type="radio"]:checked + .button {
  border-color: #af5cd4;
  color: #af5cd4;
  background-color: #ffffff;
}

.button-label input[type="radio"]:focus + .button {
  box-shadow: 0 0 0 2px rgba(175, 92, 212, 0.5);
}
.login-button {
  width: 100%;
  height: 45px;
  border-radius: 4px;
  border: none;
  background-color: #af5cd4;
  color: #ffffff;
  font-size: 16px;
  font-weight: bold;
  margin-top: 20px;
  cursor: pointer;
}
.re-password {
  border: 1px solid red;
}
</style>
