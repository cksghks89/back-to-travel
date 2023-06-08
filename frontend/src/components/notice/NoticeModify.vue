<template>
  <div class="create-post">
    <h2>게시판 수정</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="post-title">제목</label>
        <input type="text" id="post-title" v-model="title" required />
      </div>
      <div class="form-group">
        <label for="post-content">내용</label>
        <quill-editor
          id="post-content"
          v-model="content"
          :options="editorOptions"
          @blur="onBlur($event)"
          @focus="onFocus($event)"
          @ready="onEditorReady($event)"
        ></quill-editor>
      </div>
      <div class="form-group">
        <label for="post-file">파일 첨부</label>
        <div
          class="dropzone"
          @drop.prevent="handleFileUpload"
          @dragover.prevent
          @click="onClickDropzone"
        >
          <input
            type="file"
            id="post-file"
            ref="fileInput"
            @change="handleFileUpload"
            style="display: none"
          />
          <p v-if="!selectedFile">
            여기에 파일을 드래그 앤 드롭하거나 클릭하세요.
          </p>
          <p v-else>{{ selectedFile.name }}</p>
        </div>
      </div>
      <div class="button-group">
        <button type="button" @click="goBack">수정 취소</button>
        <button type="submit" @click="submitForm">수정</button>
      </div>
    </form>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

export default {
  name: "NoticeWrite",
  components: {
    quillEditor,
  },
  data() {
    return {
      title: "",
      content: "",
      selectedFile: null,
      userId: null,
      editorOptions: {
        theme: "snow",
      },
    };
  },
  methods: {
    ...mapActions("noticeStore", ["modifyNotice", "fetchNotices"]),
    submitForm() {
      const notice = {
        title: this.title,
        content: this.content,
        userId: this.userId,
      };
      if (confirm("수정하시겠습니까?")) {
        this.modifyNotice({ noticeId: this.getNoticeDetail.noticeId, notice });
        this.$router.push({
          path: `/notice/detail/${this.getNoticeDetail.noticeId}`,
        });
      }
    },

    handleFileUpload(event) {
      this.selectedFile = event.dataTransfer
        ? event.dataTransfer.files[0]
        : event.target.files[0];
    },
    onClickDropzone() {
      this.$refs.fileInput.click();
    },
    goBack() {
      this.$router.go(-1);
    },
    onBlur(quill) {
      console.log("Quill editor blur!", quill);
    },
    onFocus(quill) {
      console.log("Quill editor focus!", quill);
    },
    onEditorReady(quill) {
      console.log("Quill is ready!", quill);
    },
  },
  computed: {
    ...mapGetters("userStore", ["getUserId"]),
    ...mapGetters("noticeStore", ["getNoticeDetail"]),
  },
  created() {
    if (!this.getUserId) {
      this.$router.push({ path: "/noticeboard/notice" });
      return;
    }
    this.title = this.getNoticeDetail.title;
    this.content = this.getNoticeDetail.content;
    this.userId = this.getUserId;
  },
};
</script>

<style scoped>
.create-post {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 5px #ccc;
  margin-top: 30px;
}
.form-group {
  margin-bottom: 20px;
}
label {
  display: block;
  margin-bottom: 5px;
  margin-top: 20px;
}
input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-top: 10px;
}
#post-title {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  height: 40px;
}
button-group {
  display: flex;
  justify-content: space-between;
}
button {
  padding: 5px 20px;
  margin: 15px 0;
  border-radius: 5px;
  cursor: pointer;
  height: 40px;
}
button[type="submit"] {
  background-color: #caa8fe;
  color: white;
  border: none;
}
button[type="submit"]:hover {
  background-color: #af5cd4;
}
button[type="button"] {
  background-color: #f1f1f1;
  color: #333;
  border: 1px solid #ccc;
}
button[type="button"]:hover {
  background-color: #ddd;
}
.dropzone {
  width: 100%;
  height: 100px;
  padding: 10px;
  border: 2px dashed #ccc;
  background-color: #faf9fe;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 4px;
  margin-top: 10px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.dropzone:hover {
  background-color: #edecf9;
}
</style>
