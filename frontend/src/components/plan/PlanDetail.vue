<template>
  <div>
    <div><h2>여행계획 상세보기</h2></div>
    <div class="plan-container">
      <detail-kakao-map></detail-kakao-map>
      <div class="result-container">
        <div>
          <img
            style="width: 200px; margin-left: 40px"
            src="@/assets/planDetail.png"
          />
        </div>
        <div
          class="list-box"
          v-for="(item, index) in plans.attractionSequence"
          :key="index"
          style="display: flex"
        >
          <div>
            <img
              style="width: 70px; height: 100px"
              :src="item.firstImage || require('@/assets/no-image.png')"
            />
          </div>
          <div>
            <router-link :to="`/attraction/detail/${item.contentId}`">
              <div style="font-weight: 600; margin: 0 0 10px 10px">
                {{ item.title }}
              </div>
            </router-link>
            <div style="font-size: 13px; margin: 0 0 10px 10px">
              {{ item.addr1 }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DetailKakaoMap from "@/components/map/DetailKakaoMap.vue";
import { mapState, mapMutations, mapActions } from "vuex";

export default {
  name: "PlanDetail",
  components: { DetailKakaoMap },
  data() {
    return {};
  },
  computed: {
    ...mapState("planDetailStore", ["plans"]),
  },
  methods: {
    ...mapActions("planDetailStore", ["getPlanListByPlanId"]),
    ...mapMutations("planDetailStore", ["CLEAR_STATE", "PLAN_INIT"]),
  },

  async created() {
    // 지도 정보 초기화 작업
    this.PLAN_INIT();
    this.getPlanListByPlanId(this.$route.params.id);
    console.log(this.plans);
  },
};
</script>

<style scoped>
/* 기존 코드 */
.plan-container {
  display: flex;
}

/* 추가된 스타일 */
h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

select {
  margin: 5px;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input {
  width: 180px;
  margin: 5px;
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  margin: 5px;
  padding: 7px 14px;
  border: none;
  border-radius: 5px;
  background-color: #bb66f4;
  color: #fff;
  cursor: pointer;
}

button:hover {
  background-color: #ae42f7;
}

.search-container,
.result-container {
  height: 730px;
  background-color: #fffaff;
  width: 300px;
  overflow-y: scroll;
  padding: 10px;
  border-right: 1px solid #ccc;
}

.search-container::-webkit-scrollbar {
  width: 8px;
}
.search-container::-webkit-scrollbar-thumb {
  background-color: #e5e5e5;
  border-radius: 10px;
}
.result-container::-webkit-scrollbar {
  width: 8px;
}

.result-container::-webkit-scrollbar-thumb {
  background-color: #e5e5e5;
  border-radius: 10px;
}
.search-result img {
  width: 200px;
  height: 200px;
}
.plan-kakao-map {
  width: 800px;
  height: 730px;
}

.search-title {
  font-size: 18px;
}

.search-button {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}
.search-result {
  border: none;
  border-radius: 5px;
  margin-top: 10px;
}
.search-addr {
  font-size: 14px;
  color: gray;
  margin-bottom: 5px 14px;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #ce8cfb;
}
.addr-card {
  font-size: 12px;
}

.list-box {
  margin: 10px 0;
}
</style>
