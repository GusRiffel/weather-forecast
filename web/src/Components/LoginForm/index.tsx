import { useContext } from "react";
import { useForm } from "react-hook-form";

import axios from "axios";

import { CookieContext } from "../../utils/AuthProvider";

type FormValues = {
  username: string;
  password: string;
};

export function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>();
  
  const cookieContext = useContext(CookieContext);
  
  async function loginUser(data: FormValues) {
    try {
      await axios
        .post(
          "http://localhost:8080/login",
          { username: data.username, password: data.password },
          {
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
          }
        )
        .then((res) => cookieContext?.createCookie(res.data));
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log("error message: ", error.message);
        return error.message;
      }
    }
  }

  return (
    <form onSubmit={handleSubmit((data) => loginUser(data))}>
      <input {...register("username")} placeholder="Type your username" />
      <input {...register("password")} placeholder="Type your password" />
      <button type="submit">Submit</button>
    </form>
  );
}
