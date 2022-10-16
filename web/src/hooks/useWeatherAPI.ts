import axios from "axios";
import { useContext, useEffect, useState } from "react";
import { CookieContext } from "../context/AuthContext";

export const useWeatherAPI = () => {
  const { currentUser, getCookie } = useContext(CookieContext);

  const getWeatherByCity = async (city: string) => {
    try {
      return await axios
        .get(`http://localhost:8080/weather/${city}`)
        .then((res) => res.data);
    } catch (error) {
      return error;
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

  return {getWeatherByCity, deleteFavorite, createFavorite, getFavoriteCities}
};
