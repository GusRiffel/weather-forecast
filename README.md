# 🌤️ Weather Forecast App

A full-stack weather forecast application that allows users to check current and upcoming weather conditions for any city worldwide. Built with Spring Boot, React, and Tailwind CSS, and powered by the OpenWeatherMap API.

## 🛠️ Tech Stack

**Frontend:**
- React
- Tailwind CSS
- Axios

**Backend:**
- Java
- Spring Boot
- Spring Boot Security
- RESTful API
- OpenWeatherMap API
- JWT
- Swagger
- PostgreSQL

**Tools & Deployment:**
- Git & GitHub
- IntelliJ IDEA


## 📦 Features

- 🌍 Search weather by city name
- 📊 Current temperature, humidity, wind speed
- 🗓️ 5-day forecast with dynamic icons
- 🔁 Real-time API integration
- 🎨 Fully responsive UI using Tailwind CSS


## 🧪 Getting Started Locally

### Prerequisites

- Java 17+
- Node.js & npm
- OpenWeatherMap API key

### Backend Setup

``bash
cd backend
./mvnw spring-boot:run
define access_secret and refresh_secret for JWT
past this code to fix swagger api bean error **_spring.mvc.pathmatch.matching-strategy=ant_path_matcher_**

### Frontend Setup
cd frontend
npm install
npm run dev 

## 🔑 API Key Configuration
Create a .env file in the frontend root:
REACT_APP_WEATHER_API_KEY=your_api_key_here


