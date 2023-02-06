# Weather-forecast
An app to check the weather of cities around the world.

# Description
The idea was to create a full stack project using modern framework/libs.  
The project has the weather data retrieve from a free api provided by https://openweathermap.org/

# What was used to create this app?
The web page of the app counts with modern react with typescript with a login system managed with cookies. Yup was used to validate data and tailwind for the css.  
  
Front-end stacks:
- React + Typescript
- Axios
- Js-cookie
- react-toastify
- yup
- tailwind
  
The server of the app was developed using springboot including springboot security. To add on security JWT was also implemented to deal with user requests.  
PostgreSQL is the database being used to stored data and H2 is used for tests.  

Back-end stacks:
- Springboot
- Springboot security
- Swagger
- Lombok
- JWT
- Model-Mapper
- PostgreSQL  

# How to Install and Run
1. Clone this repository.  
2. Go to web folder and open it
3. install the dependencies and run the project using the commands:
- npm install
- npm run dev  
4. Now on the server folder make sure all the depencies are loaded, if not manually download with maven.
5. Add the following information to the application properties file:
- apikey (the key provided by openweathermap to make requests)  
- set up a DB connection (this project is using PostgreSQL)
- define access_secret and refresh_secret for JWT  
- past this code to fix swagger api bean error **_spring.mvc.pathmatch.matching-strategy=ant_path_matcher_**
