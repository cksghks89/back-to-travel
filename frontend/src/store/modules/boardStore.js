import http from "@/api/http";
import tokenHttp from "@/api/tokenHttp";

const boardStore = {
  namespaced: true,

  state: { boards: [], boardDetail: null, pageResult: null },
  getters: {
    getBoards(state) {
      return state.boards;
    },
    getBoardDetail(state) {
      return state.boardDetail;
    },
    getPageResult(state) {
      return state.pageResult;
    },
  },

  mutations: {
    SET_BOARDS(state, boards) {
      state.boards = boards;
    },
    SET_BOARD_DETAIL(state, boardDetail) {
      state.boardDetail = boardDetail;
    },
    SET_PAGE_NAV(state, page) {
      state.pageResult = page;
    },
  },

  actions: {
    async fetchBoards({ commit }, { pageNo, query, listSize }) {
      await http
        .get(`/board/list?pageNo=${pageNo}&listSize=${listSize}&query=${query}`)
        .then((response) => {
          commit("SET_BOARDS", response.data.boardList);
          commit("SET_PAGE_NAV", response.data.pageResult);
        });
    },

    async deleteBoard(_, boardId) {
      await tokenHttp.delete(`/board/delete/${boardId}`);
    },

    async modifyBoard({ commit }, { boardId, board }) {
      await tokenHttp.put(`/board/modify/${boardId}`, board);
      commit("SET_BOARD_DETAIL", board);
    },

    async fetchBoardDetail({ commit }, boardId) {
      const response = await http.get(`/board/detail/${boardId}`);
      commit("SET_BOARD_DETAIL", response.data);
    },

    async writeBoard(_, board) {
      await tokenHttp.post("/board/write", board);
    },
    async getUserInfo({ commit }, userId) {
      // let nickName = null;
      return await http.get(`/user/info/${userId}`);
     
    },
  },
};

export default boardStore;
