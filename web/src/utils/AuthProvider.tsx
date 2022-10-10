import { AxiosResponse } from "axios";
import { createContext, PropsWithChildren, useState, useEffect } from "react";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

interface CookieContextType {
  createCookie: (data: AxiosResponse<any, any>) => void;
  getCookie: () => {
    access_token: string;
    refresh_token: string;
    username: string;
  };
  removeCookie: () => void;
  currentUser: string;
}

export const CookieContext = createContext<CookieContextType | null>(null);

export function AuthProvider({ children }: PropsWithChildren) {
  const cookies = new Cookies();
  const navigate = useNavigate();
  const [currentUser, setCurrentUser] = useState("");

  useEffect(() => {}, [currentUser]);

  function createCookie(data: AxiosResponse<any, any>) {
    cookies.set("auth", data);
    setCurrentUser(getCookie().username);
    navigate("/");
  }

  function getCookie() {
    return cookies.get("auth");
  }

  function removeCookie() {
    cookies.remove("auth");
    setCurrentUser("");
    navigate("/");
  }

  return (
    <CookieContext.Provider
      value={{
        createCookie,
        getCookie,
        removeCookie,
        currentUser,
      }}
    >
      {children}
    </CookieContext.Provider>
  );
}
