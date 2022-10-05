import { useState } from "react";
import axios from "axios";
import { SearchBar } from "../../components/SearchBar";
import { WeatherCard } from "../../components/WeatherCard";
import { CityWeather } from "../../interfaces";


export function WeatherContainer() {
  const [weathers, setWeathers] = useState<CityWeather[]>([]);

  async function getWeather(city: string){
    try {
      await axios
        .get(`http://localhost:8080/weather/${city}`)
        .then((res) => setWeathers([...weathers, res.data]));
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <>
      <SearchBar onSubmit={getWeather} />
      {weathers && 
        weathers.map((city) => <WeatherCard {...city}/>)
      }
    </>
  );
}
