import axios from "axios";
export default axios.create({
  baseURL: process.env.VUE_APP_AWS_API_URL,
  // baseURL: provess.env.VUE_APP_LOCAL_API_URL,
  withCredentials: false,
  headers: {
    "Content-Type": "application/json",
  },
});
