import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const noticeStore = {
  namespaced: true,

  state: { notices: [], noticeDetail: null, pageResult: null },
  getters: {
    getNotices(state) {
      return state.notices;
    },
    getNoticeDetail(state) {
      return state.noticeDetail;
    },
    getPageResult(state) {
      return state.pageResult;
    },
  },

  mutations: {
    SET_NOTICES(state, notices) {
      state.notices = notices;
    },
    SET_NOTICE_DETAIL(state, noticeDetail) {
      state.noticeDetail = noticeDetail;
    },
    SET_PAGE_NAV(state, page) {
      state.pageResult = page;
    },
  },

  actions: {
    async fetchNotices({ commit }, { pageNo, query, listSize }) {
      await http
        .get(
          `/notice/list?pageNo=${pageNo}&listSize=${listSize}&query=${query}`
        )
        .then((response) => {
          commit("SET_NOTICES", response.data.noticeList);
          commit("SET_PAGE_NAV", response.data.pageResult);
        });
    },

    async deleteNotice(_, noticeId) {
      await tokenHttp.delete(`/notice/delete/${noticeId}`);
    },

    async modifyNotice(_, { noticeId, notice }) {
      await tokenHttp.put(`/notice/modify/${noticeId}`, notice);
    },

    async fetchNoticeDetail({ commit }, noticeId) {
      const response = await http.get(`/notice/detail/${noticeId}`);
      commit("SET_NOTICE_DETAIL", response.data);
    },

    async writeNotice(_, notice) {
      await tokenHttp.post("/notice/write", notice);
    },
  },
};

export default noticeStore;
