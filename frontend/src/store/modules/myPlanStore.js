import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const myPlanStore = {
  namespaced: true,

  state: {
    plans: [],
  },

  getters: {},
  mutations: {
    SET_PLANS(state, data) {
      state.plans = data;
    },
  },
  actions: {
    async getMyPlans({ commit }) {
      await tokenHttp.get("/attraction/plan/mylist").then((response) => {
        console.log(response.data);
        commit("SET_PLANS", response.data);
      });
    },
  },
};

export default myPlanStore;
