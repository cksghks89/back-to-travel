<template>
  <div style="display: flex">
    <div>
      <img
        style="width: 70px; height: 100px"
        :src="src || require('@/assets/no-image.png')"
      />
    </div>
    <div>
      <div style="font-weight: 600; margin: 0 0 10px 10px">
        {{ title }}
      </div>
      <div style="font-size: 13px; margin: 0 0 10px 10px">
        {{ addr1 }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["src", "title", "addr1"],
};
</script>

<style scoped></style>