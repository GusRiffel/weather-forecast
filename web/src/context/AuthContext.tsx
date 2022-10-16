import { AxiosResponse } from "axios";
import { createContext, useState, ReactNode } from "react";
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

interface AuthProviderProps {
  children: ReactNode;
}

export const CookieContext = createContext<CookieContextType>({} as CookieContextType);

export function AuthContext({ children }: AuthProviderProps) {
  const cookies = new Cookies();
  const navigate = useNavigate();
  const [currentUser, setCurrentUser] = useState("");

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
