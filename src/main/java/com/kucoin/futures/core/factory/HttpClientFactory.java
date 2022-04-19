/*
 * Copyright 2019 Mek Global Limited
 */
package com.kucoin.futures.core.factory;

import com.kucoin.futures.core.rest.interceptor.FuturesApiKey;
import com.kucoin.futures.core.rest.interceptor.AuthenticationInterceptor;
import com.kucoin.futures.core.rest.interceptor.LoggingInterceptor;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenshiwei on 2019/1/18.
 */
public class HttpClientFactory {

    public static OkHttpClient getPublicClient() {
        return buildHttpClient(new LoggingInterceptor());
    }

    public static OkHttpClient getAuthClient(FuturesApiKey apiKey) {
        return buildHttpClient(new AuthenticationInterceptor(apiKey), new LoggingInterceptor());
    }

    private static OkHttpClient buildHttpClient(Interceptor... interceptors) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(100);
        dispatcher.setMaxRequests(100);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .pingInterval(20, TimeUnit.SECONDS);
        if (interceptors != null) {
            Arrays.stream(interceptors).forEachOrdered(builder::addInterceptor);
        }
        return builder.build();
    }

}
