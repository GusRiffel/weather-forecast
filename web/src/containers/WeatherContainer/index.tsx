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
  const { currentUser } = useContext(CookieContext);

  useEffect(() => {
    if (currentUser) {
      getFavWeather(currentUser);
    } else {
      setFavCities([]);
    }
  }, [currentUser]);

  useEffect(() => {
    if (weathers.length > 3) {
      setWeathers(weathers.splice(0, 3));
    }
  }, [weathers]);

  async function handleDelete(city: string) {
    try {
      await axios
        .delete(`http://localhost:8080/favorites/delete`, {
          data: { username: currentUser, city },
        })
        .then(() => getFavWeather(currentUser));
    } catch (error) {
      console.log(error);
    }
  }

  async function favoriteHandler(city: string) {
    try {
      await axios
        .post(
          "http://localhost:8080/favorites/create",
          { username: currentUser, city: city },
          {
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
          }
        )
        .then(() => getFavWeather(currentUser));
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log("error message: ", error.message);
        return error.message;
      }
    }
  }

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
    if (hasAlreadySearched(city) || isPartOfFavCities(city)) {
      console.log("City already searched");
      return;
    }
    try {
      await axios
        .get(`http://localhost:8080/weather/${city}`)
        .then((res) => setWeathers([res.data, ...weathers]));
    } catch (error) {
      console.log(error);
    }
  }

  function hasAlreadySearched(city: string) {
    return weathers.some((w) => w.city.toUpperCase() === city.toUpperCase());
  }

  function isPartOfFavCities(city: string) {
    return favCities.some((c) => c.toUpperCase() === city.toUpperCase());
  }

  return (
    <>
      <SearchBar onSubmit={getWeather} />
      <div className="flex justify-center">
        {favCities &&
          favCities.map((city, index) => (
            <WeatherFavCard
              city={city}
              key={`weather-card-${index}`}
              onFavDelete={() => handleDelete(city)}
            />
          ))}
      </div>
      {weathers &&
        weathers.map((weather, index) => (
          <WeatherCard
            {...weather}
            key={`weather-card-${index}`}
            onFavorite={() => favoriteHandler(weather.city)}
            isFavorite={isPartOfFavCities(weather.city)}
          />
        ))}
    </>
  );
}
