import { useContext } from "react";
import { BsStarFill } from "react-icons/bs";
import { getIconByWeatherCondition } from "../../utils/iconHelpers";

import * as dayjs from "dayjs";

import { UserContext } from "../../context/AuthContext";
import { CityWeather } from "../../interfaces";

interface WeatherCardProps extends CityWeather {
  onFavorite: any;
  isFavorite: boolean;
}

export const WeatherCard = ({
  city,
  weatherCondition,
  weatherData,
  windSpeed,
  onFavorite,
  isFavorite,
}: WeatherCardProps) => {
  const { currentUser } = useContext(UserContext);

  return (
    <div className="flex justify-center py-5 text-white font-bold">
      <div
        className={`flex flex-col shadow-lg p-2 bg-gradient-to-b w-[50%] from-[#99ccff] to-[#66b2ff] rounded-md`}
      >
        <div className="grid grid-cols-3 text-5xl font-black">
          <div></div>
          <div className="text-center">
            <h1>{city}</h1>
          </div>
          <div className="justify-self-end cursor-pointer">
            {currentUser && (
              <BsStarFill
                className={
                  isFavorite
                    ? "text-yellow-300"
                    : "text-white hover:text-yellow-300"
                }
                size={24}
                onClick={() => onFavorite(city)}
              />
            )}
          </div>
        </div>
        <div>
          <div className="grid grid-cols-3 items-center justify-items-center py-2">
            <div>
              <div className="text-2xl">
                <h1>{dayjs().format("MMMM D, YYYY")}</h1>
              </div>
            </div>
            <div>{getIconByWeatherCondition(weatherCondition)}</div>
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
};
