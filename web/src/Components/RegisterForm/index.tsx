import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { RegisterFormValues } from "../../interfaces";

import { useUser } from "../../hooks/useUser";

import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const schema = yup.object().shape({
  username: yup.string().required("Username can't be empty"),
  email: yup.string().email().required("Email can't be empty"),
  password: yup
    .string()
    .min(6, "Password must have 6 characters minimum")
    .required("Password can't be empty"),
});

export const RegisterForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterFormValues>({
    resolver: yupResolver(schema),
  });
  const { create } = useUser();
  const navigate = useNavigate();

  const handleRegister = async (data: RegisterFormValues) => {
    const response = await create(data);
    if (response === 201) {
      toast.success("Account created!", {
        position: toast.POSITION.TOP_CENTER,
      });
      navigate("/login");
    }
  };

  return (
    <>
      <section className="flex flex-col items-center border border-solid border-black w-[30%] rounded-md mx-auto">
        <div className="text-3xl py-3">
          <h1>Register</h1>
        </div>
        <form
          className="flex flex-col gap-3"
          onSubmit={handleSubmit((data) => handleRegister(data))}
        >
          <input
            className="border rounded-md"
            {...register("username")}
            placeholder="Type your username"
          />
          <span className="text-red-500 font-semibold">
            {errors.username?.message}
          </span>
          <input
            className="border rounded-md"
            {...register("email")}
            placeholder="Type your e-mail"
          />
          <span className="text-red-500 font-semibold">
            {errors.email?.message}
          </span>
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
          <Link to="/login">Registered already? Log in now!</Link>
        </div>
      </section>
    </>
  );
};
