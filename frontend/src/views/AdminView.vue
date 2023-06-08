<template>
  <div class="profile-view">
    <admin-profile class="side-profile-box"></admin-profile>
    <router-view></router-view>
  </div>
</template>

<script>
import AdminProfile from "@/components/admin/AdminProfile.vue";

export default {
  name: "AdminView",
  components: {
    AdminProfile,
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
