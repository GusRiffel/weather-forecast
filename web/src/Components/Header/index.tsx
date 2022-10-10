import { Link } from "react-router-dom";
import { useContext } from "react";
import { CookieContext } from "../../utils/AuthProvider";

export function Header() {
  const cookieContext = useContext(CookieContext);

  function handleLogOut() {
    cookieContext?.removeCookie();
  }

  return (
    <div className="grid grid-cols-3 bg-slate-300 h-20 items-center rounded ">
      <div></div>
      <div className="text-center text-3xl">
        <Link to={"/"}>Welcome to Weather Forecast</Link>
      </div>
      <div className="text-right text-xl pr-5 cursor-pointer">
        <div>
          {cookieContext?.currentUser ? (
            <div>
              <p>Welcome {cookieContext?.currentUser}</p>
              <p onClick={() => handleLogOut()}>Log out</p>
            </div>
          ) : (
            <Link to={"/login"}>Login</Link>
          )}
        </div>
      </div>
    </div>
  );
}
