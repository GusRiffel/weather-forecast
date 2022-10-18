import { service } from "../api/service";
import { LoginFormValues, UserToken } from "../interfaces";

export const useUser = () => {
  const login = async (data: LoginFormValues): Promise<UserToken> => {
    return await service
      .post(
        "/login",
        { username: data.username, password: data.password },
        {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }
      )
      .then((res) => res.data)
      .catch((error) => error);
  };

  return { login };
};
