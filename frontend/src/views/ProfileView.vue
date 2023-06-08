<template>
  <div class="profile-view">
    <main-profile class="side-profile-box"></main-profile>
    <router-view></router-view>
  </div>
</template>

<script>
import MainProfile from "@/components/profile/MainProfile.vue";
export default {
  name: "ProfileView",
  components: {
    MainProfile,
  },
  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style scoped>
.profile-view {
  display: flex;
}
.side-profile-box {
  height: 500px !important;
}

.profile-view::-webkit-scrollbar {
  width: 8px;
}

.profile-view::-webkit-scrollbar-thumb {
  background-color: #e5e5e5;
  border-radius: 10px;
}
</style>
