import axios, { AxiosError, AxiosResponse } from "axios";

const onResponse = (response: AxiosResponse): AxiosResponse => {
  return response;
};
const onResponseError = async (
  error: AxiosError
): Promise<AxiosError | any> => {
  console.log("Passouuuuuuuuuuuu")
  return Promise.reject(error);
};
const axiosInstance = axios.create({ baseURL: "http://localhost:8080" });
axiosInstance.interceptors.response.use(onResponse, onResponseError);
export const service = axiosInstance;
