import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { toast } from "react-toastify";
import { getCookie } from "../utils/cookieHelper";

const onRequest = (config: AxiosRequestConfig): AxiosRequestConfig => {
  if (config.url?.includes("favorites")) {
    const cookie = getCookie();
    if (cookie) {
      config.headers = config.headers ?? {};
      config.headers.Authorization = `Bearer ${cookie.access_token}`;
    }
  }
  return config;
};

const onRequestError = (error: AxiosError): Promise<AxiosError> => {
  return Promise.reject(error);
};

const onResponse = (response: AxiosResponse): AxiosResponse => {
  return response;
};

const onResponseError = async (
  error: AxiosError
): Promise<AxiosError | any> => {
  console.log(error);
  toast.error(
    (error.response as any)?.data.message ||
      (error.response as any)?.data.details,
    {
      position: toast.POSITION.TOP_CENTER,
    }
  );
  return Promise.reject(error);
};

const axiosInstance = axios.create({ baseURL: "http://localhost:8080" });
axiosInstance.interceptors.request.use(onRequest, onRequestError);
axiosInstance.interceptors.response.use(onResponse, onResponseError);

export const service = axiosInstance;
