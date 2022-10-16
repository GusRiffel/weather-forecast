import axios from "axios";
import { useContext, useEffect, useState } from "react";
import { SearchBar } from "../../components/SearchBar";
import { WeatherCard } from "../../components/WeatherCard";
import { WeatherFavCard } from "../../components/WeatherFavCard";
import { CityWeather } from "../../interfaces";
import { CookieContext } from "../../context/AuthContext";

export function WeatherContainer() {
  const [weathers, setWeathers] = useState<CityWeather[]>([]);
  const [favoriteWeather, setFavoriteWeather] = useState<CityWeather[]>([]);
  const { currentUser, getCookie } = useContext(CookieContext);

  useEffect(() => {
    if (currentUser) {
      handleFavoriteWeather(currentUser);
    } else {
      setFavoriteWeather([]);
      setWeathers([]);
    }
  }, [currentUser]);

  useEffect(() => {
    if (weathers.length > 3) {
      setWeathers(weathers.splice(0, 3));
    }
  }, [weathers]);

  const deleteFavorite = async (city: string) => {
    try {
      return await axios
        .delete(`http://localhost:8080/favorites/delete`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getCookie().refresh_token}`,
          },
          data: { username: currentUser, city },
        })
        .then((res) => res.status);
    } catch (error) {
      console.log(error);
    }
  };

  const createFavorite = async (city: string) => {
    try {
      return await axios
        .post(
          "http://localhost:8080/favorites/create",
          { username: currentUser, city: city },
          {
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
              Authorization: `Bearer ${getCookie().refresh_token}`,
            },
          }
        )
        .then((res) => res.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log("error message: ", error.message);
        return error.message;
      }
    }
  };

  const getFavoriteCities = async (username: string) => {
    try {
      return await axios
        .get(`http://localhost:8080/favorites/${username}`, {
          headers: {
            Authorization: `Bearer ${getCookie().refresh_token}`,
          },
        })
        .then((res) => res.data);
    } catch (error) {
      console.log(error);
    }
  };

  const getWeatherByCity = async (city: string) => {
    try {
      return await axios
        .get(`http://localhost:8080/weather/${city}`)
        .then((res) => res.data);
    } catch (error) {
      return error;
    }
  };

  const handleDeleteFavoriteCity = async (city: string) => {
    const data = await deleteFavorite(city);
    if (data === 204) {
      setFavoriteWeather([
        ...favoriteWeather.filter(
          (w) => w.city.toUpperCase() !== city.toUpperCase()
        ),
      ]);
    }
  };

  const handleAddFavoriteCity = async (city: string) => {
    const data = await createFavorite(city);
    if (data.id) {
      const newFavWeather = weathers.find(
        (w) => w.city.toUpperCase() === city.toUpperCase()
      );
      if (newFavWeather) {
        setFavoriteWeather([...favoriteWeather, newFavWeather]);
      }
    }
  };

  const handleFavoriteWeather = async (username: string) => {
    const favCities: string[] = await getFavoriteCities(username);
    const data: CityWeather[] = await Promise.all(
      favCities.map((city) => getWeatherByCity(city))
    );
    setFavoriteWeather([...data]);
  };

  const handleWeatherSearch = async (city: string) => {
    if (hasAlreadySearched(city) || isPartOfFavCities(city)) {
      console.log("City already searched");
      return;
    }
    const data: CityWeather = await getWeatherByCity(city);
    if (data.city) {
      setWeathers([data, ...weathers]);
    }
  };

  function hasAlreadySearched(city: string) {
    return weathers.some((w) => w.city.toUpperCase() === city.toUpperCase());
  }

  function isPartOfFavCities(city: string) {
    return favoriteWeather.some(
      (w) => w.city.toUpperCase() === city.toUpperCase()
    );
  }

  return (
    <>
      <SearchBar onSubmit={handleWeatherSearch} />
      <div className="flex justify-center">
        {favoriteWeather &&
          favoriteWeather.map((favoriteWeather, index) => (
            <WeatherFavCard
              {...favoriteWeather}
              key={`weather-fav-card-${index}`}
              onFavDelete={() => handleDeleteFavoriteCity(favoriteWeather.city)}
            />
          ))}
      </div>
      {weathers &&
        weathers.map((weather, index) => (
          <WeatherCard
            {...weather}
            key={`weather-card-${index}`}
            onFavorite={() => handleAddFavoriteCity(weather.city)}
            isFavorite={isPartOfFavCities(weather.city)}
          />
        ))}
    </>
  );
}
