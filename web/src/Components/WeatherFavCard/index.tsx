import { useContext } from "react";
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
  const background = "bg-gradient-to-b from-[#99ccff] to-[#66b2ff] rounded-md"

  return (
      <div
        className={`mx-3 text-white text-center p-3 ${background}`}
      >
        <div className="">
          <div></div>
          <div className="">
            <h1>{props.city}</h1>
          </div>
          <div className="">
            {/* {cookieContext?.currentUser && (
              <BsStarFill size={24} onClick={() => favoriteHandler(city)} />
            )} */}
          </div>
        </div>
        <div>
          <div className="">
            <div>
              <div className="">
                <h1>{dayjs().format("MMMM D, YYYY")}</h1>
              </div>
            </div>
            <div>
              <BsFillCloudsFill />
            </div>
            <div className="">
              <h1>Clouds</h1>
            </div>
          </div>
          <div className="">
            <div>
              <div className="">
                <h1>Temperature</h1>
              </div>
              <div className="">
                <h4>18 °C</h4>
              </div>
            </div>
            <div>
              <div className="">
                <h1>Humidity</h1>
              </div>
              <div className="">
                <h4>50 %</h4>
              </div>
            </div>
            <div>
              <div>
                <h1 className="">Wind</h1>
              </div>
              <div className="">
                <h4>17 km/h</h4>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}
