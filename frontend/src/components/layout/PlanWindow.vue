<template>
  <div class="window-container" style="display: flex" v-if="hide">
    <div>
      <img
        style="width: 100px; height: 100px; border-radius: 10px"
        :src="
          plans.attractionSequence[0].firstImage ||
          require('@/assets/no-image.png')
        "
      />
    </div>
    <div>
      <div style="font-weight: 600; margin: 0 0 10px 10px; font-size: 14px">
        {{ plans.title }}
      </div>
      <div class="card-hit">
        <div class="pk">#{{ plans.planId }}</div>
        <img src="@/assets/eye.png" alt="" />
        <span>{{ plans.wishCnt }}</span>
      </div>
    </div>
    <router-link
      :to="`/plan/detail/${plans.planId}`"
      tag="button"
      class="detail-button"
    >
      <img src="@/assets/more.png" alt="" />
    </router-link>
  </div>
</template>

<script>
import http from "@/api/http.js";

export default {
  props: ["message"],
  data() {
    return {
      plans: null,
      hide: true,
    };
  },
  methods: {
    parseNumber(msg) {
      const regex = /#(\d+)/; // 정규표현식 패턴

      const match = regex.exec(msg); // 정규표현식 매칭
      if (match) {
        let number = match[1];
        console.log(number);
        return number;
      }
    },
  },

  computed: {},

  async mounted() {
    console.log(this.message);
    let num = this.parseNumber(this.message);
    await http
      .get(`/attraction/plan/detail/${num}`)
      .then((response) => {
        this.plans = response.data;
      })
      .catch((err) => {
        console.log(num, " - planId가 존재하지 않음");
      });
    if (this.plans == null) {
      this.hide = false;
      return;
    }
    console.log("plan 존재", this.plans);
  },
};
</script>

<style scoped>
.window-container {
  position: relative;
  border-radius: 10px;
  width: 250px;
  margin: 5px auto 20px auto;
  /* background-color: rgb(238, 238, 238); */
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
}

.card-hit {
  position: absolute;
  bottom: 2px;
  left: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
  padding: 5px;
}

.card-hit img {
  width: 20px;
  height: 20px;
  margin-right: 8px;
}
.pk {
  border: 1px solid rgb(224, 224, 224);
  padding: 0 5px;
  margin-right: 5px;
}

.detail-button {
  position: absolute;
  bottom: 6px;
  right: 3px;
}
.detail-button img {
  width: 25px;
}
button {
  border: none;
  background: none;
}
</style>
