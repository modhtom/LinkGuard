import apiClient from "./api";

export const expandUrl = (urlToExpand) => {
  return apiClient.post("/api/urls/v1/expand", { url: urlToExpand });
};
