import { useState } from "react";
import { IoClose } from "react-icons/io5";
import { CityWeather } from "../../interfaces";
import { getIconByWeatherCondition } from "../../utils/iconHelpers";

interface WeatherFavCardProps extends CityWeather {
  city: string;
  onFavDelete: any;
  divId: string;
  favoriteView: (divId: string) => void;
}

export const WeatherFavCard = ({
  weatherCondition,
  weatherData,
  windSpeed,
  city,
  onFavDelete,
  divId,
  favoriteView,
}: WeatherFavCardProps) => {
  const background = "bg-gradient-to-b from-[#99ccff] to-[#66b2ff] rounded-md";
  const [viewWeather, setViewWeather] = useState<boolean>(false);

  const handleFavClick = () => {
    setViewWeather(!viewWeather);
  };

  return (
    <>
      <div
        className={` flex flex-col mx-3 cursor-pointer w-[10rem] text-white text-center ${background}`}
      >
        <IoClose className="absolute place-self-end" onClick={() => onFavDelete(city)} />
        <div
          className="flex text-3xl h-20 items-center self-center"
          onClick={() => handleFavClick()}
        >
          <h1>{city}</h1>
        </div>
        {viewWeather && (
          <div>
            <div>
              <h4>{Math.round(Number(weatherData.temp))}°C</h4>
              <div className="flex justify-center">
                {getIconByWeatherCondition(weatherCondition)}
              </div>
              <h1>{weatherCondition}</h1>
            </div>
            <div className="grid grid-cols-2">
              <div className="">
                <h1>Humidity</h1>
                <h4>{Math.round(Number(weatherData.humidity))}%</h4>
              </div>
              <div className="">
                <h1>Wind</h1>
                <h4>{Math.round(Number(windSpeed * 3.6))}Km/h</h4>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
};
