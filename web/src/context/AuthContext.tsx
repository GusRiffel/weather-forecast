import { createContext, ReactNode, useEffect, useState } from "react";
import { getCookie } from "../utils/cookieHelper";

interface UserContextProps {
  currentUser: string;
  createCurrentUser: (username: string) => void;
  deleteCurrentUser: () => void;
}

interface AuthProviderProps {
  children: ReactNode;
}

export const UserContext = createContext<UserContextProps>(
  {} as UserContextProps
);

export const AuthContext = ({ children }: AuthProviderProps) => {
  const [currentUser, setCurrentUser] = useState("");

  useEffect(() => {
    const cookieUser = getCookie();
    if (cookieUser) {
      setCurrentUser(cookieUser.username);
    }
  }, []);

  const createCurrentUser = (username: string) => {
    setCurrentUser(username);
  };

  const deleteCurrentUser = () => {
    setCurrentUser("");
  };

  return (
    <UserContext.Provider
      value={{
        currentUser,
        createCurrentUser,
        deleteCurrentUser,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};
