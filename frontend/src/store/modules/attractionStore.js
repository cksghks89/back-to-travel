import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";
import axios from "axios";

const attractionStore = {
  namespaced: true,

  state: {
    attractionList: [],
    wishList: [],
    attractionDetail: null,
    pageNo: 1,
    type: 0,
    lisSize: 12,
    pageResult: null,
    weather: null,
    newInfo: null,
  },
  getters: {
    getWeather(state) {
      return state.weather;
    },
    getAttractionDetail(state) {
      return state.attractionDetail;
    },
    getNewsInfo(state) {
      return state.newInfo;
    },
    attractionWishList(state) {
      return state.wishList;
    },
  },

  mutations: {
    SET_ATTRACTION(state, { data, page }) {
      state.attractionList = data.attractionList;
      state.listSize = data.pageResult.listSize;
      state.pageResult = data.pageResult;
      state.type = page.type;
    },
    ADD_ATTRACTION(state, { data, page }) {
      for (let item of data.attractionList) {
        state.attractionList.push(item);
      }
      state.attractionList = [...new Set(state.attractionList)];
      state.listSize = data.pageResult.listSize;
      state.pageResult = data.pageResult;
      state.type = page.type;
    },
    SET_TYPE(state, type) {
      state.type = type;
    },

    SET_DETAIL(state, data) {
      state.attractionDetail = data;
    },
    SET_WISHLIST(state, data) {
      state.wishList = data;
    },
    SET_WEATHER(state, data) {
      state.weather = data;
    },
    SET_NEWSINFO(state, data) {
      state.newInfo = data;
    },
  },

  actions: {
    async getAttractions({ commit }, page) {
      await http
        .get(
          `/attraction/list?pageNo=${page.pageNo}&type=${page.type}&listSize=${page.listSize}&query=${page.query}`
        )
        .then((response) => {
          commit("SET_ATTRACTION", { data: response.data, page });
        });
    },

    async getAddAttraction({ commit }, page) {
      return await http
        .get(
          `/attraction/list?pageNo=${page.pageNo}&type=${page.type}&listSize=${page.listSize}&query=${page.query}`
        )
        .then((response) => {
          commit("ADD_ATTRACTION", { data: response.data, page });
        });
    },

    async isThisLike({ commit }, contentId) {
      return await tokenHttp.get(`/attraction/like?contentId=${contentId}`);
    },

    async doLike({ commit }, contentId) {
      return await tokenHttp.post(`/attraction/like?contentId=${contentId}`);
    },

    async getAttractionByContentId({ commit }, contentId) {
      await http
        .get(`/attraction/detail/${contentId}`)
        .then((response) => {
          commit("SET_DETAIL", response.data);
        })
        .catch((error) => {
          console.log("getAttractionByContentId FAILED");
        });
    },
    async getWishList({ commit }, userId) {
      await tokenHttp.get(`/attraction/wish`).then((response) => {
        commit("SET_WISHLIST", response.data);
      });
    },

    async getWeatherInfo({ commit }, { lat, lon }) {
      await axios
        .get(
          `https://api.openweathermap.org/data/2.5/forecast?units=metric&lat=${lat}&lon=${lon}&appid=${process.env.VUE_APP_OPEN_WEATHER_MAP_KEY}`
        )
        .then((response) => {
          return response.data;
        })
        .then((data) => {
          return data["list"].filter((item) => {
            return item["dt_txt"].includes("15:00:00");
          });
        })
        .then((item) => {
          commit("SET_WEATHER", item);
        })
        .catch((error) => {
          console.log("getWeather FAILED");
        });
    },
    async getNews({ commit }, { query }) {
      await http
        .get(`api/news/${query}`)
        .then((response) => {
          commit("SET_NEWSINFO", response.data);
          return response.data;
        })
        .catch((error) => {
          console.log("getNews FAILED");
        });
    },
    async getHeart({ commit }, contentId) {
      await http
        .get(`/attraction/heart/${contentId}`)
        .then((response) => {
          return response.data;
        })
        .catch((error) => {
          console.log("getHeart FAILED");
        });
    },
  },
};

export default attractionStore;
