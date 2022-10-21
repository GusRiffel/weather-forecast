import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { getCookie } from "../utils/cookieHelper";
import { toast } from "react-toastify";
import { string } from "yup";

const onRequest = (config: AxiosRequestConfig): AxiosRequestConfig => {
  if (config.url?.includes("favorites")) {
    const cookie = getCookie();
    if (cookie) {
      config.headers = config.headers ?? {};
      config.headers.Authorization = `Bearer ${cookie.refresh_token}`;
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
  toast.error((error.response as any)?.data.message, {
    position: toast.POSITION.TOP_CENTER,
  });
  return Promise.reject(error);
};

const axiosInstance = axios.create({ baseURL: "http://localhost:8080" });
axiosInstance.interceptors.request.use(onRequest, onRequestError);
axiosInstance.interceptors.response.use(onResponse, onResponseError);

export const service = axiosInstance;
