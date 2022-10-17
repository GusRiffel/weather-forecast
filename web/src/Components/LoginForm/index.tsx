import { useContext } from "react";
import { useForm } from "react-hook-form";
import { LoginFormValues } from "../../interfaces";

import { CookieContext } from "../../context/AuthContext";
import { useUser } from "../../hooks/useUser";

export function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>();
  const { login } = useUser();
  const { createCookie } = useContext(CookieContext);

  const handleLogin = async (data: LoginFormValues) => {
    const response = await login(data);
    if (response.access_token) {
      createCookie(response);
    }
  };

  return (
    <form onSubmit={handleSubmit((data) => handleLogin(data))}>
      <input {...register("username")} placeholder="Type your username" />
      <input {...register("password")} placeholder="Type your password" />
      <button type="submit">Submit</button>
    </form>
  );
}
