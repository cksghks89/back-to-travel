<template>
  <div class="container">
    <div class="personal__title">
      <h2>My Plans</h2>
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
              @click.prevent="openModal(i.contentId)"
              :to="`/attraction/detail/${i.contentId}`"
              class="plan-track"
            >
              {{ i.title }}
            </router-link>
            <span v-if="index !== item.attractionSequence.length - 1">-></span>
          </span>
        </b-card-text>
        <router-link
          :to="`/plan/detail/${item.planId}`"
          tag="button"
          class="detail-button"
        >
          <img src="@/assets/more.png" alt="" />
        </router-link>
      </b-card>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "MyPlansProfile",

  data() {
    return {
      planList: [],
    };
  },
  computed: {
    ...mapState("myPlanStore", ["plans"]),
  },
  mounted() {
    this.getPlanListByUserId().then(() => {
      this.planList = this.getPlans;
    });
  },
  methods: {
    ...mapActions("myPlanStore", ["getMyPlans"]),
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
  },
  created() {
    this.getMyPlans();
  },
};
</script>

<style scoped>
.container {
  padding-top: 30px;
  padding-left: 30px;
  padding-right: 30px;
  margin-left: 30px;
  border: 1px solid #e5e5e5;
  border-radius: 10px;
}
.btn-change {
  margin-top: 10px;
  margin-left: 30px;
}
.btn-change:hover {
  background-color: blue;
  color: black;
}
.warning h6 {
  color: red;
  font-weight: bold;
  margin-bottom: 30px;
}
.warning {
  margin-top: 30px;
}
.personal__body {
  margin-top: 50px;
  margin-bottom: 30px;
  overflow-y: scroll;
  max-height: 487px;
}
.personal__body::-webkit-scrollbar {
  width: 8px;
}
.personal__body::-webkit-scrollbar-thumb {
  background-color: #e5e5e5;
  border-radius: 10px;
}
label {
  color: #85878a;
}
.gender-label {
  color: #85878a;
  margin-bottom: 8px;
}
.row {
  margin-bottom: 20px;
}
.submit-button {
  margin-top: 50px;
}
.personal__title h2 {
  font-weight: bold;
  margin-bottom: 30px;
}
img {
  min-height: 100%;
  width: 100px;
}
.detail-button {
  position: absolute;
  bottom: 0;
  right: 0;
  border: none;
  background: none;
  padding-right: 6px;
}
.detail-button img {
  width: 30px;
  height: 30px;
}

.main-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 120px;
}
.delete-button img {
  width: 15px;
  height: 15px;
}
.delete-button {
  position: absolute;
  top: 0;
  right: 0;
  background: none;
  border: none;
  padding-right: 12px;
}
</style>
