<template>
  <div class="kakao-map">
    <div class="map" ref="mapContainer"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "KakaoMap",
  data() {
    return {};
  },
  methods: {
    resetMap() {
      if (!this.mapLocation) return;
      // 맵이 생성되었을 때 초기설정을 여기서 해야 함
      var container = this.$refs.mapContainer; //지도를 담을 영역의 DOM 레퍼런스
      container.style.width = this.mapSize.width;
      container.style.height = this.mapSize.height;

      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new window.kakao.maps.LatLng(
          this.mapLocation.latitude,
          this.mapLocation.longitude
        ), //지도의 중심좌표.
        level: this.mapSize.level, //지도의 레벨(확대, 축소 정도)
      };
      var map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

      for (let mark of this.mapMarker) {
        var markerPosition = new window.kakao.maps.LatLng(
          mark.latitude,
          mark.longitude
        );
        var marker = new window.kakao.maps.Marker({
          position: markerPosition,
        });
        marker.setMap(map);
      }
    },
  },
  computed: {
    ...mapState("mapStore", ["mapSize", "mapLocation", "mapMarker"]),
  },
  watch: {
    mapSize() {
      this.resetMap();
    },
    mapLocation() {
      this.resetMap();
    },
    mapMarker() {
      this.resetMap();
    },
  },

  mounted() {
    this.resetMap();
  },
};
</script>

<style scoped>
.mapContainer {
  backface-visibility: hidden;
}
</style>