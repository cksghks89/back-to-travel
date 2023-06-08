<template>
  <div>
    <div class="banner-img">
      <img @click="openModal" class="image" alt="" :src="getImagePath" />
    </div>
    <div class="map-container">
      <!-- kakao map -->
      <kakao-map class="map"></kakao-map>

      <div class="description">
        <h2>{{ attractionDetail.title }}</h2>
        <p>{{ attractionDetail.overview }}</p>
      </div>
    </div>

    <div class="detail-section">
      <div class="addr">
        <img
          style="height: 20px; margin-right: 10px"
          src="@/assets/marker/marker_location.png"
        />
        <span>{{ attractionDetail.addr1 + " " + attractionDetail.addr2 }}</span>
      </div>
    </div>

    <attraction-weather></attraction-weather>
    <attraction-news></attraction-news>

    <image-modal
      class="modal"
      :isOpen="isModalOpen"
      :imageSrc="getImagePath"
      @close="closeModal"
    >
    </image-modal>
  </div>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap.vue";
import ImageModal from "@/components/attraction/ImageModal.vue";
import AttractionWeather from "@/components/attraction/AttractionWeather.vue";
import AttractionNews from "@/components/attraction/AttractionNews.vue";
import { mapActions, mapState, mapMutations } from "vuex";

export default {
  name: "AttractionDetail",
  components: {
    KakaoMap,
    ImageModal,
    AttractionWeather,
    AttractionNews,
  },
  data() {
    return {
      isModalOpen: false,
    };
  },
  computed: {
    ...mapState("attractionStore", ["attractionDetail"]),
    isOpen() {
      return this.isModalOpen;
    },
    getImagePath() {
      if (!this.attractionDetail) return;
      if (this.attractionDetail.firstImage == "") {
        return require("@/assets/no-image.png");
      } else {
        return this.attractionDetail.firstImage;
      }
    },
  },
  methods: {
    ...mapActions("attractionStore", ["getAttractionByContentId"]),
    ...mapMutations("mapStore", [
      "CLEAR_STATE",
      "ATTRACTION_DETAIL_SET_MAP_STATE",
    ]),
    closeModal() {
      this.isModalOpen = false;
    },
    openModal() {
      this.isModalOpen = true;
    },
  },
  async created() {
    this.CLEAR_STATE();
    await this.getAttractionByContentId(this.$route.params.id);
    this.ATTRACTION_DETAIL_SET_MAP_STATE(this.attractionDetail);
  },
};
</script>

<style scoped>
.image {
  height: 400px;
  width: 100%;
  object-fit: cover;
  display: block;
}
/* �긽�떒 諛곕꼫 - end */

/* Map & Description - start */
.map-container {
  margin-top: 50px;
  display: flex;
  justify-content: space-between;
}

.map {
  width: 600px;
  height: 500px;

  margin-right: 40px;
}

.description {
  width: 100%;
}

#description-title {
  margin-bottom: 30px;
  font-size: 2em;
}

#description-subtext {
  padding-right: 10px;
  line-height: 24px;
}
/* Map & Description - end */

/* 하단 이미지 - start */
.detail-section {
  margin: 20px 0 70px 0;
}

.deatil-head {
  margin-bottom: 15px;
}

.addr {
  margin-bottom: 10px;
}

.modal {
  /* margin: 0 auto; */
  position: fixed;
  top: 0;
  left: 33%;
  width: 600px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
