import { useForm } from "react-hook-form";
import axios from "axios";

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

  async function loginUser(data: FormValues) {
    try {
      const user = await axios.post<FormValues>(
        "http://localhost:8080/login",
        { username: data.username, password: data.password },
        {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        },
      );

      console.log(JSON.stringify(user));
      return user;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log('error message: ', error.message);
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
