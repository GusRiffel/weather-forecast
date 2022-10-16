import { useContext, useEffect, useState } from "react";
import { SearchBar } from "../../components/SearchBar";
import { WeatherCard } from "../../components/WeatherCard";
import { WeatherFavCard } from "../../components/WeatherFavCard";
import { CookieContext } from "../../context/AuthContext";
import { useWeatherAPI } from "../../hooks/useWeatherAPI";
import { CityWeather } from "../../interfaces";

export function WeatherContainer() {
  const [weathers, setWeathers] = useState<CityWeather[]>([]);
  const [favoriteWeather, setFavoriteWeather] = useState<CityWeather[]>([]);
  const { currentUser } = useContext(CookieContext);
  const {
    deleteFavorite,
    createFavorite,
    getFavoriteCities,
    getWeatherByCity,
  } = useWeatherAPI();

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
