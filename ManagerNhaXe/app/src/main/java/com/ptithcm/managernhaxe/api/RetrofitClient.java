package com.ptithcm.managernhaxe.api;

import com.ptithcm.managernhaxe.utils.SharedPrefManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // URL Backend Spring Boot
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static ApiService apiService;

    public static ApiService getApiService() {

        if (apiService == null) {

            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client =
                    new OkHttpClient.Builder()

                            // Tự động gắn JWT Token
                            .addInterceptor(chain -> {

                                Request.Builder builder =
                                        chain.request().newBuilder();

                                String token =
                                        SharedPrefManager.getToken();

                                if (token != null && !token.isEmpty()) {
                                    builder.addHeader(
                                            "Authorization",
                                            "Bearer " + token
                                    );
                                }

                                return chain.proceed(builder.build());
                            })

                            .addInterceptor(logging)
                            .build();

            apiService =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            )
                            .build()
                            .create(ApiService.class);
        }

        return apiService;
    }
}