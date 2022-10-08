import { Link } from "react-router-dom";

export function Header() {
  return (
    <div className="grid grid-cols-3 bg-slate-300 h-20 items-center rounded ">
      <div></div>
      <div className="text-center text-3xl">
        <Link to={"/"}>Welcome to Weather Forecast</Link>
      </div>
      <div className="text-right text-xl pr-5 cursor-pointer">
        <Link to={"/login"}>Login</Link>
      </div>
    </div>
  );
}
