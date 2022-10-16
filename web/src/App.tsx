import { Routes, Route } from "react-router-dom";
import { Header } from "./components/Header";
import { Home } from "./views/Home";
import { Login } from "./views/Login";
import { AuthContext } from "./context/AuthContext";

function App() {
  return (
    <div className="App">
      <>
        <AuthContext>
          <Header />
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
