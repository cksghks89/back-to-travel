import http from "@/api/http";

const homeStore = {
  namespaced: true,

  state: {
    popularList: [],
  },

  getters: {
    popularList(state) {
      return state.popularList;
    },
  },

  mutations: {
    SET_POPULAR_LIST(state, popularList) {
      popularList = popularList.map((item) => {
        if (!item.firstImage) {
          return {
            ...item,
            firstImage: require("@/assets/no-image.png"),
          };
        }
        return item;
      });
      state.popularList = popularList;
    },
  },

  actions: {
    async getPopularList({ commit }, typeId) {
      await http.get(`/attraction/popular/${typeId}`).then((response) => {
        commit("SET_POPULAR_LIST", response.data);
      });
    },
  },
};

export default homeStore;
