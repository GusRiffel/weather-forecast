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

export interface UserToken {
  access_token: string;
  refresh_token: string;
  username: string;
}

export interface LoginFormValues {
  username: string;
  password: string;
}

export interface RegisterFormValues {
  username: string;
  email: string;
  password: string;
}

export interface FavoriteCity {
  id: string;
  city: string;
  username: string;
}

export enum WeatherCondition {
  THUNDERSTORM = "Thunderstorm",
  DRIZZLE = "Drizzle",
  RAIN = "Rain",
  SNOW = "Snow",
  HAZE = "Haze",
  MIST = "Mist",
  FOG = "Fog",
  CLEAR = "Clear",
  CLOUDS = "Clouds",
}
