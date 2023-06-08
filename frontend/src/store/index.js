import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import noticeStore from "@/store/modules/noticeStore";
import homeStore from "@/store/modules/homeStore";
import userStore from "@/store/modules/userStore";
import boardStore from "@/store/modules/boardStore";
import attractionStore from "./modules/attractionStore";
import mapStore from "@/store/modules/mapStore";
import planStore from "./modules/planStore";
import socketStore from "./modules/socketStore";
import planDetailStore from "./modules/planDetailStore";
import myPlanStore from "./modules/myPlanStore";
import adminStore from "./modules/adminStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    noticeStore,
    homeStore,
    userStore,
    boardStore,
    attractionStore,
    mapStore,
    planStore,
    socketStore,
    planDetailStore,
    myPlanStore,
    adminStore,
  },

  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});
