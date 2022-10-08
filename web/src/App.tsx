import { Routes, Route } from "react-router-dom";
import { Header } from "./components/Header";
import { Home } from "./views/Home";
import { Login } from "./views/Login";

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </div>
  );
}

export default App;
