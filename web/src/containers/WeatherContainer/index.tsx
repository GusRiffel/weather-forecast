import axios from "axios";
import { useContext, useEffect, useState } from "react";
import { SearchBar } from "../../components/SearchBar";
import { WeatherCard } from "../../components/WeatherCard";
import { WeatherFavCard } from "../../components/WeatherFavCard";
import { CityWeather } from "../../interfaces";
import { CookieContext } from "../../utils/AuthProvider";

export function WeatherContainer() {
  const [weathers, setWeathers] = useState<CityWeather[]>([]);
  const [favoriteCities, setFavoriteCities] = useState<string[]>([]);
  const [favoriteWeather, setFavoriteWeather] = useState<CityWeather[]>([]);
  const [postData, setPostData] = useState();
  const { currentUser, getCookie } = useContext(CookieContext);

  useEffect(() => {
    if (currentUser) {
      handleFavoriteCity(currentUser);
    } else {
      setFavoriteCities([]);
    }
  }, [currentUser]);

  useEffect(() => {
    if (currentUser && !favoriteCities.length) {
      handleFavoriteCity(currentUser);
    }
  }, [favoriteCities]);

  useEffect(() => {
    if (weathers.length > 3) {
      setWeathers(weathers.splice(0, 3));
    }
  }, [weathers]);

  useEffect(() => {
    if (favoriteCities.length) {
      handleFavoriteWeather();
    }
  }, [favoriteCities]);

  const deleteFavorite = async (city: string) => {
    try {
      await axios
        .delete(`http://localhost:8080/favorites/delete`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getCookie().refresh_token}`,
          },
          data: { username: currentUser, city },
        })
        .then(() => getFavoriteCities(currentUser));
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
    // if (hasAlreadySearched(city) || isPartOfFavCities(city)) {
    //   console.log("City already searched");
    //   return;
    // }
    try {
      return await axios
        .get(`http://localhost:8080/weather/${city}`)
        .then((res) => res.data);
    } catch (error) {
      return error;
    }
  };

  const handleAddFavoriteCity = async (city: string) => {
    const data = await createFavorite(city);
    if (data.id) {
      setFavoriteCities([]);
    }
  };

  const handleFavoriteCity = async (username: string) => {
    const data: string[] = await getFavoriteCities(username);
    if (data.length) {
      setFavoriteCities([...data]);
    }
  };

  const handleFavoriteWeather = async () => {
    console.log(favoriteCities);
    const data: CityWeather[] = await Promise.all(
      favoriteCities.map((city) => getWeatherByCity(city))
    );
    console.log(data);
    setFavoriteWeather([...data]);
  };

  const handleWeatherSearch = async (city: string) => {
    const data: CityWeather = await getWeatherByCity(city);
    if (data.city) {
      setWeathers([data, ...weathers]);
    } else {
      console.log(data);
    }
  };

  function hasAlreadySearched(city: string) {
    return weathers.some((w) => w.city.toUpperCase() === city.toUpperCase());
  }

  function isPartOfFavCities(city: string) {
    return favoriteCities.some((c) => c.toUpperCase() === city.toUpperCase());
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
              onFavDelete={() => deleteFavorite(favoriteWeather.city)}
            />
          ))}
      </div>
      {/* <div className="flex justify-center">
        {favoriteCities &&
          favoriteCities.map((city, index) => (
            <WeatherFavCard
              city={city}
              key={`weather-fav-card-${index}`}
              onFavDelete={() => deleteFavorite(city)}
            />
          ))}
      </div> */}
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
