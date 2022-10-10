import { createContext, PropsWithChildren } from "react";
import { Cookies } from "react-cookie";

interface CookieContextType {
  createCookie: (data: any) => void;
  getCookie: () => {};
  removeCookie: () => void;
}

export const CookieContext = createContext<CookieContextType | null>(null);

export function CookieProvider({ children }: PropsWithChildren) {
  const cookies = new Cookies();

  function createCookie(data: any) {
    cookies.set("auth", data);
  }

  function getCookie() {
    return cookies.get("auth");
  }

  function removeCookie() {
    cookies.remove("auth");
  }

  return (
    <CookieContext.Provider
      value={{
        createCookie,
        getCookie,
        removeCookie,
      }}
    >
      {children}
    </CookieContext.Provider>
  );
}
