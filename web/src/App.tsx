import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'
import axios from 'axios';

function App() {
  const [count, setCount] = useState(0)
  const [weather, setWeather] = useState();

  useEffect(() => {
    axios.get("http://localhost:8080/weather/gaspar").then((res) => setWeather(res.data))
  }, [])


  return (
    <div className="App">
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src="/vite.svg" className="logo" alt="Vite logo" />
        </a>
        <a href="https://reactjs.org" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
      </div>
      <>
      {weather &&
      Object.entries(weather).map(([key, value]) => { return <>{key}</>})
      }
      </>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </div>
  )
}

export default App
