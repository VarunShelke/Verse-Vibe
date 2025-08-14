# VerseVibe - Find and Play Songs by Their Lyrics

## About
Music discovery is often driven by remembering a song's lyrics rather than its title or artist. With over 5 million songs in the Genius Lyrics dataset, there is an immense potential to create a platform that allows users to search for tracks using any part of the lyrics. This project aims to develop a search engine that leverages this extensive dataset to match lyrics with songs and provide a direct link to stream the song on Spotify. By integrating two popular platforms, Genius and Spotify, this tool will offer users a seamless music discovery experience, empowering them to find and enjoy their favorite songs with ease.

## Project Setup
### Prerequisites
* [Docker](https://docs.docker.com/get-started/get-docker/)
* [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/downloads/#java17)
* [Node.js v23](https://nodejs.org/en/download)

### Setup Elasticsearch and Kibana
- Configure the environment variables in the [.env](./.env) file.
   - Set the passwords for the `elastic` user.
     ```sh
     # Password for the 'elastic' user (at least 6 characters)
     ELASTIC_PASSWORD=your_password
     ```
- Start the services.
   ```sh
   docker-compose up --build -d es01 kibana
   ```
- Open the [Kibana dashboard](https://127.0.0.1:5601) locally.
   If prompted for a password, you can login to the dashboard using the username `elastic` and the password specified in the `ELASTIC_PASSWORD` environment variable.

**Note:  You can stop and start the services without removing the containers by running the commands given below.**
   ```sh
   docker-compose stop
   docker-compose start
   ```

### Backend Setup
* Export the project root folder as an environment variable.
  ```sh
  export PROJECT_ROOT=$(pwd)
  ```
* Export the Spotify API credentials in the .env file.
  ```sh
  SPOTIFY_CLIENT_ID=client_id
  SPOTIFY_CLIENT_SECRET=client_secret
  ```
* Navigate to the mentioned directory.
  ```sh
  cd ${PROJECT_ROOT}/verse-vibe-backend
  ```
* Install the package `dotenv-cli` using `npm`. This will be required to configure the Springboot application with environment variables.
  ```sh
  npm install -g dotenv-cli
  ```
* Install the dependencies for the Springboot application.
  ```sh
  ./mvnw clean install
  ```
* Run the application server.
  ```sh
  dotenv -e ../.env ./mvnw spring-boot:run
  ```
  The application server should be running on port `8080`.

### Frontend Setup
* Navigate to the mentioned directory
  ```sh
  cd ${PROJECT_ROOT}/verse-vibe-frontend
  ```
* Install the dependencies.
  ```sh
  npm install
  ```
* Run the client server.
  ```
  npm run dev
  ```
  The client server should be running on port `3000`. Open your browser and visit [http://localhost:3000](http://localhost:3000).

## Contributors
- Varun Shelke (vps27@pitt.edu)
- Mohammed Misran (mom218@pitt.edu)
- Satvik Tandon (sat362@pitt.edu)
