import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import Cookies from "js-cookie";

const onRequest = async (config: AxiosRequestConfig) => {
  if (config.url?.includes("favorites")) {
    const cookie = Cookies.get("auth");
    if (cookie) {
      config.headers = config.headers ?? {};
      config.headers.Authorization = `Bearer ${
        JSON.parse(decodeURIComponent(cookie)).refresh_token
      }`;
    }
  }
  return config;
};

const onResponse = (response: AxiosResponse): AxiosResponse => {
  return response;
};
const onResponseError = async (
  error: AxiosError
): Promise<AxiosError | any> => {
  console.log(error);
  return Promise.reject(error);
};
const axiosInstance = axios.create({ baseURL: "http://localhost:8080" });
axiosInstance.interceptors.request.use(onRequest, (error) =>
  Promise.reject(error)
);
axiosInstance.interceptors.response.use(onResponse, onResponseError);
export const service = axiosInstance;
