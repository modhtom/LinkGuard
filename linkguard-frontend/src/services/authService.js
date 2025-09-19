import apiClient from "./api";

export const registerUser = (userData) => {
  return apiClient.post("/api/auth/register", userData);
};

export const loginUser = (credentials) => {
  return apiClient.post("/api/auth/login", credentials);
};
