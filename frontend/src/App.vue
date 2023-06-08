<template>
  <div id="app">
    <the-header></the-header>
    <div class="main">
      <router-view />
    </div>
    <the-footer></the-footer>
    <the-chatting class="chatting"></the-chatting>
  </div>
</template>
<script>
import TheHeader from "@/components/layout/TheHeader.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
import TheChatting from "./components/layout/TheChatting.vue";

export default {
  components: {
    TheHeader,
    TheFooter,
    TheChatting,
  },
};
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* width: 1200px;
  margin: 0 auto; */
  position: relative;
}

#app::-webkit-scrollbar {
  width: 8px;
}
#app::-webkit-scrollbar-thumb {
  background-color: #e5e5e5;
  border-radius: 10px;
}
.main {
  min-height: 750px;
  width: 1200px;
  margin: 0 auto;
  box-sizing: border-box;
}
.main {
  min-height: 750px;
}

@font-face {
  font-family: "TheJamsil5Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2")
    format("woff2");
  font-weight: 500;
  font-style: normal;
}

.chatting {
  position: fixed;
  bottom: 100px;
  right: 20px;
}

</style>
