import { useContext } from "react";
import { Link } from "react-router-dom";
import { UserContext } from "../../context/AuthContext";
import { removeCookie } from "../../utils/cookieHelper";

export const Header = () => {
  const { currentUser, deleteCurrentUser } = useContext(UserContext);

  const handleLogOut = () => {
    removeCookie();
    deleteCurrentUser();
  };

  return (
    <div className="grid grid-cols-3 bg-slate-300 h-20 items-center rounded ">
      <div></div>
      <div className="text-center text-3xl">
        <Link to={"/"}>Welcome to Weather Forecast</Link>
      </div>
      <div className="text-right text-xl pr-5 cursor-pointer">
        <div>
          {currentUser ? (
            <div>
              <p>Welcome {currentUser}</p>
              <p onClick={() => handleLogOut()}>Log out</p>
            </div>
          ) : (
            <Link to={"/login"}>Login</Link>
          )}
        </div>
      </div>
    </div>
  );
};
