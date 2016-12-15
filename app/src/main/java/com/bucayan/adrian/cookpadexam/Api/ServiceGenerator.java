package com.bucayan.adrian.cookpadexam.Api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Adrian Bucayan on 12/15/16.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.instagram.com/";

    private static ServiceGenerator instance;

    private AuthenticationService authenticationService;

    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private ServiceGenerator() {
        // Default Private C'tor
    }

    public static ServiceGenerator getInstance() {
        if (instance == null)
            instance = new ServiceGenerator();

        return instance;
    }

    public AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            retrofit.Retrofit retrofit = getBuilder().build();

            authenticationService = retrofit.create(AuthenticationService.class);

            retrofitLogger(retrofit);
        }

        return authenticationService;
    }

    private retrofit.Retrofit.Builder getBuilder() {
        return new retrofit.Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()));
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JodaModule());

        return objectMapper;
    }

    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setReadTimeout(30, TimeUnit.SECONDS);
        httpClient.setConnectTimeout(30, TimeUnit.SECONDS);

        return httpClient;
    }

    private void retrofitLogger(retrofit.Retrofit retrofit) {
        // Setting the Retrofit logs
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit.client().interceptors().add(logging);
    }

}
