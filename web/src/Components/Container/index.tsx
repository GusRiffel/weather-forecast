import { useState } from "react";
import axios from "axios";
import { SearchBar } from "../SearchBar";

interface CityWeather {
  city: string;
  dateTime: string;
  weatherData: {
    temp: string;
    pressure: string;
    humidity: string;
    temp_min: string;
    temp_max: string;
  };
  weatherCondition: string;
}

export function Container() {
  const [cityWeather, setCityWeather] = useState<CityWeather[]>([]);

  console.log(cityWeather);

  async function getWeather(city: string){
    try {
      await axios
        .get(`http://localhost:8080/weather/${city}`)
        .then((res) => setCityWeather(res.data));
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div>
      <SearchBar onSubmit={getWeather} />
      
    </div>
  );
}
