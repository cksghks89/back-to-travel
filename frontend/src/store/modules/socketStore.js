import tokenHttp from "@/api/tokenHttp.js";

const socketStore = {
  namespaced: true,

  state: {
    stompClient: null,
    notifyList: [],
  },
  getters: {},
  mutations: {
    SET_STOMP_CLIENT(state, stompClient) {
      state.stompClient = stompClient;
    },
    SET_NOTIFY_LIST(state, data) {
      state.notifyList = data;
    },
    ADD_NOTIFY_LIST(state, item) {
      state.notifyList = [item, ...state.notifyList];
    },
  },
  actions: {
    async getNotification({ commit }) {
      await tokenHttp.get("/notify/list").then((response) => {
        commit("SET_NOTIFY_LIST", response.data);
      });
    },

    async readNotification({ commit }) {
      console.log("read");
      await tokenHttp.put("/notify/read");
    },
  },
};

export default socketStore;
