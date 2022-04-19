package com.kucoin.futures.core.rest.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (log.isDebugEnabled()) {
            Request request = chain.request();
            String uriToCall = request.method() + " " + request.url();
            String bodyString = "<EMPTY BODY>";
            if (request.body() != null) {
                Buffer bodyBuffer = new Buffer();
                request.body().writeTo(new Buffer());
                bodyString = "[" + request.body().contentType() + "]::" + bodyBuffer.readString(StandardCharsets.UTF_8);
            }
            log.debug("Request: " + uriToCall + "::" + bodyString);

            long timeStart = System.currentTimeMillis();
            Response response = chain.proceed(request);
            long timeEnd = System.currentTimeMillis();

            bodyString = "<EMPTY BODY>";
            if (response.body() != null) {
                bodyString = "[" + response.body().contentType() + "]::" + new String(response.body().source().peek().readByteArray(), StandardCharsets.UTF_8);
            }
            log.debug("Response: {}ms::[{}]::{}", timeEnd - timeStart, response.code(), bodyString);
            return response;
        }
        return chain.proceed(chain.request());
    }
}
