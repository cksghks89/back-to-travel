import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import jwt_decode from "jwt-decode";
import dayjs from "dayjs";

import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  let token = sessionStorage.getItem("refresh-token");
  if (!token) {
    alert("로그인이 필요한 페이지입니다.");
    store.commit("userStore/LOGOUT");
    router.push("/user/login");
    return;
  }

  const user = jwt_decode(token);
  const isExpired = dayjs().diff(dayjs.unix(user.exp)) < 1;

  if (isExpired) {
    next();
  } else {
    alert("로그인이 필요한 페이지입니다.");
    store.commit("userStore/LOGOUT");
    router.push("/user/login");
    return;
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/chat",
    name: "chat",
    component: () => import("@/views/ChatView.vue"),
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/UserView.vue"),
    children: [
      {
        path: "login",
        name: "userlogin",
        component: () => import("@/components/user/LoginForm.vue"),
      },
      {
        path: "signup",
        name: "usersignup",
        component: () => import("@/components/user/SignupForm.vue"),
      },
      {
        path: "findpassword",
        name: "userfindpassword",
        component: () => import("@/components/user/FindPassword.vue"),
      },
      {
        path: "navercallback",
        name: "usernavercallback",
        component: () => import("@/components/user/NaverLoginCallback.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
    children: [
      {
        path: "list",
        name: "boardlist",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "boardwrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardWrite.vue"),
      },
      {
        path: "detail/:id",
        name: "boarddetail",
        component: () => import("@/components/board/BoardDetail.vue"),
        props: true,
      },
      {
        path: "modify/:id",
        name: "boardmodify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify.vue"),
        props: true,
      },
    ],
  },
  {
    path: "/notice",
    name: "notice",
    component: () => import("@/views/NoticeView.vue"),
    children: [
      {
        path: "list",
        name: "noticelist",
        component: () => import("@/components/notice/NoticeBoard.vue"),
      },
      {
        path: "write",
        name: "noticewrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeWrite.vue"),
      },
      {
        path: "detail/:id",
        name: "noticedetail",
        component: () => import("@/components/notice/NoticeDetail.vue"),
        props: true,
      },
      {
        path: "modify/:id",
        name: "noticemodify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeModify.vue"),
        props: true,
      },
    ],
  },
  {
    path: "/profile",
    name: "profile",
    component: () => import("@/views/ProfileView.vue"),
    redirect: "/profile/personal",
    children: [
      {
        path: "main",
        name: "profilemain",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/profile/MainProfile.vue"),
      },
      {
        path: "personal",
        name: "profilepersonal",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/profile/PersonalProfile.vue"),
      },
      {
        path: "delete",
        name: "profiledelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/profile/DeleteProfile.vue"),
      },
      {
        path: "wish",
        name: "profilewish",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/profile/WishProfile.vue"),
      },
      {
        path: "detail/:id",
        redirect: "/attraction/detail/:id",
      },
      {
        path: "myplans",
        name: "profilemyplans",
        component: () => import("@/components/profile/MyPlansProfile.vue"),
      },
    ],
  },
  {
    path: "/attraction",
    name: "attraction",
    component: () => import("@/views/AttractionView.vue"),
    redirect: "/attraction/list",
    children: [
      {
        path: "list",
        name: "attractionlist",
        component: () => import("@/components/attraction/AttractionList.vue"),
      },
      {
        path: "detail/:id",
        name: "attractiondetail",
        component: () => import("@/components/attraction/AttractionDetail.vue"),
      },
    ],
  },

  {
    path: "/plan",
    name: "plan",
    component: () => import("@/views/PlanView.vue"),
    redirect: "/plan/list",
    children: [
      {
        path: "make",
        name: "planmake",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/plan/PlanMake.vue"),
      },
      {
        path: "list",
        name: "planlist",
        component: () => import("@/components/plan/PlanList.vue"),
      },
      {
        path: "detail/:id",
        name: "plandetail",
        component: () => import("@/components/plan/PlanDetail.vue"),
      },
    ],
  },
  {
    path: "/noticeboard",
    name: "noticeboard",
    redirect: "/noticeboard/notice",
    component: () => import("@/views/NoticeBoardView.vue"),
    children: [
      {
        path: "notice",
        name: "noticeboardnotice",
        component: () => import("@/components/notice/NoticeBoard.vue"),
      },
      {
        path: "board",
        name: "noticeboardboard",
        component: () => import("@/components/board/BoardList.vue"),
      },
    ],
  },
  {
    path: "/admin",
    name: "admin",
    component: () => import("@/views/AdminView.vue"),
    redirect: "/admin/stats",
    children: [
      {
        path: "stats",
        name: "adminstatistics",
        component: () => import("@/components/admin/TheStats.vue"),
      },
      {
        path: "user",
        name: "adminuser",
        component: () => import("@/components/admin/UserControl.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.be;
export default router;
