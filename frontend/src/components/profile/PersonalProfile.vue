<template>
  <div class="container">
    <div class="personal__title">
      <h2>Personal Information</h2>
    </div>
    <div class="personal__body">
      <div class="personal__body__img">
        <img
          class="avatar-image"
          id="profile-image-preview"
          :src="require('@/assets/no-profile.png')"
          alt="Profile Image Preview"
        />

        <!-- 프로필 사진 업로드 버튼 -->
        <label for="profile-image-input" id="upload-button-label">change</label>
        <input
          ref="inputEl"
          type="file"
          id="profile-image-input"
          @change="changeFile"
        />
      </div>
      <b-row>
        <b-col>
          <b-form @submit.stop.prevent>
            <label for="feedback-user">ID</label>
            <b-form-input
              v-model="userId"
              :state="validation"
              id="feedback-user"
              disabled
            ></b-form-input>
          </b-form>
        </b-col>
        <b-col>
          <b-form @submit.stop.prevent>
            <label for="feedback-user">Email address</label>
            <b-form-input
              v-model="email"
              :state="validation"
              id="feedback-user"
              disabled
            ></b-form-input>
          </b-form>
        </b-col> </b-row
      ><b-row>
        <b-col>
          <b-form @submit.stop.prevent>
            <label for="feedback-user">Nickname</label>
            <b-form-input
              v-model="nickName"
              :state="validation"
              id="feedback-user"
              @keyup="validateNickName"
              @keyup.enter="updateUserInfo"
            ></b-form-input>
          </b-form>
        </b-col>
        <b-col>
          <b-form-group v-slot="{ ariaDescribedby }">
            <label for="" class="gender-label">Gender</label>
            <b-row>
              <b-col>
                <b-form-radio
                  v-model="gender"
                  :aria-describedby="ariaDescribedby"
                  name="gender"
                  value="M"
                  >Male</b-form-radio
                >
              </b-col>
              <b-col>
                <b-form-radio
                  v-model="gender"
                  :aria-describedby="ariaDescribedby"
                  name="gender"
                  value="F"
                  >Female</b-form-radio
                >
              </b-col>
            </b-row>
            <b-row class="submit-button">
              <b-col class="offset-md-8">
                <b-button
                  @click="updateUserInfo"
                  variant="primary"
                  style="width: 100px"
                  >수정</b-button
                >
              </b-col>
            </b-row>
          </b-form-group>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import fileTokenHttp from "@/api/fileTokenHttp.js";

export default {
  name: "PersonalProfile",

  data() {
    return {
      userId: null,
      nickName: null,
      email: null,
      gender: null,
      isValidate: true,
    };
  },

  computed: {
    ...mapGetters("userStore", ["getUserId", "getUserInfo", "getIsDuplicate"]),
    getIsValidate() {
      return this.isValidate;
    },
  },

  watch: {
    getUserInfo() {
      this.userId = this.getUserInfo.userId;
      this.nickName = this.getUserInfo.nickName;
      this.email = this.getUserInfo.email;
      this.gender = this.getUserInfo.gender;
    },
  },

  methods: {
    ...mapActions("userStore", [
      "fetchUserInfo",
      "updateAccount",
      "checkNickName",
    ]),
    updateUserInfo() {
      if (confirm("수정하시겠습니까?") == false) return;
      const userInfo = {
        userId: this.userId,
        nickName: this.nickName,
        email: this.email,
        gender: this.gender,
      };

      if (this.isValidate) {
        this.updateAccount(userInfo).then(async () => {
          if (this.$refs.inputEl.files[0]) {
            const formData = new FormData();
            formData.append("file", this.$refs.inputEl.files[0]);
            await fileTokenHttp.put("/user/profile", formData);
          }

          this.fetchUserInfo(this.getUserId);
          alert("수정되었습니다.");
        });
      } else {
        alert("중복된 닉네임입니다.");
        this.nickName = this.getUserInfo.nickName;
      }
    },
    async validateNickName() {
      await this.checkNickName(this.nickName);
      this.isValidate = this.getIsDuplicate;
    },

    changeFile() {
      var file = this.$refs.inputEl.files[0];
      var imageType = /^image\//;

      if (imageType.test(file.type)) {
        // 이미지 파일인 경우, 이미지 미리보기
        var reader = new FileReader();
        reader.onload = function () {
          var imagePreview = document.getElementById("profile-image-preview");
          imagePreview.src = reader.result;
        };
        reader.readAsDataURL(file);
      }
    },
  },
  mounted() {
    this.fetchUserInfo(this.getUserId);
    this.userId = this.getUserInfo.userId;
    this.nickName = this.getUserInfo.nickName;
    this.email = this.getUserInfo.email;
    this.gender = this.getUserInfo.gender;
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
.avatar-image {
  margin-right: 30px;
  width: 70px;
  height: 70px;
}
.btn-change {
  margin-top: 10px;
  margin-left: 30px;
}
.btn-change:hover {
  background-color: blue;
  color: black;
}
.personal__body__img {
  margin-top: 30px;
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

/* 업로드 버튼 스타일 */
#upload-button-label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #514edb;
  color: #fff;
  cursor: pointer;
  border-radius: 5px;
  width: 90px;
  height: 45px;
}

/* input[type="file"] 숨기기 */
#profile-image-input {
  display: none;
}
</style>
