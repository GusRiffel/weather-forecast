import { useContext, useState } from "react";
import {
  BsFillCloudsFill,
  BsCloudHazeFill,
  BsFillSunFill,
  BsCloudDrizzleFill,
  BsFillCloudRainFill,
  BsStarFill,
} from "react-icons/bs";
import { IoSnowSharp, IoThunderstormSharp } from "react-icons/io5";

import * as dayjs from "dayjs";

interface WeatherFavCardProps {
  city: string;
}

export function WeatherFavCard(props: WeatherFavCardProps) {
  const background = "bg-gradient-to-b from-[#99ccff] to-[#66b2ff] rounded-md";
  const grid = "grid grid-rows-2"
  const [viewWeather, setViewWeather] = useState<boolean>(false);

  function handleFavClick() {
    setViewWeather(!viewWeather);
  }

  return (
    <div
      className={`mx-3 items-center cursor-pointer w-[10rem] text-white text-center px-3 ${background}`}
      onClick={() => handleFavClick()}
    >
      <div>
        <div className="text-3xl">
          <h1>{props.city}</h1>
        </div>
      </div>
      {viewWeather && (
        <div className={`${viewWeather ? grid : ""}`}>
          <div>
            <div className="text-2xl">
              <h4>18°C</h4>
            </div>
            <div className="flex justify-center">
              <BsFillCloudsFill size={32} />
            </div>
            <div className="">
              <h1>Clouds</h1>
            </div>
          </div>
          <div>
            <div className="grid grid-cols-2">
              <div className="">
                <h1>Humidity</h1>
                <h4>50%</h4>
              </div>
              <div className="">
                <h1>Wind</h1>
                <h4>17km/h</h4>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
