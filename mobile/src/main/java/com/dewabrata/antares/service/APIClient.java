package com.dewabrata.antares.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by user on 1/10/2018.
 */

public class APIClient {


    public static String m2m = "ac562cdbf7891f3f:20a076d283e8506f";
   public static String BASE_URL ="http://platform.antares.id:8080/~/antares-cse/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("X-M2M-Origin",APIClient.m2m)
                                                                    .addHeader("Content-Type","application/json;ty=4")
                                                                     .addHeader("Accept","application/json").build();
                return chain.proceed(newRequest);
            }
        };


      //  HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
       // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


       // OkHttpClient client = new OkHttpClient.Builder().build();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        gb.serializeNulls();
       // gb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gb.create();


        retrofit = new Retrofit.Builder()
                .baseUrl(APIClient.BASE_URL)

                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();



        return retrofit;
    }

}
