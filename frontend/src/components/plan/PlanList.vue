<template>
  <div>
    <div class="banner">
      <img class="plan-banner" src="@/assets/plan-banner-no-text.png" alt="" />
      <p class="banner-text">
        나만의 여행 계획을 세우고 <br />다른 사람과 공유해보세요!!
      </p>
      <router-link class="banner-link" tag="button" to="/plan/make"
        >Add Plan</router-link
      >
    </div>

    <div class="plan-list">
      <b-card
        class="card"
        v-for="(item, index) in plans"
        :key="index"
        :img-src="getThumnail(item)"
        img-alt="Card image"
        img-left
      >
        <b-card-title>{{ item.title }}</b-card-title>
        <b-card-text>
          <span v-for="(i, index) in item.attractionSequence" :key="index">
            <router-link
              :to="`/attraction/detail/${i.contentId}`"
              class="plan-track"
            >
              {{ i.title }}
            </router-link>
            <span v-if="index !== item.attractionSequence.length - 1">-></span>
          </span>
        </b-card-text>
        <router-link
          :to="`detail/${item.planId}`"
          tag="button"
          class="detail-button"
        >
          <img src="@/assets/more.png" alt="" />
        </router-link>
        <button @click="copyPlan(index)" class="copy-button">
          <img src="@/assets/copy-icon.png" alt="" />
        </button>
        <div class="card-hit">
          <div class="pk">#{{ item.planId }}</div>
          <img src="@/assets/eye.png" alt="" />
          <span>{{ item.wishCnt }}</span>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from "vuex";
import tokenHttp from "@/api/tokenHttp";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import server from "@/api/server";
import http from "@/api/http";

export default {
  name: "PlanList",
  components: {},
  data() {
    return {
      currentPage: "",
      selectedContentId: null,
      getPlanList: [],
      stompClient: null,
      reciverNickName: "",
    };
  },

  computed: {
    ...mapState("planStore", ["plans"]),
    ...mapState("userStore", ["userInfo"]),
  },
  methods: {
    ...mapActions("planStore", ["getPlans", "getPlanWishCount"]),
    ...mapMutations("socketStore", ["SET_STOMP_CLIENT"]),
    async sendMessage(index) {
      if (this.userInfo == null) {
        alert("로그인 후 이용할 수 있습니다.");
      }

      if (this.stompClient != null) {
        let chatMessage = {
          senderNickName: this.userInfo.nickName,
          receiverNickName: this.plans[index].userId,
          title: "[알림]",
          content: "",
          planId: this.plans[index].planId,
        };
        this.stompClient.send("/pub/plan", JSON.stringify(chatMessage), {});
      }
    },
    getThumnail(item) {
      if (
        item &&
        item.attractionSequence.length != 0 &&
        item.attractionSequence[0].firstImage != ""
      ) {
        return item.attractionSequence[0].firstImage;
      } else {
        return require("@/assets/no-image.png");
      }
    },
    async copyPlan(index) {
      let self = this;
      await tokenHttp
        .post("/attraction/copyPlan", self.plans[index])
        .then(() => {
          this.sendMessage(index);
          alert("My Plan 에 추가되었습니다!");
        })
        .catch((err) => {
          console.log(err);
        });
      this.getPlans();
    },
  },
  async created() {
    await this.getPlans();

    let socket = new SockJS(`${server.baseURL}/ws`);
    this.stompClient = Stomp.over(socket);
  },
};
</script>

<style scoped>
.card {
  height: 150px;
  margin: 20px 0;
}

.card img {
  width: 230px;
}

.banner {
  position: relative;
}
.plan-banner {
  width: 1200px;
  opacity: 0.9;
  border-radius: 20px;
  margin-bottom: 30px;
}
.banner-text {
  position: absolute;
  top: 200px;
  left: 60px;
  color: white;
  font-size: 25px;
  font-weight: bold;
}

.banner-link {
  position: absolute;
  top: 398px;
  left: 66px;
  width: 175px;
  height: 58px;
  background: none;
  color: white;
  font-weight: bold;
  font-size: 30px;
}
button {
  border: none;
  background: none;
}
.detail-button {
  position: absolute;
  bottom: 6px;
  right: 10px;
}
.detail-button img {
  width: 25px;
}
.plan-track {
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
  font-size: 20px;
}
.copy-button {
  position: absolute;
  top: 6px;
  right: 10px;
  border: none;
}

.copy-button img {
  width: 20px;
}

.card-hit {
  position: absolute;
  bottom: 2px;
  /* left: 50px; */
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 5px;
  padding: 5px;
}

.card-hit img {
  width: 20px;
  height: 20px;
  margin-right: 8px;
}

.pk {
  border: 1px solid rgb(224, 224, 224);
  padding: 0 5px;
  margin-right: 20px;
}
</style>
