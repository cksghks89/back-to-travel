import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const mapStore = {
  namespaced: true,

  state: {
    mapSize: {
      width: "600px",
      height: "500px",
      level: 7,
    },
    mapLocation: {
      latitude: "",
      longitude: "",
    },
    mapMarker: [
      {
        latitude: "",
        longitude: "",
      },
    ],
  },

  getters: {},
  mutations: {
    CLEAR_STATE(state) {
      state.mapSize = {
        width: "600px",
        height: "500px",
      };
      state.mapLocation = null;
      state.mapMarker = null;
    },
    ATTRACTION_DETAIL_SET_MAP_STATE(state, info) {
      state.mapSize = {
        width: "600px",
        height: "500px",
        level: 7,
      };

      state.mapLocation = {
        latitude: info.latitude,
        longitude: info.longitude,
      };

      state.mapMarker = [
        { latitude: info.latitude, longitude: info.longitude },
      ];
    },
    PLAN_INIT(state) {
      state.mapSize = {
        width: "750px",
        height: "730px",
        level: 7,
      };
      state.mapLocation = {
        latitude: "36.355622",
        longitude: "127.298612",
      };
      state.mapMarker = [
        {
          latitude: "36.355622",
          longitude: "127.298612",
        },
      ];
    },
  },
  actions: {},
};

export default mapStore;
