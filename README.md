# CTracker

This Project is a Panel that tracks corona cases by date, number and visualizes it.

## Features

```
* Track corona cases over time by date and case number.
* Visualize the data using Line Graph.
* Access detailed reports from the case
* Backend and frontend connected through RESTful APIs
* Built using Java, Spring Boot for the backend, and Vite with React for the frontend

```
## Architecture

Client Server based architecture used

```
+-----------------+               +--------------------+               +--------------------+
|     Client      |               |       Server       |                |   MongoDB         |
|    (Frontend)   |  <------->    |    (Backend)       |  <------->     |    Database       |
|  (Vite/React)   |  HTTP API     |   (Spring Boot)    |   Mongo URI    |                   |
|    Port: 5173   |               |   Port: 8080       |                |  Port: 27017      |
|                 |               |                    |                |                   |
+-----------------+               +--------------------+                +--------------------+

```

## Technologies Used

### Backend
- **Spring Boot**: Framework used to build the backend REST APIs.
- **MongoDB**: NoSQL database to store corona case data.
- **Docker**: Containerization for easy deployment and management.

### Frontend
- **Vite**: Fast build tool for the frontend, serving a React-based UI.
- **React**: Frontend library for building the user interface.


## Setup

To get started with CTracker, follow the steps below to set up both the frontend and backend services using Docker

### Prerequisites

Make sure you have the following installed:

- **Docker**: [Install Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Install Docker Compose](https://docs.docker.com/compose/install/)

### Steps 

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. Build and start the Docker containers:
    ```bash
    docker-compose up --build
    ```

    This will start the following services:
    - **Frontend** (Vite app) at `http://localhost:5173`
    - **Backend** (Spring Boot app) at `http://localhost:8080`
    - **MongoDB** at `mongodb://localhost:27017`

3. Access the application:
    - Frontend: `http://localhost:5173`
    - Backend API: `http://localhost:8080`


### Environment Variables

You can reach the env variables from the docker-compose. 
While leaving variables exposed is not a best practice, for this scenario they were initially left visible. 
The variables will later be changed and will be kept as secret variables later