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
  return (
    <div className="flex justify-center py-5 mx-3 text-white font-bold">
      <div
        className={`flex flex-col shadow-lg p-2 bg-gradient-to-b w-[16rem] from-[#99ccff] to-[#66b2ff] rounded-md`}
      >
        <div className="grid grid-cols-3 text-4xl font-black">
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
    </div>
  );
}
