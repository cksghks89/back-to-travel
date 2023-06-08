<template>
  <div class="image-modal" v-if="isOpen">
    <div class="modal-content">
      <img
        @click="closeModal"
        class="model-img"
        :src="imageSrc"
        alt="Modal Image"
      />
    </div>
  </div>
</template>
  
  <script>
export default {
  name: "ImageModal",
  props: ["isOpen", "imageSrc"],
  methods: {
    closeModal() {
      this.$emit("close");
    },
  },
  created() {},
};
</script>
  
  <style scoped>
/* 모달 스타일링 */
.image-modal {
  cursor: pointer;
}
</style>