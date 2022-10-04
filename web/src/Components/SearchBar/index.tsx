import { useForm } from "react-hook-form";

type FormValues = {
  city: string;
};

interface SearchBarProps {
  onSubmit: (city: string) => Promise<void>
}

export function SearchBar({onSubmit}: SearchBarProps) {
  const {register, handleSubmit, formState: {errors}} = useForm<FormValues>();


  return (
    <div className="flex justify-center">
      <form onSubmit={handleSubmit((data) => onSubmit(data.city))}>
        <label htmlFor="searchBar"></label>
        <input
          {...register("city")}
          className="border-solid border-2 rounded-md"
          type="text"
          placeholder="Type the name of city..."
          required
        />
        <button className="ml-1 border-solid border-2 border-green-500 rounded-md" type="submit">Search</button>
      </form>
    </div>
  );
}
