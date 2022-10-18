export interface CityWeather {
  id: number;
  city: string;
  weatherData: {
    temp: string;
    pressure: string;
    humidity: string;
    temp_min: string;
    temp_max: string;
  };
  weatherCondition: string;
  windSpeed: number;
}

export interface LoginFormValues {
  username: string;
  password: string;
}

export interface SearchWeather {
  city: string;
}

export enum WeatherCondition {
  THUNDERSTORM = "Thunderstorm",
  DRIZZLE = "Drizzle",
  RAIN = "Rain",
  SNOW = "Snow",
  HAZE = "Haze",
  MIST = "Mist",
  CLEAR = "Clear",
  CLOUDS = "Clouds",
}
