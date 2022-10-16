import { useContext } from "react";
import { CookieContext } from "../context/AuthContext";
import { service } from "./../api/service";

export const useWeatherAPI = () => {
  const { currentUser, getCookie } = useContext(CookieContext);

  const getWeatherByCity = async (city: string) => {
    try {
      return await service.get(`/weather/${city}`).then((res) => res.data);
    } catch (error) {
      return error;
    }
  };

  const getFavoriteCities = async (username: string) => {
    try {
      return await service
        .get(`/favorites/${username}`, {
          headers: {
            Authorization: `Bearer ${getCookie().refresh_token}`,
          },
        })
        .then((res) => res.data);
    } catch (error) {
      return error;
    }
  };

  const createFavorite = async (city: string) => {
    try {
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
        .then((res) => res.data);
    } catch (error) {
      return error;
    }
  };

  const deleteFavorite = async (city: string) => {
    try {
      return await service
        .delete(`/favorites/delete`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getCookie().refresh_token}`,
          },
          data: { username: currentUser, city },
        })
        .then((res) => res.status);
    } catch (error) {
      return error;
    }
  };

  return {
    getWeatherByCity,
    deleteFavorite,
    createFavorite,
    getFavoriteCities,
  };
};
