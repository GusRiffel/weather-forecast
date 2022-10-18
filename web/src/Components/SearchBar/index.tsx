import { useForm } from "react-hook-form";
import { SearchWeather } from "../../interfaces";

interface SearchBarProps {
  onSubmit: (city: string) => void;
}

export const SearchBar = ({ onSubmit }: SearchBarProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<SearchWeather>();

  const onCustomSubmit = (data: SearchWeather) => {
    onSubmit(data.city);
    reset();
  };

  return (
    <div className="flex justify-center py-5">
      <form onSubmit={handleSubmit((data) => onCustomSubmit(data))}>
        <label htmlFor="searchBar"></label>
        <input
          {...register("city")}
          className="border-solid border-2 rounded-md"
          type="text"
          placeholder="Search for a place"
          size={50}
          required
        />
        <button
          className="ml-1 border-solid border-2 border-green-500 rounded-md"
          type="submit"
        >
          Search
        </button>
      </form>
    </div>
  );
};
