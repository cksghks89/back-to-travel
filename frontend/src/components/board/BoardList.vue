<template>
  <div class="container">
    <h1 class="container-title">공유게시판</h1>
    <div class="search">
      <input
        v-model="query"
        @keyup.enter="onSearch"
        type="text"
        placeholder="검색"
      />
      <button @click="onSearch">검색</button>
    </div>
    <table class="boardTable">
      <thead>
        <tr>
          <th>No</th>
          <th>제목</th>
          <th>글쓴이</th>
          <th>작성시간</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(board, index) in getBoards" :key="index">
          <td>{{ board.boardId }}</td>
          <td>
            <router-link
              :to="{
                path: `/board/detail/${board.boardId}`,
              }"
            >
              {{ board.title }}
            </router-link>
            <img class="file-icon" src="@/assets/file.png" alt="file icon" />
          </td>
          <td>{{ board.userId }}</td>
          <td>{{ board.createTime.split(" ")[0] }}</td>
          <td>{{ board.hit }}</td>
        </tr>
      </tbody>
    </table>
    <div class="container-bottom">
      <div class="pagination">
        <button @click="movePage(-1)" :disabled="getPageResult.prev">
          이전
        </button>
        <span
          v-for="page in getPageResult.endPage - getPageResult.beginPage + 1"
          :key="page"
          @click="goToPage(page)"
          :pageNo="page"
          :class="{ active: pageNo == page }"
        >
          {{ page }}
        </span>
        <button @click="movePage(1)" :disabled="getPageResult.next">
          다음
        </button>
      </div>
      <div class="btn-container">
        <router-link to="/board/write">
          <button class="write-button" v-if="this.getUserId">
            <img src="@/assets/writing.png" alt="" />
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      query: "",
      pageNo: 1,
      listSize: 10,
      totalPages: 1,
    };
  },

  watch: {
    query() {
      this.pageNo = 1;
    },
  },
  methods: {
    onSearch() {
      this.fetchBoards({
        pageNo: 1,
        query: this.query,
        listSize: this.listSize,
      });
    },
    ...mapActions("boardStore", ["fetchBoards", "getUserInfo"]),
    movePage(step) {
      this.getPageResult.pageNo += step;
      this.pageNo = this.getPageResult.pageNo;
      this.fetchBoards({
        query: this.query,
        pageNo: this.getPageResult.pageNo,
        listSize: this.listSize,
      });
    },

    goToPage(page) {
      this.getPageResult.pageNo = page;
      this.pageNo = this.getPageResult.pageNo;
      this.fetchBoards({
        query: this.query,
        pageNo: page,
        listSize: this.listSize,
      });
    },

    fetchData() {
      const pageNo = this.$route.query.pageNo;
      const listSize = this.$route.query.listSize;
      const query = this.$route.query.query;

      if (pageNo) this.pageNo = pageNo;
      if (listSize) this.listSize = listSize;
      if (query) this.query = query;

      this.fetchBoards({
        pageNo: this.pageNo,
        size: this.listSize,
        query: this.query,
      });
    },
  },

  computed: {
    ...mapGetters("boardStore", ["getBoards", "getPageResult"]),
    ...mapGetters("userStore", ["getUserId"]),
  },
  created() {
    this.fetchData();
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.fetchData();
    });
  },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  margin-left: 0px;
  margin-right: 0px;
  margin-top: 100px;
}
.container-title {
  margin-bottom: 20px;
  font-size: 35px;
  font-weight: bold;
}
.boardTable {
  width: 100%;
  border-collapse: collapse;
}

.boardTable thead th {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.boardTable tbody td {
  padding: 8px;
  border-bottom: 1px solid #ddd;
}
.boardTable th:nth-child(2),
.boardTable td:nth-child(2) {
  width: 60%;
  position: relative;
}

.boardTable th:not(:nth-child(2)),
.boardTable td:not(:nth-child(2)) {
  width: 10%;
}
.search {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  margin-top: 40px;
  margin-bottom: 40px;
}

.search input {
  width: 200px;
  padding: 4px 8px;
  border: 1px solid #ddd;
  border-radius: 10px 0 0 10px;
}

.search button {
  background-color: #caa8fe;
  color: white;
  border: none;
  padding: 6px 12px;
  cursor: pointer;
  border-radius: 0 10px 10px 0;
}

.search button:hover {
  background-color: #af5cd4;
}

.btn-container button {
  border-radius: 4px;
}

button {
  background-color: #caa8fe;
  color: white;
  border: none;
  padding: 6px 12px;
  cursor: pointer;
}

.pagination span {
  display: inline-block;
  margin: 0 5px;
  cursor: pointer;
}
.pagination button {
  display: inline-block;
  margin: 0 5px;
  cursor: pointer;
  border-radius: 4px;
}
.pagination button:hover {
  background-color: #af5cd4;
}
.container-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 30px;
}
.file-icon {
  display: inline-block;
  margin-left: 5px;
  width: 16px;
  height: 16px;
  vertical-align: middle;
  position: absolute;
  transform: translateY(-50%);
  top: 50%;
  right: 0;
  margin-right: 15px;
}
.active {
  color: blue;
  font-weight: bold;
}
.write-button {
  border: none;
  background: none;
}
.write-button img {
  width: 30px;
  height: 30px;
}
.write-button:hover {
  background-color: white;
}
</style>
