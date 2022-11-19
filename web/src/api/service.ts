import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { toast } from "react-toastify";
import { useUser } from "../hooks/useUser";
import { getCookie, createCookie } from "../utils/cookieHelper";

const axiosInstance = axios.create({ baseURL: "http://localhost:8080" });

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
  const cookie = getCookie();
  const { refreshAccessToken } = useUser();

  if ((error.response as any)?.data.error.includes("expired")) {
    const {access_token: newAccessToken} = await refreshAccessToken(
      cookie!.username
    );
    createCookie({username: cookie!.username, access_token: newAccessToken});

    return axiosInstance.request(error.config);
  }
  toast.error(
    (error.response as any)?.data.message ||
      (error.response as any)?.data.details,
    {
      position: toast.POSITION.TOP_CENTER,
    }
  );
  return Promise.reject(error);
};


axiosInstance.interceptors.request.use(onRequest, onRequestError);
axiosInstance.interceptors.response.use(onResponse, onResponseError);

export const service = axiosInstance;
