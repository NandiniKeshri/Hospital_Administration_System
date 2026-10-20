
import axios from "axios";

const apiOrigin = (import.meta.env.VITE_API_URL || "http://localhost:8080").replace(/\/+$/, "");

const api = axios.create({
  baseURL: `${apiOrigin}/api`,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
