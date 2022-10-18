import { service } from "./../api/service";
import { CityWeather, FavoriteCity } from "./../interfaces";

export const useWeatherAPI = () => {
  const getWeatherByCity = async ({
    city,
  }: {
    city: string;
  }): Promise<CityWeather> => {
    return await service
      .get(`/weather/${city}`)
      .then((res) => res.data)
      .catch((error) => error);
  };

  const getFavoriteCities = async ({
    username,
  }: {
    username: string;
  }): Promise<string[]> => {
    return await service
      .get(`/favorites/${username}`)
      .then((res) => res.data)
      .catch((error) => error);
  };

  const createFavorite = async ({
    city,
    currentUser,
  }: {
    city: string;
    currentUser: string;
  }): Promise<FavoriteCity> => {
    return await service
      .post("/favorites/create", { username: currentUser, city: city })
      .then((res) => res.data)
      .catch((error) => error);
  };

  const deleteFavorite = async ({
    city,
    currentUser,
  }: {
    city: string;
    currentUser: string;
  }): Promise<Number> => {
    return await service
      .delete(`/favorites/delete`, {
        data: { username: currentUser, city },
      })
      .then((res) => res.status)
      .catch((error) => error);
  };

  return {
    getWeatherByCity,
    deleteFavorite,
    createFavorite,
    getFavoriteCities,
  };
};
