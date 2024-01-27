import {
  AxiosInstance,
  AxiosPromise,
  AxiosRequestConfig,
  AxiosResponse,
} from "axios";
import Axios from "./axios";
import {
  AxiosRequestErrorInterceptor,
  AxiosRequestInterceptor,
} from "./requestInterceptor";
import {
  AxiosResponseErrorInterceptor,
  AxiosResponseInterceptor,
} from "./responseInterceptor";
import PaginationModel from "@/models/PaginationModel";

class AxiosApi {
  private static readonly AXIOS_INSTANCE: AxiosInstance = Axios;

  private static executeRequest = <Response>(
    request: (axios: AxiosInstance) => AxiosPromise<Response>
  ): AxiosPromise<Response> => {
    const requestId = this.AXIOS_INSTANCE.interceptors.request.use(
      AxiosRequestInterceptor,
      AxiosRequestErrorInterceptor
    );
    const responseId = this.AXIOS_INSTANCE.interceptors.response.use(
      AxiosResponseInterceptor,
      AxiosResponseErrorInterceptor
    );
    return request(this.AXIOS_INSTANCE).finally(() => {
      if (requestId) {
        this.AXIOS_INSTANCE.interceptors.request.eject(requestId);
      }
      if (responseId) {
        this.AXIOS_INSTANCE.interceptors.response.eject(responseId);
      }
    });
  };

  protected static getPaginationModel = <Model>(
    response: AxiosResponse<Model[]>
  ): AxiosResponse<PaginationModel<Model>> => {
    const first = response.headers.link?.match(/<([^>]*)>; rel="first"/)?.[1];
    const prev = response.headers.link?.match(/<([^>]*)>; rel="prev"/)?.[1];
    const next = response.headers.link?.match(/<([^>]*)>; rel="next"/)?.[1];
    const last = response.headers.link?.match(/<([^>]*)>; rel="last"/)?.[1];
    const totalPages = response.headers["x-total-pages"];
    return {
      ...response,
      data: {
        first: first,
        prev: prev,
        next: next,
        last: last,
        totalPages: totalPages,
        data: response.data,
      },
    };
  };

  protected static get = <Response>(
    url: string,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> =>
    this.executeRequest<Response>((axios: AxiosInstance) =>
      axios.get(url, config)
    );

  protected static post = <Data, Response>(
    url: string,
    data: Data,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> =>
    this.executeRequest<Response>((axios: AxiosInstance) =>
      axios.post(url, data, config)
    );

  protected static put = <Data, Response>(
    url: string,
    data: Data,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> =>
    this.executeRequest<Response>((axios: AxiosInstance) =>
      axios.put(url, data, config)
    );

  protected static delete = <Response>(
    url: string,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> =>
    this.executeRequest<Response>((axios: AxiosInstance) =>
      axios.delete(url, config)
    );

  protected static patch = <Data, Response>(
    url: string,
    data: Data,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> =>
    this.executeRequest<Response>((axios: AxiosInstance) =>
      axios.patch(url, data, config)
    );

  protected static options = <Response>(
    url: string,
    config?: AxiosRequestConfig
  ): AxiosPromise<Response> =>
    this.executeRequest<Response>((axios: AxiosInstance) =>
      axios.options(url, config)
    );
}

export default AxiosApi;
