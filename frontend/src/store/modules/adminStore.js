import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const adminStore = {
  namespaced: true,

  state: {
    userLog: [],
    userList: [],
  },

  getters: {},

  mutations: {
    SET_USER_LOG(state, data) {
      state.userLog = data;
    },
    SET_USER_LIST(state, data) {
      state.userList = data;
    },
  },

  actions: {
    async getUserLog({ commit }) {
      await tokenHttp.get("/admin/log").then((response) => {
        commit("SET_USER_LOG", response.data);
      });
    },
    async getUserList({ commit }) {
      await tokenHttp.get("/admin/userlist").then((response) => {
        commit("SET_USER_LIST", response.data);
      });
    },
    async removeUser({ commit }, user) {
      await tokenHttp
        .delete(`/admin/remove/${user.userId}`)
        .then(() => {
          alert(user.userId + "님이 제거되었습니다.");
        })
        .catch(() => {
          alert(user.userId + "님 제거에 실패하였습니다.");
        });
    },
  },
};

export default adminStore;
