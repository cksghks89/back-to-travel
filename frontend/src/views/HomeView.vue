<template>
  <div class="home-view">
    <!-- 메인 페이지 -->
    <main-banner></main-banner>
    <popular-attraction></popular-attraction>
    <popular-plan></popular-plan>
    <popular-hot-place></popular-hot-place>
  </div>
</template>

<script>
import MainBanner from "@/components/home/MainBanner.vue";
import PopularAttraction from "@/components/home/PopularAttraction.vue";
import PopularPlan from "@/components/home/PopularPlan.vue";

export default {
  name: "HomeView",
  components: {
    MainBanner,
    PopularAttraction,
    PopularPlan,
  },
};
</script>
<style scoped>
.home-view {
  margin-top: 10px;
}
</style>
