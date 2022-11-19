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

  const getRefreshToken = async (currentUser: string) => {
    return await service
      .get(`token/${currentUser}`)
      .then((res) => res.data)
      .catch((error) => error);
  };

  const saveRefreshToken = async (data: UserToken) => {
    return await service
      .post("/token/save", {
        username: data.username,
        refreshToken: data.refresh_token,
      })
      .then((res) => res.status)
      .catch((error) => error);
  };

  const deleteRefreshToken = async (currentUser: string) => {
    return await service
      .delete(`token/delete/${currentUser}`)
      .then((res) => res.status)
      .catch((error) => error);
  };

  const refreshAccessToken = async (currentUser: string) => {
    const response = await getRefreshToken(currentUser);
    if (response.refreshToken) {
      return await service
        .get(`user/token/refresh`, {
          headers: { Authorization: `Bearer ${response.refreshToken}` },
        })
        .then((res) => res.data)
        .catch((error) => error);
    }
  };

  return {
    login,
    create,
    saveRefreshToken,
    deleteRefreshToken,
    refreshAccessToken,
  };
};
