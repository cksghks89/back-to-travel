<template>
  <div class="container">
    <h1>날씨 정보</h1>
    <div class="weather-container">
      <div v-for="(icon, idx) in icons" :key="idx" class="weather">
        <span class="weather-date">{{ formatDate(date[idx]) }}</span>
        <img :src="icon" alt="" class="weather-icon" />
        <span class="weather-temp"
          >{{ temp_min[idx] }}°C ~ {{ temp_max[idx] }}°C</span
        >
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "AttractionWeather",

  data() {
    return {
      date: "",
      icons: [],
      temp_min: "",
      temp_max: "",
    };
  },

  methods: {
    ...mapActions("attractionStore", ["getWeatherInfo"]),
    formatDate(date) {
      let dayOfWeek = ["일", "월", "화", "수", "목", "금", "토"];
      let dateObj = new Date(date);
      let formattedDate = `${
        dateObj.getMonth() + 1
      }월 ${dateObj.getDate()}일 (${dayOfWeek[dateObj.getDay()]}요일)`;
      return formattedDate;
    },
  },

  computed: {
    ...mapGetters("attractionStore", ["getWeather", "getAttractionDetail"]),
  },

  created() {
    this.getWeatherInfo({
      lat: this.getAttractionDetail.latitude,
      lon: this.getAttractionDetail.longitude,
    });

    this.weather = this.getWeather;

    this.icons = this.weather.map((data) => {
      return `https://openweathermap.org/img/w/${data.weather[0].icon}.png`;
    });

    this.date = this.weather.map((data) => {
      return data.dt_txt;
    });

    this.temp_min = this.weather.map((data) => {
      return data.main.temp_min;
    });

    this.temp_max = this.weather.map((data) => {
      return data.main.temp_max;
    });
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  margin-top: 10px;
  margin-bottom: 20px;
  padding: 10px;
}

.weather-container {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 100%;
  border-radius: 5px;
}

.weather {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 20%;
  height: 100%;
  margin: 0px 15px;
  padding: 0;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  border-radius: 5px;
}

.weather-date {
  font-size: 16px;
  font-weight: bold;
  color: #333333;
  margin-bottom: 5px;
}

.weather-icon {
  width: 50px;
  height: 50px;
}

.weather-temp {
  font-size: 14px;
  color: #333333;
  margin-top: 5px;
}

h1 {
  font-size: 32px;
  font-weight: bold;
  color: #333333;
  margin-bottom: 10px;
  margin-bottom: 30px;
}
</style>
