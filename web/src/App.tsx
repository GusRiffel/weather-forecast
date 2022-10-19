import { Routes, Route } from "react-router-dom";
import { Header } from "./components/Header";
import { Home } from "./views/Home";
import { Login } from "./views/Login";
import { AuthContext } from "./context/AuthContext";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <div className="App">
      <>
        <AuthContext>
          <Header />
          <ToastContainer />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </AuthContext>
      </>
    </div>
  );
}

export default App;
