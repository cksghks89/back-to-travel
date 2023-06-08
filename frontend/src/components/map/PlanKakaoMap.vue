<template>
  <div class="kakao-map" ref="kakaoMap">
    <div class="map" ref="mapContainer"></div>
    <div class="current-search" @click="searchCurrentLocation">
      <button class="search-btn">
        <img
          style="width: 15px; height: 15px"
          src="@/assets/rotate.png"
          alt=""
        />
        현 지도에서 검색
      </button>
    </div>
  </div>
</template>

<script
  type="text/javascript"
  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<%= VUE_APP_KAKAO_MAP_KEY %>"
></script>
<script>
import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
import InfowindowItem from "@/components/map/InfowindowItem.vue";
import Vue from "vue";

export default {
  name: "PlanKakaoMap",
  data() {
    return {
      curMap: null,
      curMarker: [],
      curMarkerAttraction: [],
      curPlanMarker: [],
      curPolyline: null,
      curInfowindow: [],
    };
  },
  methods: {
    ...mapActions("planStore", ["locationSearch"]),
    ...mapMutations("planStore", ["ADD_PLAN"]),
    searchCurrentLocation() {
      let center = this.curMap.getCenter();
      let lat = center.getLat();
      let lng = center.getLng();
      this.locationSearch({ lat, lng });
    },
    initMap() {
      var container = this.$refs.mapContainer; //지도를 담을 영역의 DOM 레퍼런스
      container.style.width = this.mapSize.width;
      container.style.height = this.mapSize.height;

      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(
          this.mapLocation.latitude,
          this.mapLocation.longitude
        ), //지도의 중심좌표.
        level: this.mapSize.level, //지도의 레벨(확대, 축소 정도)
      };
      var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
      this.curMap = map;
    },
    setSize() {
      var container = this.$refs.mapContainer; //지도를 담을 영역의 DOM 레퍼런스
      container.style.width = this.mapSize.width;
      container.style.height = this.mapSize.height;
    },
    setCenterLocation() {
      var moveLatLon = new kakao.maps.LatLng(
        this.mapLocation.latitude,
        this.mapLocation.longitude
      );
      this.curMap.setCenter(moveLatLon);
    },
    removeMarker() {
      for (let m of this.curMarker) {
        m.setMap(null);
      }
      for (let m of this.curPlanMarker) {
        m.setMap(null);
      }
      for (let m of this.curInfowindow) {
        m.close();
      }
      if (this.curPolyline) {
        this.curPolyline.setMap(null);
      }
      this.curMarker = [];
      this.curMarkerAttraction = [];
      this.curPlanMarker = [];
    },
    setMarker() {
      this.curMarker = [];
      this.curMarkerAttraction = [];
      // marker 등록 -- start
      for (let mark of this.mapMarker) {
        var imageSrc = require(`@/assets/marker/${mark.contentTypeId}.png`); // 마커이미지의 주소입니다
        var imageSize = new kakao.maps.Size(35, 35); // 마커이미지의 크기입니다
        // var imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize
          // imageOption
        );
        var markerPosition = new kakao.maps.LatLng(
          mark.latitude,
          mark.longitude
        ); // 마커가 표시될 위치입니다

        var marker = new kakao.maps.Marker({
          position: markerPosition,
          image: markerImage, // 마커이미지 설정
        });
        marker.setZIndex(-1);
        marker.setMap(this.curMap);

        this.curMarker.push(marker);
        this.curMarkerAttraction.push(mark);
      }
      this.setPlanMarker();
      this.setInfowindow();
    },
    setInfowindow() {
      for (let i = 0; i < this.curMarker.length; i++) {
        var infowindow = new window.kakao.maps.InfoWindow({
          content: new Vue({
            render: (h) =>
              h(InfowindowItem, {
                props: {
                  src: this.curMarkerAttraction[i].firstImage,
                  title: this.curMarkerAttraction[i].title,
                  addr1: this.curMarkerAttraction[i].addr1,
                },
              }),
          }).$mount().$el,
        });
        this.curInfowindow.push(infowindow);

        kakao.maps.event.addListener(this.curMarker[i], "click", () => {
          this.ADD_PLAN(this.curMarkerAttraction[i]);
          console.log(this.curInfowindow[i]);
          this.curInfowindow[i].close();
        });
        kakao.maps.event.addListener(
          this.curMarker[i],
          "mouseover",
          this.makeOverListener(this.curMap, this.curMarker[i], infowindow)
        );
        kakao.maps.event.addListener(
          this.curMarker[i],
          "mouseout",
          this.makeOutListener(infowindow)
        );
      }
    },
    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },
    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
    },

    setPlanMarker() {
      if (!this.planList) return;
      let path = [];
      // marker 등록 -- start
      for (let mark of this.planList) {
        var imageSrc = require(`@/assets/marker/selectMarker.png`); // 마커이미지의 주소입니다
        var imageSize = new kakao.maps.Size(37, 37); // 마커이미지의 크기입니다
        // var imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize
          // imageOption
        );
        var markerPosition = new kakao.maps.LatLng(
          mark.latitude,
          mark.longitude
        ); // 마커가 표시될 위치입니다
        path.push(markerPosition);

        var marker = new kakao.maps.Marker({
          position: markerPosition,
          image: markerImage, // 마커이미지 설정
        });
        marker.setMap(this.curMap);

        this.curPlanMarker.push(marker);
      }
      var polyline = new kakao.maps.Polyline({
        map: this.curMap,
        path: path,
        strokeWeight: 8,
        strokeColor: "#ff66f7",
        strokeOpacity: 0.8,
        // strokeStyle: "dashed",
      });
      polyline.setZIndex(55);

      this.curPolyline = polyline;
      polyline.setMap(this.curMap);
    },
  },
  computed: {
    ...mapState("planStore", [
      "mapSize",
      "mapLocation",
      "mapMarker",
      "searchList",
      "planList",
    ]),
    ...mapGetters("planStore", ["getPlanList", "getSearchList"]),
  },
  watch: {
    mapSize() {
      this.setSize();
    },
    mapLocation() {
      this.setCenterLocation();
    },
    mapMarker() {
      this.removeMarker();
      this.setMarker();
      this.setInfowindow();
    },
    planList() {
      this.removeMarker();
      this.setMarker();
    },
  },

  mounted() {
    // this.resetMap();
    this.initMap();
  },
};
</script>

<style scoped>
.mapContainer {
  backface-visibility: hidden;
}

.kakao-map {
  position: relative;
}

.current-search {
  position: absolute;
  top: 20px;
  left: 300px;
  z-index: 1;
}

.search-btn {
  border-radius: 20px;
  border: 1px solid rgb(167, 164, 164);
}
</style>
