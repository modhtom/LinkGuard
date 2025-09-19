\# LinkGuard Frontend



Frontend for the LinkGuard API, built as a modern, responsive Single-Page Application (SPA) using React.



---



\## Features



\-   \*\*JWT-Powered Authentication:\*\* Secure user registration and login flows with persistent sessions via `localStorage`.

\-   \*\*Protected Routes:\*\* Client-side routing that protects dashboard and analytics pages from unauthorized access.

\-   \*\*Dynamic URL Expansion:\*\* A clean interface for submitting shortened URLs and viewing the resolved metadata in real-time.

\-   \*\*Live Analytics:\*\* A dashboard component that visualizes the top trending domains by fetching data from the analytics API.

\-   \*\*Modern Tooling:\*\* Fast and efficient development environment powered by Vite.

\-   \*\*Centralized State Management:\*\* Predictable state management using Zustand for a lightweight and simple developer experience.



---



\## Tech Stack



\-   \*\*Framework:\*\* React 18+

\-   \*\*Development Server:\*\* Vite

\-   \*\*API Communication:\*\* Axios

\-   \*\*Routing:\*\* React Router

\-   \*\*State Management:\*\* Zustand

\-   \*\*Styling:\*\* CSS Modules



---



\## Getting Started



Follow these instructions to get the frontend development server running on your local machine.



\### Prerequisites



\-   \[Node.js](https://nodejs.org/) (version 18.x or newer recommended) and npm

\-   The LinkGuard backend server must be running. Follow the instructions in the main project `README.md` to start it.



\### Local Development



1\.  \*\*Navigate to the Frontend Directory:\*\*

&nbsp;   From the project's root `LinkGuard/` folder, navigate into the frontend directory:

&nbsp;   ```bash

&nbsp;   cd frontend

&nbsp;   ```



2\.  \*\*Install Dependencies:\*\*

&nbsp;   Install all the necessary npm packages.

&nbsp;   ```bash

&nbsp;   npm install

&nbsp;   ```



3\.  \*\*Run the Development Server:\*\*

&nbsp;   This command will start the Vite development server, typically on `http://localhost:5173`.

&nbsp;   ```bash

&nbsp;   npm run dev

&nbsp;   ```



The application will open in your browser, and you can now interact with the UI. The Vite server is configured with a proxy, so all API calls will be automatically forwarded to the backend running on `localhost:8080`, avoiding any CORS issues.



---



\## Project Structure



The `src` directory is organized to separate concerns, making the codebase clean and maintainable.

```Bash

/src

├── components/     # Reusable UI components (Navbar, ProtectedRoute, etc.)

├── pages/          # Top-level page components (LoginPage, DashboardPage, etc.)

├── services/       # API call logic (authService, urlService, etc.)

├── store/          # Zustand global state management (userStore)

└── main.jsx        # Main application entry point

```

