<template>
  <div></div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "NaverLoginCallback",
  methods: {
    ...mapActions("userStore", ["sendNaverCode"]),
  },
  async created() {
    let code = this.$route.query.code;
    await this.sendNaverCode(code);
    this.$router.push("/");
  },
};
</script>

<style scoped></style>
