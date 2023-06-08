<template>
  <div>
    <div><h2>여행계획 짜기</h2></div>
    <div>
      <select name="sido" @change="sidoChange()" v-model="selectedSido">
        <option value="0">시/도 전체</option>
        <option
          v-for="(item, index) in getSido"
          :key="index"
          :value="item.sidoCode"
        >
          {{ item.sidoName }}
        </option>
      </select>

      <select name="sido" v-model="selectedGugun">
        <option value="0">구/군 전체</option>
        <option
          v-for="(item, index) in getGugun"
          :key="index"
          :value="item.gugunCode"
        >
          {{ item.gugunName }}
        </option>
      </select>

      <select name="sido" v-model="selectedContentTypeId">
        <option
          v-for="(item, index) in attractionType"
          :key="index"
          :value="item.code"
        >
          {{ item.text }}
        </option>
      </select>
      <input
        @keyup.enter="searchEvent"
        v-model="query"
        type="text"
        placeholder="검색어 입력"
      />
      <button @click="searchEvent">검색</button>
    </div>
    <div class="plan-container">
      <div class="search-container">
        <div
          class="list-box"
          v-for="(item, index) in searchList"
          :key="index"
          style="display: flex; margin-bottom: 10px"
        >
          <div>
            <img
              style="width: 70px; height: 80px"
              :src="item.firstImage || require('@/assets/no-image.png')"
            />
          </div>
          <div>
            <div
              @click="addPlan(item)"
              style="font-weight: 600; margin: 0 0 10px 10px; cursor: pointer"
            >
              {{ item.title }}
            </div>
            <div style="font-size: 13px; margin: 0 0 10px 10px">
              <router-link :to="`/attraction/detail/${item.contentId}`">
                <img
                  src="@/assets/weather.png"
                  style="
                    width: 20px;
                    height: 20px;
                    margin-right: 10px;
                    cursor: pointer;
                  "
                />
              </router-link>
              <img
                src="@/assets/plusButton.png"
                @click="addPlan(item)"
                style="width: 20px; height: 20px; cursor: pointer"
              />
            </div>
          </div>
        </div>
      </div>
      <plan-kakao-map> </plan-kakao-map>
      <div class="result-container">
        <input v-model="title" type="text" placeholder="제목을 입력하세요." />
        <div
          class="list-box"
          v-for="(item, index) in planList"
          :key="index"
          style="display: flex; margin: 10px 0"
        >
          <div>
            <img
              style="width: 70px; height: 80px"
              :src="item.firstImage || require('@/assets/no-image.png')"
            />
          </div>
          <div>
            <div
              @click="addPlan(item)"
              style="font-weight: 600; margin: 0 0 10px 10px; cursor: pointer"
            >
              {{ item.title }}
            </div>
            <div style="font-size: 13px; margin: 0 0 10px 10px">
              <img
                class="deleteButton"
                src="@/assets/delete-button.png"
                @click="deletePlan(item)"
                style="width: 15px; height: 15px; cursor: pointer"
              />
            </div>
          </div>
        </div>
        <div v-if="planList">
          <b-button @click="clearPlanList">CLEAR</b-button>
          <b-button @click="savePlanEvent">Save</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PlanKakaoMap from "@/components/map/PlanKakaoMap.vue";
import { mapState, mapMutations, mapActions } from "vuex";
import http from "@/api/http";

export default {
  name: "PlanMake",
  components: { PlanKakaoMap },
  data() {
    return {
      title: "",
      sidoList: null,
      gugunList: null,
      selectedSido: 0,
      selectedGugun: 0,
      selectedContentTypeId: 0,
      query: "",
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
  computed: {
    ...mapState("planStore", [
      "mapSize",
      "mapLocation",
      "mapMarker",
      "searchList",
      "planList",
    ]),
    getSido() {
      return this.sidoList;
    },
    getGugun() {
      return this.gugunList;
    },
  },
  methods: {
    ...mapActions("planStore", ["querySearch", "savePlan"]),
    ...mapMutations("planStore", [
      "CLEAR_STATE",
      "PLAN_INIT",
      "ADD_PLAN",
      "CLEAR_PLAN_LIST",
      "DELETE_PLAN",
    ]),
    async sidoChange() {
      await http
        .get(`/attraction/gugun/${this.selectedSido}`)
        .then((response) => (this.gugunList = response.data));
    },
    searchEvent() {
      // 기본 검색 (검색시 가장 위에 있는 관광지 기준 반경 2km 검색)

      this.querySearch({
        sidoCode: this.selectedSido,
        gugunCode: this.selectedGugun,
        contentTypeId: this.selectedContentTypeId,
        query: this.query,
      });
    },
    addPlan(item) {
      this.ADD_PLAN(item);
    },
    savePlanEvent() {
      if (!this.title) {
        alert("제목을 입력해 주세요");
        return;
      }
      this.savePlan({ title: this.title, plan: this.planList })
        .then(() => {
          alert("계획이 저장되었습니다.");
          this.$router.push("/plan/list");
        })
        .catch((err) => {
          console.log(err);
          alert("계획 저장에 실패하였습니다.");
        });
    },
    clearPlanList() {
      this.CLEAR_PLAN_LIST();
    },
    deletePlan(index) {
      this.DELETE_PLAN(index);
    },
  },

  async created() {
    // 지도 정보 초기화 작업
    this.PLAN_INIT();

    await http
      .get("/attraction/sido")
      .then((response) => (this.sidoList = response.data));
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
  background-color: #f5f5f5;
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

.deleteButton {
  position: absolute;
  right: 0;
  bottom: 0;
}
.list-box {
  position: relative;
}
</style>
