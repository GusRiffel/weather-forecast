import { Routes, Route } from "react-router-dom";
import { Header } from "./components/Header";
import { Home } from "./views/Home";
import { Login } from "./views/Login";
import { AuthProvider } from "./utils/AuthProvider";

function App() {
  return (
    <div className="App">
      <>
        <AuthProvider>
          <Header />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </AuthProvider>
      </>
    </div>
  );
}

export default App;
