import axios, {InternalAxiosRequestConfig} from "axios";
import {plainToInstance} from "class-transformer";
import {InitResponse} from "../stores/root.store";
import {isBeforeNow} from "../utils/common";
import {AccessToken} from "../stores/auth.store";
import {ACCESS_TOKEN, RECAPTCHA_KEY} from "../utils/callback.injector";

let recaptcha: any;

try {
    recaptcha = grecaptcha;
} catch (e) {
    if (e instanceof ReferenceError) {
        console.error("Recaptcha is not defined.")
    }
}

// TODO: one base axios instance
export const BASE = '/api/v1';
export const AUTH_NOT_REQUIRED = ["/api/init", "/apis"];

export const init = async () => {
    const res = await axios.get<InitResponse>('/api/init');
    return plainToInstance(InitResponse, res.data as InitResponse);
}

const log = (error: any) => {
    if (axios.isAxiosError(error)) {
        console.error(error.response?.data.message);
    } else {
        console.error(error);
    }
}

const getHeaders = (token: string, hasContentType: boolean) => {
    const res = {
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
    };
    if (!hasContentType) {
        (res as any)['Content-Type'] = 'application/json';
    }
    return res;
};

export const getFormDataHeaders = () => {
    return {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };
}

const refreshAccessTokenAxiosInstance = axios.create({
    baseURL: "/api",
});

axios.interceptors.request.use(async request => {
    /**
     * Every request, besides below ones is access token protected
     */
    if (!AUTH_NOT_REQUIRED.some(str => request.url && request.url.endsWith(str))) {
        if (isBeforeNow(ACCESS_TOKEN.callback.exp)) {
            const res = await refreshAccessTokenAxiosInstance.get<AccessToken>('/token/refresh');
            ACCESS_TOKEN.callback = () => res.data as AccessToken;
        }
        const headers = request.headers;
        headers.set(getHeaders(ACCESS_TOKEN.callback.value, headers.has('Content-Type')));
    }

    /**
     * Recaptcha logic
     */
    const verifyRecaptcha = (request: InternalAxiosRequestConfig) => {
        return new Promise((res, rej) => {
            recaptcha.ready(function() {
                recaptcha.execute(RECAPTCHA_KEY.callback, { action: request.url }).then(function(token: unknown) {
                    return res(token);
                });
            });
        })
    }

    if (request.method?.toLowerCase() === 'post') {
        if (recaptcha) {
            const token = await verifyRecaptcha(request);
            request.params = { ...request.params, "g-recaptcha-response": token };
        } else {
            console.warn("Recaptcha not defined.");
        }
    }

    return request;
})

axios.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    log(error);
    return Promise.reject(error);
});