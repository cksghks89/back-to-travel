<template>
  <div>
    <h2 style="font-weight: bold" class="title">관련 뉴스</h2>
    <div v-for="news in getNewsInfo" :key="news.title">
      <a class="news-card" :href="news.link">
        <h3 class="news-title" v-html="news.title"></h3>
        <a v-html="news.description"></a>
      </a>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "AttractionNews",

  data() {
    return {};
  },

  mounted() {},
  methods: {
    ...mapActions("attractionStore", ["getNews"]),
  },
  computed: {
    ...mapGetters("attractionStore", ["getAttractionDetail", "getNewsInfo"]),
  },
  watch: {
    getAttractionDetail() {
      this.getNews({ query: this.getAttractionDetail.title });
    },
  },
  created() {},
};
</script>

<style scoped>
.title {
  margin-top: 20px;
  margin-bottom: 20px;
  color: black;
}
.news-card {
  /* box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); */
  border: 2px solid rgb(240, 240, 240);
  border-radius: 4px;
  overflow: hidden;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  text-decoration: none;
}

.news-card img {
  width: 100%;
  object-fit: cover;
}

.news-card h3 {
  font-size: 18px;
  font-weight: bold;
  margin: 16px 20px;
}

.news-card p {
  font-size: 16px;
  margin: 8px 0;
}

.news-card a {
  color: #2196f3;
  text-decoration: none;
  margin: 0 20px 16px 20px;
  margin-bottom: 16px;
}

.news-card:hover {
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.15);
  transform: scale(1.02);
  transition: 0.3s;
}
.news-title {
  font-size: 18px;
  font-weight: bold;
  margin: 16px 0;
  color: black;
}
</style>
