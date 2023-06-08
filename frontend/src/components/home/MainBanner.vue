<template>
  <div class="main-banner">
    <div class="image-container">
      <img src="@/assets/banner_image.png" alt="홈페이지 메인 이미지" />
      <div class="text-overlay">
        <h2>
          Welcome to <span style="color: #000">Enjoy</span
          ><span style="color: #af5cd4">Trip!!</span>
        </h2>
        <p class="sub">
          여행지를 고르고, 계획을 세우고, 다양한 사람들과 공유해보세요
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainBanner",
  components: {},
  data() {
    return {};
  },
  created() {},
  methods: {},
};
</script>

<style scoped>
.main-banner {
  width: 1200px;
  height: 400px;
}

.image-container {
  position: relative;
}

.image-container img {
  width: 1200px;
  height: 400px;
  border-radius: 15px;
}

.text-overlay {
  position: absolute;
  top: 100px;
  right: 60px;
}

.text-overlay h2 {
  font-size: 40px;
  text-align: center;
  font-weight: 900;
  font-family: "KCC-Ganpan";
  color: #ffffff;
}

.text-overlay p {
  font-size: 15px;
  margin-top: 100px;
  font-weight: 700;
}

@font-face {
  font-family: "KCC-Ganpan";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302@1.0/KCC-Ganpan.woff2")
    format("woff2");
  font-weight: normal;
  font-style: normal;
}
</style>
