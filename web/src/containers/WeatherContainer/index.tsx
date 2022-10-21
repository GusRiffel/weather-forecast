import { useContext, useEffect, useState } from "react";
import { SearchBar } from "../../components/SearchBar";
import { WeatherCard } from "../../components/WeatherCard";
import { WeatherFavCard } from "../../components/WeatherFavCard";
import { UserContext } from "../../context/AuthContext";
import { useWeatherAPI } from "../../hooks/useWeatherAPI";
import { CityWeather } from "../../interfaces";

export const WeatherContainer = () => {
  const [weathers, setWeathers] = useState<CityWeather[]>([]);
  const [favoriteWeather, setFavoriteWeather] = useState<CityWeather[]>([]);
  const { currentUser } = useContext(UserContext);
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
    const response = await deleteFavorite({ city, currentUser });
    if (response === 204) {
      setFavoriteWeather([
        ...favoriteWeather.filter(
          (w) => w.city.toUpperCase() !== city.toUpperCase()
        ),
      ]);
    }
  };

  const handleAddFavoriteCity = async (city: string) => {
    const response = await createFavorite({ city, currentUser });
    if (response.id) {
      const newFavWeather = weathers.find(
        (w) => w.city.toUpperCase() === city.toUpperCase()
      );
      if (newFavWeather) {
        setFavoriteWeather([...favoriteWeather, newFavWeather]);
      }
    }
  };

  const handleFavoriteWeather = async (username: string) => {
    const favCities = await getFavoriteCities({ username });
    const response = await Promise.all(
      favCities.map((city) => getWeatherByCity({ city }))
    );
    setFavoriteWeather([...response]);
  };

  const handleWeatherSearch = async (city: string) => {
    if (hasAlreadySearched(city) || isPartOfFavCities(city)) {
      console.log("City already searched");
      return;
    }
    const response = await getWeatherByCity({ city });
    if (response.city) {
      setWeathers([response, ...weathers]);
    }
  };

  const hasAlreadySearched = (city: string) => {
    return weathers.some((w) => w.city.toUpperCase() === city.toUpperCase());
  };

  const isPartOfFavCities = (city: string) => {
    return favoriteWeather.some(
      (w) => w.city.toUpperCase() === city.toUpperCase()
    );
  };

  const manageFavoriteView = (divId: string) => {
    console.log(document.getElementById(divId));
    const div = document.getElementById(divId);
    if (div) {
      div.remove();
    }
  };

  return (
    <>
      <SearchBar onSubmit={handleWeatherSearch} />
      <div className="flex justify-center">
        <ul className="inline-flex list-none">
          {favoriteWeather &&
            favoriteWeather.map((favoriteWeather, index) => (
              <li>
                <WeatherFavCard
                  {...favoriteWeather}
                  key={`weather-fav-card-${index}`}
                  divId={`weather-fav-card-${index}`}
                  favoriteView={manageFavoriteView}
                  onFavDelete={() =>
                    handleDeleteFavoriteCity(favoriteWeather.city)
                  }
                />
              </li>
            ))}
        </ul>
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
};
