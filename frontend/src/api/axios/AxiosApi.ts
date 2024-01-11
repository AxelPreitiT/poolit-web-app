import { AxiosInstance, AxiosPromise, AxiosRequestConfig } from "axios";
import Axios from "./axios";
import {
  AxiosRequestErrorInterceptor,
  AxiosRequestInterceptor,
} from "./requestInterceptor";
import {
  AxiosResponseErrorInterceptor,
  AxiosResponseInterceptor,
} from "./responseInterceptor";

class AxiosApi {
  private static readonly AXIOS_INSTANCE: AxiosInstance = Axios;

  private static useAxiosInterceptors(): [number, number] {
    return [
      this.AXIOS_INSTANCE.interceptors.request.use(
        AxiosRequestInterceptor,
        AxiosRequestErrorInterceptor
      ),
      this.AXIOS_INSTANCE.interceptors.response.use(
        AxiosResponseInterceptor,
        AxiosResponseErrorInterceptor
      ),
    ];
  }

  private static ejectAxiosInterceptors([requestId, responseId]: [
    number,
    number,
  ]) {
    this.AXIOS_INSTANCE.interceptors.request.eject(requestId);
    this.AXIOS_INSTANCE.interceptors.response.eject(responseId);
  }

  protected static get<Response>(
    url: string,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> {
    const interceptorIds = this.useAxiosInterceptors();
    return this.AXIOS_INSTANCE.get(url, config).finally(() => {
      this.ejectAxiosInterceptors(interceptorIds);
    });
  }

  protected static post<Data, Response>(
    url: string,
    data?: Data,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> {
    const interceptorIds = this.useAxiosInterceptors();
    return this.AXIOS_INSTANCE.post(url, data, config).finally(() => {
      this.ejectAxiosInterceptors(interceptorIds);
    });
  }

  protected static put<Data, Response>(
    url: string,
    data?: Data,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> {
    const interceptorIds = this.useAxiosInterceptors();
    return this.AXIOS_INSTANCE.put(url, data, config).finally(() => {
      this.ejectAxiosInterceptors(interceptorIds);
    });
  }

  protected static delete<Response>(
    url: string,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> {
    const interceptorIds = this.useAxiosInterceptors();
    return this.AXIOS_INSTANCE.delete(url, config).finally(() => {
      this.ejectAxiosInterceptors(interceptorIds);
    });
  }

  protected static patch<Data, Response>(
    url: string,
    data?: Data,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> {
    const interceptorIds = this.useAxiosInterceptors();
    return this.AXIOS_INSTANCE.patch(url, data, config).finally(() => {
      this.ejectAxiosInterceptors(interceptorIds);
    });
  }

  protected static options<Response>(
    url: string,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> {
    const interceptorIds = this.useAxiosInterceptors();
    return this.AXIOS_INSTANCE.options(url, config).finally(() => {
      this.ejectAxiosInterceptors(interceptorIds);
    });
  }
}

export default AxiosApi;
