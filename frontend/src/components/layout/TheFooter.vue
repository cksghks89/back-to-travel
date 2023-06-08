<template>
  <div class="footer-container">
    <div class="footer">
      <div class="logo-container">
        <img
          class="logo"
          src="@/assets/chatting_logo.png"
          alt="홈페이지 로고"
          style="width: 150px;"
        />
      </div>
      <div class="footer-content">
        <div>
          <p>이용약관 | 고객문의 | 개인정보처리방침</p>
        </div>
        <div>
          <p>{{ address }}</p>
          <p>{{ email }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TheFooter",
  data() {
    return {
      address: `SSAFY - 대전광역시 유성구 덕명동 124, 6반 202호`,
      email: `E-Mail : song960705@naver.com`,
      copyRight: `COPYRIGHT `,
    };
  },
};
</script>

<style scoped>
.footer-container {
  margin-top: 40px;
  background-color: #f8f8f8;
}
.footer {
  display: flex;
  justify-content: space-between;
  list-style: none !important;
  width: 1200px;
  margin: 0 auto;
}

.logo-container {
  margin: 30px;
}

.footer-content {
  margin: 30px;
}

.logo {
  width: 300px;
}
</style>
