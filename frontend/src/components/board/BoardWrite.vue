<template>
  <div class="create-post">
    <h2>게시물 작성</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="post-title" style="">제목</label>
        <input type="text" id="post-title" v-model="title" required />
      </div>
      <div class="form-group">
        <label for="post-content">내용</label>
        <quill-editor
          id="post-content"
          :content="content"
          @change="onEditorChange"
          required
        ></quill-editor>
      </div>
      <div class="form-group file-form">
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
        <button type="button" @click="goBack">목록으로</button>
        <button type="submit" @click="submitForm">등록</button>
      </div>
    </form>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import { quillEditor } from "vue-quill-editor";
export default {
  name: "BoardWrite",
  components: {
    quillEditor,
  },
  data() {
    return {
      title: "",
      content: null,
      selectedFile: null,
      userId: null,
    };
  },

  methods: {
    ...mapActions("boardStore", ["writeBoard", "fetchBoards"]),
    submitForm() {
      const board = {
        title: this.title,
        content: this.content.html,
        userId: this.userId,
      };
      this.writeBoard(board);
      this.$router.push({ name: "noticeboardboard" });
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
    onEditorChange(value) {
      this.content = value;
    },
  },
  computed: {
    ...mapGetters("userStore", ["getUserId"]),
  },
  created() {
    this.userId = this.getUserId;
  },
};
</script>

<style scoped>
.create-post {
  max-width: 1200px;
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
/* input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-top: 10px;
} */
#post-title {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  height: 40px;
}
#post-content {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  height: 300px;
}
.button-group {
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
.file-form {
  margin-top: 80px;
}
</style>
