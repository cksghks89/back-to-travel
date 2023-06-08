<template>
  <div class="popular-attraction">
    <h2 class="title">Popular Plan</h2>

    <div class="d-flex justify-content-between plan-card">
      <b-card
        v-for="(item, index) in getPlansOrderCnt"
        :key="index"
        img-top
        style="width: 390px"
      >
        <router-link :to="`/plan/detail/${item.planId}`">
          <b-card-img
            :src="
              item.attractionSequence[0].firstImage ||
              require('@/assets/no-image.png')
            "
            alt="Image"
            top
            class="card-img"
          ></b-card-img>
        </router-link>
        <div class="text-group">
          <b-card-title class="popular-plan-text">{{
            item.title
          }}</b-card-title>
          <b-card-text>
            <div class="card-hit">
              <div class="pk">#{{ item.planId }}</div>
              <img src="@/assets/eye.png" alt="" />
              <span>{{ item.wishCnt }}</span>
            </div>
          </b-card-text>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";

export default {
  name: "PopularPlan",
  data() {
    return {};
  },
  methods: {
    ...mapActions("planStore", ["getPlans"]),
  },
  computed: {
    ...mapState("planStore", ["plans"]),
    ...mapGetters("planStore", ["getPlansOrderCnt"]),
  },

  async created() {
    await this.getPlans();
    console.log(this.plans);
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

.card-hit {
  margin-top: 10px;
  bottom: 2px;
  display: flex;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 5px;
}

.card-hit img {
  width: 20px;
  height: 20px;
  margin-right: 8px;
}

.pk {
  border: 1px solid rgb(224, 224, 224);
  padding: 0 5px;
  margin-right: 20px;
}

.card-img {
  width: 390px;
  height: 390px;
  object-fit: cover;
}
.card-body {
  padding: 0;
}
.text-group {
  padding: 20px;
}
</style>
