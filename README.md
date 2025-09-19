# LinkGuard🛡️

*A secure, full-stack application for expanding and analyzing shortened URLs.*

LinkGuard is a secure, internal API service and web interface designed to safely expand and analyze shortened URLs. It provides real-time security insights by disarming potentially malicious links and aggregating anonymized analytics on trending domains.

---

##  Key Features
-   **Full-Stack Application:** A secure REST API backend with a responsive React frontend.
-   **User Authentication:** Secure registration and login system using JWT (JSON Web Tokens).
-   **Secure URL Expansion:** Submits a short URL and robustly resolves the entire redirect chain to find the final destination.
-   **Metadata Scraping:** Fetches the title and meta description from the final URL's HTML content.
-   **Real-time Analytics:** Tracks the most frequently expanded domains using Redis for fast, real-time results.
-   **Fully Containerized:** The entire backend stack (Spring Boot App, PostgreSQL, Redis) is orchestrated with Docker Compose for a one-command setup.
-   **API Documentation:** All endpoints are documented and interactive via Swagger UI.
-   **Continuous Integration:** A GitHub Actions workflow automatically tests and builds the application on every push to the `main` branch.

---

##  Tech Stack

-   **Backend:** Java 21, Spring Boot 3
-   **Frontend:** React 18+, Vite, Zustand, React Router, Axios, CSS Modules
-   **Security:** Spring Security, JWT
-   **Database:** PostgreSQL, Spring Data JPA
-   **Cache & Analytics:** Redis
-   **API Docs:** Springdoc OpenAPI (Swagger)
-   **Build Tool:** Maven
-   **Containerization:** Docker, Docker Compose
-   **CI/CD:** GitHub Actions

---

## Architecture

The application follows a standard layered architecture, fully containerized for portability and ease of deployment.

```mermaid
graph TD
    Client[React Frontend] --> API[Spring Boot API]
    API --> Security[Spring Security/JWT]
    API --> Service[Service Layer]
    Service --> RedisCache[(Redis Cache)]
    Service --> WebClient[WebClient]
    WebClient --> External[External URL]
    Service --> Persistence[Persistence Layer]
    Persistence --> PostgreSQL[(PostgreSQL)]
    Service --> Analytics[Analytics Service]
    Analytics --> RedisSortedSets[(Redis Sorted Sets)]
````

-----

## Getting Started

This project contains both a backend API and a frontend application. Follow the steps below to run them together for local development.

## 1. Running the Backend (API)

### Prerequisites

You must have the following software installed:

  - Git
  - JDK 21 or newer
  - Apache Maven
  - Docker and Docker Compose

### Local Setup

1.  **Clone the repository:**

    ```bash
    git clone [https://github.com/modhtom/LinkGuard.git](https://github.com/modhtom/LinkGuard.git)
    cd LinkGuard/backend
    ```

2.  **Configure Environment Variables:**
    The application uses a `.env` file to manage secrets and environment-specific settings. Navigate to the `/backend` directory and create your own `.env` file by copying the provided template.

    ```bash
    # From within the 'backend' directory
    cp env.example .env
    ```

    Review the `.env` file and customize the variables if needed. See the *Environment Variables* section below for details.

### Running the Backend

1.  **Build the Application:**
    First, compile the project and run all tests using Maven. This creates the necessary `.jar` file.

    ```bash
    mvn clean package
    ```

2.  **Launch with Docker Compose:**
    Use the `--build` flag to ensure Docker creates a new image with your latest code.

    ```bash
    docker-compose up --build
    ```

The API will start and be available on `http://localhost:8080`.

To stop all running containers, press `Ctrl+C` in the terminal and then run:

```bash
docker-compose down
```

## 2. Running the Frontend (UI)

The frontend is a React application powered by Vite.

Open a new terminal window.
Navigate to the frontend directory:

```Bash
cd LinkGuard/linkGuard-frontend
```

Install Dependencies:

```Bash
npm install
```

Run the Development Server:

```Bash
npm run dev
```

The frontend will start, typically on http://localhost:5173, and will open automatically in your browser. It is pre-configured to proxy API requests to the backend, so everything will work together seamlessly.

-----

## API Documentation & Endpoints

Once the application is running, the interactive Swagger UI documentation is available at:

  - [**http://localhost:8080/swagger-ui.html**](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

### Key Endpoints

  - `POST /api/auth/register`

      - **Description:** Registers a new user.
      - **Secured:** No

  - `POST /api/auth/login`

      - **Description:** Authenticates a user and returns a JWT.
      - **Secured:** No

  - `POST /api/v1/expand`

      - **Description:** Submits a short URL for expansion and analysis. Requires a valid JWT.
      - **Secured:** Yes

  - `GET /api/v1/analytics/trending`

      - **Description:** Retrieves the top 10 most frequently expanded domains. Requires a valid JWT.
      - **Secured:** Yes

-----

## Environment Variables

The `.env` file in the `/backend` directory is used to configure the application.

```env
# PostgreSQL Database Configuration
# These variables are used by the 'app' container to connect to the 'db' container.
DB_URL=jdbc:postgresql://db:5432/LinkGuard
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password

# Redis Configuration
# The host 'redis' is the service name defined in docker-compose.yml.
REDIS_HOST=redis
REDIS_PORT=6379

# JWT Secret Key
# A long, random, Base64-encoded string is recommended for production.
JWT_KEY=your_super_secret_jwt_key_here
```

-----

## CI/CD

This project uses GitHub Actions for Continuous Integration. The workflow is defined in `.github/workflows/ci.yml` and performs the following on every push to `main`:

1.  Sets up a JDK 21 environment.
2.  Runs all unit and integration tests using Maven.
3.  Builds the application JAR.
4.  Builds and pushes a new Docker image to Docker Hub.

-----

## Project Structure

```
LinkGuard/
├── LinkGuard-backend/      <-- Spring Boot application source code
├── LinkGuard-frontend/     <-- React application source code
└── README.md               <-- You are here
```

----

## TO BE ADDED

- Rate Limiting: Implement rate limiting on the /expand endpoint using Redis. (e.g., max 10 requests per minute per user).
- Enhanced Security: Add basic malware/phishing scan by checking the domain against a curated blocklist stored in the DB.