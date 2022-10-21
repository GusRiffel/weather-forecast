import { service } from "../api/service";
import { LoginFormValues, RegisterFormValues, UserToken } from "../interfaces";

export const useUser = () => {
  const login = async (data: LoginFormValues): Promise<UserToken> => {
    return await service
      .post("/login", { username: data.username, password: data.password })
      .then((res) => res.data)
      .catch((error) => error);
  };

  const create = async (data: RegisterFormValues) => {
    return await service
      .post("/user/createuser", {
        username: data.username,
        email: data.email,
        password: data.password,
      })
      .then((res) => res.status)
      .catch((error) => error);
  };

  return { login, create };
};
