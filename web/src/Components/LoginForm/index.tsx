import { useForm } from "react-hook-form";
import axios from "axios";
import { CookieManager } from "../../utils/CookieManager";
import { Cookies } from "react-cookie";

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
  const { createCookie, getCookie } = CookieManager(new Cookies());
  console.log(getCookie());

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
        .then((res) => createCookie(res.data));
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
