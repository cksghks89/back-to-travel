import http from "@/api/http";
import jwt_decode from "jwt-decode";
import tokenHttp from "@/api/tokenHttp";

const userStore = {
  namespaced: true,

  state: {
    isLogin: false,
    userId: null,
    userProfile: null,
    userInfo: null,
    isDupliate: false,
  },

  getters: {
    getIsLogin(state) {
      return state.isLogin;
    },
    getUserId(state) {
      return state.userId;
    },
    getUserInfo(state) {
      return state.userInfo;
    },
    getIsDuplicate(state) {
      return state.isDupliate;
    },
  },

  mutations: {
    LOGIN(state, data) {
      const user = jwt_decode(data["access-token"]);

      state.isLogin = true;
      state.userId = user.userId;
      state.userProfile = data.userProfile;

      sessionStorage.setItem("access-token", data["access-token"]);
      sessionStorage.setItem("refresh-token", data["refresh-token"]);
    },

    LOGOUT(state) {
      state.isLogin = false;
      state.userId = null;
      state.userProfile = null;
      state.userInfo = null;

      sessionStorage.removeItem("access-token");
      sessionStorage.removeItem("refresh-token");
    },
    SET_USER_INFO(state, data) {
      state.userInfo = data;
    },
    SET_DUPLICATE(state, data) {
      state.isDupliate = data;
    },
  },

  actions: {
    async login({ commit }, user) {
      await http
        .post("/user/login", user)
        .then((response) => {
          if (response.data.message === "success") {
            commit("LOGIN", response.data);
          }
        })
        .catch(() => {
          throw new Error("fail");
        });
    },

    async findPassword({ commit }, user) {
      await http.post("/user/findPassword", user).catch((error) => {
        throw new Error("fail");
      });
    },

    async signup({ commit }, user) {
      await http
        .post("/user/signup", user)
        .then((response) => {})
        .catch(() => {
          throw new Error("fail");
        });
    },
    async fetchUserInfo({ commit }, userId) {
      await tokenHttp
        .get(`/user/${userId}`)
        .then((response) => {
          commit("SET_USER_INFO", response.data.data);
        })
        .catch((error) => {
          throw new Error("fail");
        });
    },

    async getRequiredNaverLoginInfo({ commit }) {
      return await http.get("/oauth/info/naver").then((response) => {
        return response.data;
      });
    },

    async sendNaverCode({ commit }, code) {
      await http.post(`/oauth/code/naver?code=${code}`).then((response) => {
        commit("LOGIN", response.data);
      });
    },
    async deleteAccount({ commit }) {
      await tokenHttp.delete(`/user/delete`).then(() => {
        commit("LOGOUT");
      });
    },

    async updateAccount({ commit }, user) {
      await tokenHttp.put(`/user/update`, user);
    },

    async checkNickName({ commit }, nickname) {
      await tokenHttp
        .get(`/user/nickname/${nickname}`)
        .then(() => {
          commit("SET_DUPLICATE", true);
        })
        .catch(() => {
          commit("SET_DUPLICATE", false);
        });
    },
  },
};

export default userStore;
