import {
  BsCloudDrizzleFill,
  BsCloudHazeFill,
  BsFillCloudRainFill,
  BsFillCloudsFill,
  BsFillSunFill,
} from "react-icons/bs";
import { IoSnowSharp, IoThunderstormSharp } from "react-icons/io5";
import { WeatherCondition } from "../interfaces";

export const getIconByWeatherCondition = (weatherCondition: string) => {
  switch (weatherCondition) {
    case WeatherCondition.CLOUDS:
      return <BsFillCloudsFill size={60} />;
    case WeatherCondition.HAZE:
      return <BsCloudHazeFill size={60} />;
    case WeatherCondition.MIST:
      return <BsCloudHazeFill size={60} />;
    case WeatherCondition.CLEAR:
      return <BsFillSunFill size={60} />;
    case WeatherCondition.DRIZZLE:
      return <BsCloudDrizzleFill size={60} />;
    case WeatherCondition.RAIN:
      return <BsFillCloudRainFill size={60} />;
    case WeatherCondition.SNOW:
      return <IoSnowSharp size={60} />;
    case WeatherCondition.THUNDERSTORM:
      return <IoThunderstormSharp size={60} />;
  }
};
