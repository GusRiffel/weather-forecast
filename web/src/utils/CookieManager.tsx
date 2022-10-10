import { Cookies } from "react-cookie";

interface CookieManager {
  cookies: Cookies;
}

export function CookieManager(cookies: Cookies) {
  function createCookie(data: any) {
    cookies.set("auth", data);
  }

  function getCookie() {
    return cookies.get("auth");
  }

  return {
    createCookie,
    getCookie,
  };
}
