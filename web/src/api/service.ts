import axios, { AxiosResponse } from "axios";

export const service = axios.create({ baseURL: "http://localhost:8080" });
const responseBody = (response: AxiosResponse) => response.data;
