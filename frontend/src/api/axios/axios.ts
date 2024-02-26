import axios, { AxiosInstance } from "axios";

const requestTimeout: number = 10000;
const requestBaseUrl: string = import.meta.env.VITE_API_URL;

const Axios: AxiosInstance = axios.create({
  baseURL: requestBaseUrl,
  timeout: requestTimeout,
});

export default Axios;
