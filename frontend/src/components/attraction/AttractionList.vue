<template>
  <div>
    <div class="type-container">
      <div
        class="type-box"
        v-for="(type, index) in attractionType"
        :key="index"
        @click="showData(type.code)"
        @mouseover="type.showImage = true"
        @mouseout="type.showImage = false"
      >
        <button
          class="type-text"
          :class="{ 'text-active': activeIndex == type.code }"
        >
          <img
            class="type-img"
            v-if="!type.showImage"
            :src="type.img"
            alt="Type Icon"
          />
          <span v-else>{{ type.text }}</span>
        </button>
      </div>
      <b-form-input
        @keyup.enter="searchData"
        class="search-input"
        v-model="search"
        placeholder="관광지 검색"
      ></b-form-input>
    </div>

    <div class="attraction-list">
      <div v-for="(att, index) in attractionList" :key="index">
        <attraction-card :att="att"></attraction-card>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import AttractionCard from "@/components/attraction/AttractionCard.vue";

export default {
  name: "AttractionList",
  components: { AttractionCard },
  data() {
    return {
      c_type: 0,
      search: "",
      activeIndex: 0,
      attractionType: [
        {
          text: "전체",
          img: require("@/assets/popular_icon/select-all.png"),
          code: 0,
          showImage: false,
        },
        {
          text: "관광지",
          img: require("@/assets/popular_icon/attraction.png"),
          code: 12,
          showImage: false,
        },
        {
          text: "문화시설",
          img: require("@/assets/popular_icon/culture.png"),
          code: 14,
          showImage: false,
        },
        {
          text: "축제",
          img: require("@/assets/popular_icon/festival.png"),
          code: 15,
          showImage: false,
        },
        {
          text: "레포츠",
          img: require("@/assets/popular_icon/sports.png"),
          code: 28,
          showImage: false,
        },
        {
          text: "숙박",
          img: require("@/assets/popular_icon/bed.png"),
          code: 32,
          showImage: false,
        },
        {
          text: "쇼핑",
          img: require("@/assets/popular_icon/shopping.png"),
          code: 38,
          showImage: false,
        },
        {
          text: "음식점",
          img: require("@/assets/popular_icon/food.png"),
          code: 39,
          showImage: false,
        },
      ],
    };
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },
  methods: {
    ...mapActions("attractionStore", ["getAttractions", "getAddAttraction"]),
    async handleScroll() {
      const contentHeight = document.documentElement.scrollHeight;
      const viewportHeight = window.innerHeight;
      const scrollPosition = window.scrollY;

      if (
        scrollPosition + viewportHeight >= contentHeight &&
        this.$route.path == "/attraction/list" &&
        Math.floor((this.attractionList.length - 1) / 12) + 1 ==
          this.pageResult.pageNo
      ) {
        await this.getAddAttraction({
          pageNo: this.pageResult.pageNo + 1,
          listSize: 12,
          type: this.type,
          query: this.search,
        });
      }
    },
    async showData(type) {
      await this.getAttractions({
        pageNo: 1,
        listSize: 12,
        type: type,
        query: "",
      }).catch(() => {});
      this.search = "";
      this.c_type = type;

      this.activeIndex = type;
    },

    async searchData() {
      await this.getAttractions({
        pageNo: 1,
        listSize: 12,
        type: this.c_type,
        query: this.search,
      });
    },
  },
  computed: {
    ...mapState("attractionStore", ["attractionList", "pageResult", "type"]),
  },
  created() {
    this.getAttractions({
      pageNo: 1,
      listSize: 12,
      type: 0,
      query: "",
    });
  },
};
</script>

<style scoped>
.type-container {
  display: flex;
  align-items: center;
}

.type-text {
  font-family: "TheJamsil5Bold";
  filter: invert(50%);
  cursor: pointer;
  border: none;
  background: none;
  width: 70px;
  height: 70px;
  font-size: 15px;
  margin-left: 8px;
  margin-right: 8px;
}
.attraction-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

article img {
  height: 300px;
}

.like-button {
  position: absolute;
  top: 5px;
  right: 3px;
  background: none;
  border: none;
}

.like-button:active {
  background: none;
}

.like-button img {
  width: 30px;
  height: 30px;
  /* overflow: hidden; */
  object-fit: cover;
}

.text-container {
  /* white-space: nowrap; 텍스트를 한 줄로 유지합니다. */
  overflow: hidden; /* 넘친 텍스트를 숨깁니다. */
}
.search-input {
  height: 30px;
  width: 200px;
}

.text-active {
  filter: invert(0%);
}
.type-img {
  width: 30px;
  height: 30px;
}
</style>
