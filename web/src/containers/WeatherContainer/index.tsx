import axios from "axios";
import { SearchBar } from "../../components/SearchBar";
import { WeatherCard } from "../../components/WeatherCard";
import { CityWeather } from "../../interfaces";
import { useContext, useEffect, useState } from "react";
import { CookieContext } from "../../utils/AuthProvider";
import { WeatherFavCard } from "../../components/WeatherFavCard";

export function WeatherContainer() {
  const [weathers, setWeathers] = useState<CityWeather[]>([]);
  const [favCities, setFavCities] = useState<string[]>([]);
  const cookieContext = useContext(CookieContext);

  useEffect(() => {
    if (cookieContext?.currentUser) {
      getFavWeather(cookieContext.currentUser);
    }
  }, []);

  async function getFavWeather(username: string) {
    try {
      await axios
        .get(`http://localhost:8080/favorites/${username}`)
        .then((res) => setFavCities(res.data));
    } catch (error) {
      console.log(error);
    }
  }

  async function getWeather(city: string) {
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
      <div className="flex justify-center">
        {favCities && favCities.map((city) => <WeatherFavCard city={city} />)}
      </div>
      {weathers && weathers.map((city, index) => <WeatherCard {...city} key={`weather-card-${index}`} />)}
    </>
  );
}
