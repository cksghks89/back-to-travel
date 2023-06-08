<template>
  <div>
    <b-card
      img-top
      tag="article"
      style="width: 250px; height: 450px"
      class="mb-4 attraction-card"
    >
      <router-link :to="`/attraction/detail/${att.contentId}`" tag="div">
        <div
          class="img-container flip-card"
          :style="{
            backgroundImage: `url(${
              att.firstImage ? att.firstImage : require('@/assets/no-image.png')
            })`,
          }"
        ></div>
      </router-link>
      <div class="card-content">
        <b-card-title class="text-container">{{ att.title }}</b-card-title>
        <b-card-text> {{ att.addr1 + " " + att.addr2 }} </b-card-text>
        <b-button class="like-button" v-if="isLogin">
          <img
            v-if="!isHeart"
            @click="toggleLike(att.contentId)"
            src="@/assets/halfHeart.svg"
            alt=""
          />
          <img
            v-if="isHeart"
            @click="toggleLike"
            src="@/assets/heart.svg"
            alt=""
          />
        </b-button>
        <div class="card-hit">
          <img src="@/assets/eye.png" alt="" />
          <span>{{ att.readCount }}</span>
        </div>
        <div class="card-heart">
          <img src="@/assets/favourite.png" alt="" /> {{ heartCnt }}
        </div>
      </div>
    </b-card>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapActions, mapState } from "vuex";

export default {
  name: "AttractionCard",
  props: {
    att: Object,
  },

  data() {
    return { isHeart: false, heartCnt: 0 };
  },

  async updated() {
    this.isThisLike(this.att.contentId)
      .then((response) => {
        if (response.data.message === "yes") this.isHeart = true;
        else this.isHeart = false;
      })
      .catch(() => {});
    this.getHeart();
  },
  watch: {},
  computed: {
    ...mapState("userStore", ["isLogin"]),
  },

  methods: {
    ...mapActions("attractionStore", ["isThisLike", "doLike"]),
    async toggleLike() {
      await this.doLike(this.att.contentId);
      this.isThisLike(this.att.contentId)
        .then((response) => {
          if (response.data.message === "yes") {
            this.isHeart = true;
          } else {
            this.isHeart = false;
          }
        })
        .catch(() => {});
    },

    async getHeart() {
      await http
        .get(`/attraction/heart/${this.att.contentId}`)
        .then((response) => {
          this.heartCnt = response.data.heartCnt;
          return response.data;
        })
        .catch((error) => {
          console.log("getHeart FAILED");
        });
    },
  },
  async mounted() {
    this.getHeart();
  },
};
</script>

<style scoped>
.attraction-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
/* .container > div:last-child */
.attraction-list > div:last-child {
  justify-self: start;
}

.img-container {
  height: 250px;
  background-size: cover;
  background-position: center;
  border-radius: 5px;
  cursor: pointer;
}
.card-content {
  margin-right: 16px;
  margin-left: 16px;
  margin-top: 16px;
}
.card-body {
  padding: 0;
}

.card-title .card-text {
  margin-left: 16px;
  margin-right: 16px;
}
.like-button {
  position: absolute;
  top: 5px;
  right: 3px;
  background: none;
  border: none;
}

-like-button:active {
  background: none;
}

.like-button img {
  width: 30px;
  height: 30px;
  object-fit: cover;
}

.text-container {
  /* white-space: nowrap; 텍스트를 한 줄로 유지합니다. */
  overflow: hidden; /* 넘친 텍스트를 숨깁니다. */
}

.attraction-card {
  transition: transform 0.3s;
}

.attraction-card:hover {
  transform: translateY(-10px);
}

.card-hit {
  position: absolute;
  bottom: 2px;
  right: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 5px;
  padding: 5px;
}
.card-hit img {
  z-index: 1;
  width: 20px;
  height: 20px;
  margin-right: 5px;
}
.card-heart {
  position: absolute;
  bottom: 9px;
  left: 10px;
}
.card-heart img {
  width: 20px;
  height: 20px;
}
</style>
