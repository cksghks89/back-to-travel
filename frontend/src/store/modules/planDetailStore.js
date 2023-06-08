import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const planDetailStore = {
  namespaced: true,

  state: {
    mapSize: {
      width: "900px",
      height: "730px",
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
    searchList: [],
    planList: [],
    plans: [],
  },

  getters: {},
  mutations: {
    PLAN_INIT(state) {
      state.mapSize = {
        width: "900px",
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
      state.searchList = null;
      state.planList = null;
    },
    SET_PLANS(state, data) {
      state.plans = data;
      state.mapLocation = {
        latitude: data.attractionSequence[0].latitude,
        longitude: data.attractionSequence[0].longitude,
      };
    },
  },
  actions: {
    async getPlanListByPlanId({ commit }, planId) {
      await http.get(`/attraction/plan/detail/${planId}`).then((response) => {
        commit("SET_PLANS", response.data);
      });
    },
  },
};

export default planDetailStore;
