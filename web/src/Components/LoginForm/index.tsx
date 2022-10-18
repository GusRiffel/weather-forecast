import { useContext } from "react";
import { useForm } from "react-hook-form";
import { LoginFormValues } from "../../interfaces";
import { useNavigate } from "react-router-dom";

import { UserContext } from "../../context/AuthContext";
import { useUser } from "../../hooks/useUser";
import { createCookie } from "../../utils/cookieHelper";

export const LoginForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>();
  const { login } = useUser();
  const { createCurrentUser } = useContext(UserContext);
  const navigate = useNavigate();

  const handleLogin = async (data: LoginFormValues) => {
    const response = await login(data);
    if (response.access_token) {
      createCookie(response);
      createCurrentUser(response.username);
      navigate("/");
    }
  };

  return (
    <form onSubmit={handleSubmit((data) => handleLogin(data))}>
      <input {...register("username")} placeholder="Type your username" />
      <input {...register("password")} placeholder="Type your password" />
      <button type="submit">Submit</button>
    </form>
  );
};
