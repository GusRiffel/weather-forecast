import { createContext, ReactNode, useState } from "react";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { UserToken } from "../interfaces";

interface CookieContextProps {
  createCookie: (data: UserToken) => void;
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

export const CookieContext = createContext<CookieContextProps>(
  {} as CookieContextProps
);

export const AuthContext = ({ children }: AuthProviderProps) => {
  const cookies = new Cookies();
  const navigate = useNavigate();
  const [currentUser, setCurrentUser] = useState("");

  const createCookie = (data: UserToken) => {
    cookies.set("auth", data);
    setCurrentUser(getCookie().username);
    navigate("/");
  };

  const getCookie = () => {
    return cookies.get("auth");
  };

  const removeCookie = () => {
    cookies.remove("auth");
    setCurrentUser("");
  };

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
};
