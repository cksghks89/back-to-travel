import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const planStore = {
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
    searchList: [],
    planList: [],
    plans: [],
  },

  getters: {
    getPlanList(state) {
      return state.planList;
    },
    getSearchList(state) {
      return state.searchList;
    },
    getPlans(state) {
      return state.plans;
    },
    getPlansOrderCnt(state) {
      let result = state.plans;
      result = result.sort((a, b) => b.wishCnt - a.wishCnt);
      result = result.splice(0, 3);
      return result;
    },
  },
  mutations: {
    CLEAR_STATE(state) {
      state.mapSize = {
        width: "750px",
        height: "730px",
      };
      state.mapLocation = null;
      state.mapMarker = null;
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
      state.searchList = null;
      state.planList = null;
    },
    CLEAR_PLAN_LIST(state) {
      state.planList = null;
    },
    SET_SEARCH_LIST(state, data) {
      state.searchList = data;
      state.mapMarker = data;
      state.mapLocation = {
        latitude: data[0].latitude,
        longitude: data[0].longitude,
      };
    },
    SET_LOCATION_SEARCH_LIST(state, data) {
      state.searchList = data.data;
      state.mapMarker = data.data;
      state.mapLocation = {
        latitude: data.location.lat,
        longitude: data.location.lng,
      };
    },
    ADD_PLAN(state, item) {
      if (state.planList == null) state.planList = [item];
      else state.planList.push(item);
    },
    DELETE_PLAN(state, index) {
      state.planList.splice(index, 1);
    },
    SET_PLANS(state, data) {
      state.plans = data;
    },
  },
  actions: {
    async querySearch({ commit }, search) {
      await http
        .get(
          `/attraction/query-search?sidoCode=${search.sidoCode}&gugunCode=${search.gugunCode}&contentTypeId=${search.contentTypeId}&query=${search.query}`
        )
        .then((response) => {
          commit("SET_SEARCH_LIST", response.data);
        })
        .catch(() => {
          console.log("query-search 실패");
        });
    },
    async locationSearch({ commit }, location) {
      await http
        .get(`/attraction/location-search?lat=${location.lat}&lng=${location.lng}`)
        .then((response) => {
          commit("SET_LOCATION_SEARCH_LIST", { data: response.data, location });
        })
        .catch(() => {
          console.log("location-search 실패");
        });
    },
    async savePlan({ commit }, { title, plan }) {
      return await tokenHttp.post("/attraction/makePlan", {
        title,
        attractionSequence: plan,
      });
    },

    async getPlans({ commit }) {
      await http.get("/attraction/plan/list").then((response) => {
        commit("SET_PLANS", response.data);
      });
    },

    async getPlanListByUserId({ commit }) {
      await tokenHttp.get(`/attraction/plan/mylist`).then((response) => {
        commit("SET_PLANS", response.data);
      });
    },

    async getPlanListByPlanId({ commit }, planId) {
      await http.get(`/attraction/plan/${planId}`).then((response) => {
        commit("SET_PLANS", response.data);
      });
    },

    async getPlanWishCount({ commit }, planId) {
      return await http.get(`/attraction/plan/wish/${planId}`);
    },
  },
};

export default planStore;
