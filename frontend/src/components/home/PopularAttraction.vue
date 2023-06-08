<template>
  <div class="popular-attraction">
    <h2 class="title">Popular Attraction</h2>

    <div class="type-container">
      <div
        class="type-box"
        v-for="(type, index) in attractionType"
        :key="index"
        @click="showData(type.code)"
      >
        <div class="icon">
          <img
            :src="type.src"
            :class="{ imgActive: activeIndex == type.code }"
          />
        </div>
        <div class="type-text">
          <span>{{ type.text }}</span>
        </div>
      </div>
    </div>

    <div class="card-container">
      <b-card
        v-for="(item, index) in popularList"
        :key="index"
        no-body
        class="overflow-hidden"
        style="max-width: 590px"
      >
        <b-row no-gutters>
          <b-col md="6">
            <b-card-img
              style="cursor: pointer"
              @click="goDetail(item.contentId)"
              :src="item.firstImage"
              class="popular-img"
            ></b-card-img>
          </b-col>
          <b-col md="6">
            <b-card-body>
              <div class="card-title">{{ item.title }}</div>
              <b-card-text class="card-text">
                {{ item.overview }}
              </b-card-text>
            </b-card-body>
          </b-col>
        </b-row>
      </b-card>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "PopularAttraction",
  data() {
    // 12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스,
    // 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점
    return {
      activeIndex: 0,
      attractionType: [
        {
          text: "전체",
          src: require("@/assets/popular_icon/select-all.png"),
          code: 0,
        },
        {
          text: "관광지",
          src: require("@/assets/popular_icon/attraction.png"),
          code: 12,
        },
        {
          text: "문화시설",
          src: require("@/assets/popular_icon/culture.png"),
          code: 14,
        },
        {
          text: "축제",
          src: require("@/assets/popular_icon/festival.png"),
          code: 15,
        },
        {
          text: "레포츠",
          src: require("@/assets/popular_icon/sports.png"),
          code: 28,
        },
        {
          text: "숙박",
          src: require("@/assets/popular_icon/bed.png"),
          code: 32,
        },
        {
          text: "쇼핑",
          src: require("@/assets/popular_icon/shopping.png"),
          code: 38,
        },
        {
          text: "음식점",
          src: require("@/assets/popular_icon/food.png"),
          code: 39,
        },
      ],
    };
  },
  methods: {
    ...mapActions("homeStore", ["getPopularList"]),
    showData(code) {
      this.getPopularList(code);
      this.activeIndex = code;
    },
    goDetail(id) {
      this.$router.push(`attraction/detail/${id}`);
    },
  },
  computed: {
    ...mapGetters("homeStore", ["popularList"]),
  },

  created() {
    this.getPopularList(0);
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;1,500;1,700&display=swap");

.popular-attraction {
  width: 1200px;
  margin: 80px 0;
}

.title {
  text-align: center;
  margin: 30px;
  font-family: "Poppins";
  font-weight: 800;
}

.type-container {
  display: flex;
  justify-content: space-between;
}

.type-box {
  width: 130px;
  height: 130px;
  /* background-color: aquamarine; */
  box-shadow: 1px 1px 20px 5px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}
.type-box:hover {
  background-color: #b9e3ff;
  cursor: pointer;
}

.icon {
  text-align: center;
  margin-top: 20px;
}
.icon img {
  width: 40px;
  filter: invert(80%);
}

.type-text {
  text-align: center;
  margin-top: 25px;
  /* font-weight: 600; */
  font-family: "Poppins", sans-serif;
}

.card-container {
  display: flex;
  margin-top: 30px;
  justify-content: space-between;
}

.popular-img {
  height: 350px;
  object-fit: cover;
}
.card-text {
  height: 250px;
  overflow-y: scroll;
}
.card-text::-webkit-scrollbar {
  display: none;
}
.card-text::-webkit-scrollbar-track {
  background-color: #f5f5f5;
}
.card-title {
  font-family: "Poppins", sans-serif;
  font-weight: bold;
  font-size: 15px;
  margin-bottom: 15px;
}
.imgActive {
  filter: invert(0) !important;
}
</style>
