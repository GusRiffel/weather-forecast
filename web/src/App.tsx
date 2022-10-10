import { Routes, Route } from "react-router-dom";
import { Header } from "./components/Header";
import { Home } from "./views/Home";
import { Login } from "./views/Login";
import { CookieProvider } from "./utils/CookieManager";

function App() {
  return (
    <div className="App">
      <>
        <CookieProvider>
          <Header />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </CookieProvider>
      </>
    </div>
  );
}

export default App;
