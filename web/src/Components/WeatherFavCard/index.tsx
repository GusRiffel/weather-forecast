import { useContext, useState } from "react";
import { BsFillCloudsFill } from "react-icons/bs";
import { IoClose } from "react-icons/io5";
import { CityWeather } from "../../interfaces";
import { CookieContext } from "../../context/AuthContext";

interface WeatherFavCardProps extends CityWeather {
  city: string;
  onFavDelete: any;
}

// interface WeatherFavCardProps {
//   city: string;
//   onFavDelete: any;
// }

export function WeatherFavCard({
  weatherCondition,
  weatherData,
  windSpeed,
  city,
  onFavDelete,
}: WeatherFavCardProps) {
  const background = "bg-gradient-to-b from-[#99ccff] to-[#66b2ff] rounded-md";
  const grid = "grid grid-rows-2";
  const [viewWeather, setViewWeather] = useState<boolean>(true);
  const cookieContext = useContext(CookieContext);

  function handleFavClick() {
    setViewWeather(!viewWeather);
  }

  return (
    <div
      className={`mx-3 items-center cursor-pointer w-[10rem] text-white text-center ${background}`}
    >
      <div>
        <div className="flex justify-end">
          <IoClose onClick={() => onFavDelete(city)} />
        </div>
        <div className="text-3xl" onClick={() => handleFavClick()}>
          <h1>{city}</h1>
        </div>
      </div>
      {viewWeather && (
        <div className={`${viewWeather ? grid : ""}`}>
          <div>
            <div className="text-2xl">
              <h4>{Math.round(Number(weatherData.temp))}°C</h4>
            </div>
            <div className="flex justify-center">
              <BsFillCloudsFill size={32} />
            </div>
            <div className="">
              <h1>{weatherCondition}</h1>
            </div>
          </div>
          <div>
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
        </div>
      )}
    </div>
  );
}
