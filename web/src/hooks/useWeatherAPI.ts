import { CityWeather, FavoriteCity } from "./../interfaces";
import { useContext } from "react";
import { CookieContext } from "../context/AuthContext";
import { service } from "./../api/service";

export const useWeatherAPI = () => {
  const { getCookie } = useContext(CookieContext);

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
      .get(`/favorites/${username}`, {
        headers: {
          Authorization: `Bearer ${getCookie().refresh_token}`,
        },
      })
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
      .post(
        "/favorites/create",
        { username: currentUser, city: city },
        {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
            Authorization: `Bearer ${getCookie().refresh_token}`,
          },
        }
      )
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
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${getCookie().refresh_token}`,
        },
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
