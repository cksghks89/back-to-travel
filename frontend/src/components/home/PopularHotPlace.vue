<template>
  <div class="popular-attraction">
    <h2 class="title">Hot Place</h2>

    <div class="d-flex justify-content-between">
      <b-card
        img-src="https://picsum.photos/300/300/?image=41"
        img-alt="Image"
        img-top
        style="width: 390px"
      >
        <b-card-title>hello</b-card-title>
        <b-card-text>
          This card has supporting text below as a natural lead-in to additional
          content.
        </b-card-text>
      </b-card>
      <b-card
        title="Title"
        img-src="https://picsum.photos/300/300/?image=41"
        img-alt="Image"
        img-top
        style="width: 390px"
      >
        <b-card-text>
          This card has supporting text below as a natural lead-in to additional
          content.
        </b-card-text>
      </b-card>
      <b-card
        title="Title"
        img-src="https://picsum.photos/300/300/?image=41"
        img-alt="Image"
        img-top
        style="width: 390px"
      >
        <b-card-text>
          This card has supporting text below as a natural lead-in to additional
          content.
        </b-card-text>
      </b-card>
    </div>
  </div>
</template>

<script>

export default {
  name: "PopularHotPlace",
  data() {
    return {
      attractionType: [
        { text: "전체", src: require("@/assets/popular_icon/select-all.png") },
        {
          text: "관광지",
          src: require("@/assets/popular_icon/attraction.png"),
        },
        { text: "문화시설", src: require("@/assets/popular_icon/culture.png") },
        {
          text: "축제",
          src: require("@/assets/popular_icon/festival.png"),
        },
        { text: "레포츠", src: require("@/assets/popular_icon/sports.png") },
        { text: "숙박", src: require("@/assets/popular_icon/bed.png") },
        { text: "쇼핑", src: require("@/assets/popular_icon/shopping.png") },
        { text: "음식점", src: require("@/assets/popular_icon/food.png") },
      ],
    };
  },
  computed: {},
  methods: {},
  async created() {},
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
</style>