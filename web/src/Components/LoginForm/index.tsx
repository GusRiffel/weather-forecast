import { useContext } from "react";
import { useForm } from "react-hook-form";
import { LoginFormValues } from "../../interfaces";
import { useNavigate } from "react-router-dom";

import { UserContext } from "../../context/AuthContext";
import { useUser } from "../../hooks/useUser";
import { createCookie } from "../../utils/cookieHelper";

import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const schema = yup.object().shape({
  username: yup.string().required("Username can't be empty"),
  password: yup.string().required("Password can't be empty"),
});

export const LoginForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: yupResolver(schema),
  });
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
    <>
      <div className="flex flex-col items-center border border-solid border-black w-[30%] rounded-md mx-auto">
        <div className="text-3xl py-3">
          <h1>Login</h1>
        </div>
        <form
          className="flex flex-col gap-3"
          onSubmit={handleSubmit((data) => handleLogin(data))}
        >
          <input
            className="border rounded-md"
            {...register("username")}
            placeholder="Type your username"
          />
          <p className="text-red-500 font-semibold">
            {errors.username?.message}
          </p>
          <input
            className="border rounded-md"
            {...register("password")}
            type="password"
            placeholder="Type your password"
          />
          <p className="text-red-500 font-semibold">
            {errors.password?.message}
          </p>
          <button className="border rounded-md" type="submit">
            Submit
          </button>
        </form>
        <div className="py-3">
          <p>Not registered yet? Sign up now!</p>
        </div>
      </div>
    </>
  );
};
