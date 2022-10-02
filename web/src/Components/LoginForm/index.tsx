import { useForm } from "react-hook-form";
import axios from "axios";

type FormValues = {
  username: string;
  email: string;
  password: string;
};

export function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>();

  async function createUser(data: FormValues) {
    try {
      const user = await axios.post<FormValues>(
        "http://localhost:8080/user/createuser",
        { username: data.username, email: data.email, password: data.password },
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
    <form onSubmit={handleSubmit((data) => createUser(data))}>
      <input {...register("username")} placeholder="Type your username" />
      <input {...register("email")} placeholder="Type your email" />
      <input {...register("password")} placeholder="Type your password" />
      <button type="submit">Submit</button>
    </form>
  );
}
