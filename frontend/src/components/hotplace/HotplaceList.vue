<template>
  <div>
    <input type="text" v-model="searchKeyword" />
    <button @click="searchByKeyword">검색</button>
    <div id="map" style="width: 100%; height: 400px"></div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      searchKeyword: "",
      map: null,
      infowindow: null,
    };
  },
  mounted() {
    let container = document.getElementById("map");
    let options = {
      center: new window.kakao.maps.LatLng(37.5666791, 126.9782914),
      level: 3,
    };
    this.map = new window.kakao.maps.Map(container, options);
    this.infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
  },
  methods: {
    searchByKeyword: async function () {
      let keyword = this.searchKeyword;
      let res = await axios.get(
        "https://dapi.kakao.com/v2/local/search/keyword.json",
        {
          params: { query: keyword },
          headers: {
            Authorization: "89447c8a7b531aba1436c8c51c2f4cd1",
          },
        }
      );

      let data = res.data.documents;
      this.displayPlaces(data);
    },
    displayPlaces: function (places) {
      let listEl = document.getElementById("placesList");
      let menuEl = document.getElementById("menu_wrap");
      let fragment = document.createDocumentFragment();
      let bounds = new window.kakao.maps.LatLngBounds();
      let listStr = "";

      places.forEach((place) => {
        let position = new window.kakao.maps.LatLng(place.y, place.x);
        bounds.extend(position);
      });

      this.map.setBounds(bounds);
    },
  },
};
</script>
