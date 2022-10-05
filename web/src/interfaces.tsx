export interface CityWeather {
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

export enum WeatherCondition {
  THUNDERSTORM = "Thunderstorm",
  DRIZZLE = "Drizzle",
  RAIN = "Rain",
  SNOW = "Snow",
  ATMOSPHERE = "Atmosphere",
  CLEAR = "Clear",
  CLOUDS = "Clouds",
}
