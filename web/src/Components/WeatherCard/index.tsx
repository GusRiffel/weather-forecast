import { CityWeather } from "../../interfaces";
import { WeatherCondition } from "../../interfaces";
import * as dayjs from "dayjs";
import { BsFillCloudsFill, BsCloudHazeFill, BsFillSunFill, BsCloudDrizzleFill, BsFillCloudRainFill } from "react-icons/bs";
import {IoSnowSharp , IoThunderstormSharp} from "react-icons/io5";

interface WeatherCardProps extends CityWeather {}

export function WeatherCard({
  city,
  weatherCondition,
  weatherData,
  windSpeed,
}: WeatherCardProps) {


  function setWeatherIcon() {
    switch (weatherCondition) {
      case WeatherCondition.CLOUDS:
        return <BsFillCloudsFill size={60} />
      case WeatherCondition.HAZE:
        return <BsCloudHazeFill size={60}/>
      case WeatherCondition.CLEAR:
        return  <BsFillSunFill size={60} />
      case WeatherCondition.DRIZZLE:
        return  <BsCloudDrizzleFill size={60} />
      case WeatherCondition.RAIN:
        return  <BsFillCloudRainFill size={60} />
      case WeatherCondition.SNOW:
        return <IoSnowSharp size={60} />
      case WeatherCondition.THUNDERSTORM:
        return <IoThunderstormSharp size={60} />
    }
  }

  return (
    <div className="flex justify-center py-5 text-white font-bold">
      <div className={`flex flex-col shadow-lg p-2 bg-gradient-to-b w-[50%] from-[#99ccff] to-[#66b2ff] rounded-md`}>
        <div className="flex text-5xl font-black justify-center">
          <h1>{city}</h1>
        </div>
        <div>
          <div className="grid grid-cols-3 items-center justify-items-center py-2">
            <div>
              <div className="text-2xl">
                <h1>{dayjs().format("MMMM D, YYYY")}</h1>
              </div>
            </div>
            <div>{setWeatherIcon()}</div>
            <div className="text-2xl">
              <h1>{weatherCondition}</h1>
            </div>
          </div>
          <div className="grid grid-cols-3 justify-items-center text-center">
            <div>
              <div className="text-2xl">
                <h1>Temperature</h1>
              </div>
              <div className="text-3xl">
                <h4>{Math.round(Number(weatherData.temp))} °C</h4>
              </div>
            </div>
            <div>
              <div className="text-2xl">
                <h1>Humidity</h1>
              </div>
              <div className="text-3xl">
                <h4>{Math.round(Number(weatherData.humidity))} %</h4>
              </div>
            </div>
            <div>
              <div>
                <h1 className="text-2xl">Wind</h1>
              </div>
              <div className="text-3xl">
                <h4>{Math.round(Number(windSpeed * 3.6))} km/h</h4>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
