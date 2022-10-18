import { UserToken } from "./../interfaces";
import Cookies from "js-cookie";

export const getCookie = () => {
  const cookie = Cookies.get("auth");
  if (cookie) {
    const cookieDecoded: UserToken = JSON.parse(decodeURIComponent(cookie));
    return cookieDecoded;
  }
};

export const createCookie = (data: UserToken) => {
  Cookies.set("auth", JSON.stringify(data));
};

export const removeCookie = () => {
  Cookies.remove("auth");
};
