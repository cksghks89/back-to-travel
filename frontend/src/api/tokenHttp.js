import axios from "axios";
import jwt_decode from "jwt-decode";
import dayjs from "dayjs";

const baseURL = process.env.VUE_APP_AWS_API_URL;
// const baseURL = provess.env.VUE_APP_LOCAL_API_URL;

const tokenHttp = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

tokenHttp.interceptors.request.use(async (req) => {
  const accessToken = sessionStorage.getItem("access-token");
  if (!accessToken) {
    console.log("token 이 존재하지 않습니다.");
    throw new Error("expire token");
  }
  const user = jwt_decode(accessToken);
  const isExpired = dayjs().diff(dayjs.unix(user.exp)) < 1;

  // access token 이 만료되지 않았다면 access-token 을 넣어 요청 실행
  if (isExpired) {
    req.headers["access-token"] = accessToken;
    return req;
  }

  // access token 이 만료되었다면 refresh-token 으로 token 재발급
  console.log("api/tokenHttp.js : access token 만료");
  await axios
    .post(
      `${baseURL}/user/refresh`,
      {},
      {
        headers: {
          "refresh-token": sessionStorage.getItem("refresh-token"),
        },
      }
    )
    .then((response) => {
      if (response.data.message === "success") {
        sessionStorage.setItem("access-token", response.data["access-token"]);
        sessionStorage.setItem("refresh-token", response.data["refresh-token"]);
      } else {
        throw new Error("expire token");
      }
    })
    .catch(() => {
      throw new Error("expire token");
    });

  req.headers["access-token"] = sessionStorage.getItem("access-token");
  return req;
});

export default tokenHttp;
