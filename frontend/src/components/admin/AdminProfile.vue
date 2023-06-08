<template>
  <div class="container">
    <div class="profile">
      <div class="profile__header">
        <!-- <div class="profile__header__avatar">
          <img :src="require('@/assets/no-profile.png')" alt="avatar" />
        </div> -->
        <div class="profile__header__info">
          <div class="profile__header__info__name">
            <h3>{{ getUserInfo.nickName }}</h3>
          </div>
          <div class="profile__header__info__email">
            <h5>관리자 화면입니다.</h5>
          </div>
        </div>
        <div class="horizontal-line"></div>
      </div>
      <div class="profile__button__group">
        <router-link tag="button" to="/admin/stats"
          ><img
            src="@/assets/profile/myprofile.png"
            class="profile-icon"
          />사용자 로그 관리</router-link
        >
        <router-link tag="button" to="/admin/user">
          <img
            src="@/assets/profile/myplans.png"
            class="profile-icon"
          />회원관리</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
export default {
  name: "AdminProfile",

  data() {
    return {};
  },

  methods: {
    ...mapActions("userStore", ["fetchUserInfo"]),
    ...mapMutations("userStore", ["LOGOUT"]),
    logout() {
      this.LOGOUT();
      this.$router.push("/");
    },
  },

  computed: {
    ...mapGetters("userStore", ["getUserInfo", "getUserId"]),
  },
  created() {
    this.fetchUserInfo(this.getUserId);
  },
};
</script>

<style scoped>
.container {
  width: 270px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  background-color: #f5f5f6;
}

.profile {
  width: 100%;
}
h3 {
  font-size: 20px;
  font-weight: bold;
}
h5 {
  font-size: 14px;
  color: #666666;
}

.horizontal-line {
  border-top: 1px solid #d9dadb;
  margin: 10px 0;
  width: 90%;
}

.profile__header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 25px;
}

.profile__header__avatar img {
  width: 75px;
  height: 75px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 12px;
}

.profile__header__info {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 3px;
}

.profile__header__info__name {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
  font-size: 18px;
  font-weight: bold;
}

.profile__header__info__name h3 {
  margin-bottom: 3px;
  margin-top: 3px;
}

.profile__header__info__email {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #666666;
}

.profile__header__info__email h5 {
  margin-bottom: 3px;
  margin-top: 3px;
}

.profile__button__group {
  display: flex;
  flex-direction: column;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 30px;
  margin-top: 30px;
}

button {
  background-color: transparent;
  border: none;
  border-radius: 5px;
  margin-bottom: 8px;
  color: #333333;
  font-size: 14px;
  padding: 10px 12px;

  cursor: pointer;
  text-align: left;
}

button:hover {
  background-color: #e4e3f3;
}

button:last-child {
  margin-bottom: 0;
}

.profile-icon {
  width: 20px;
  height: 20px;
  filter: invert(20%);
  margin-right: 10px;
}
</style>
