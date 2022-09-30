import { useForm } from 'react-hook-form';

type FormValues = {
  username: string;
  email: string;
  password: string;
}

export function LoginForm() {
  const {register, handleSubmit, formState: {errors}} = useForm<FormValues>()

  return (
    <form>
      <input {...register("username")} placeholder="Type your username" />
      <input type="text" name="email" id="email" />
      <input type="text" name="password" id="password" />
    </form>
  )
}