import apiClient from "./api";

export const getTrendingDomains = () => {
  return apiClient.get("/api/v1/analytics/trending");
};
