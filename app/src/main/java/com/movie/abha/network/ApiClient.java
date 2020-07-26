package com.movie.abha.network;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movie.user.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit mRetrofit,mRetrofitgoogle, mRetrofitPayment;

    public static Retrofit retrofit(final Context context)
    {
        if(mRetrofit==null)
        {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if(BuildConfig.DEBUG)
            {
                //log information interceptor
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //Set the Debug Log mode
                builder.addInterceptor(loggingInterceptor);
            }


            builder.addInterceptor(new Interceptor() {
                      @Override
                      public Response intercept(Chain chain) throws IOException {
                          Request original = chain.request();

                          Request request = original.newBuilder()
                                      .header("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                                      .header("x-rapidapi-key", "7deccf6586mshf6de8dd5331704ap111b5cjsncb8ec482b5a2")
                                  .method(original.method(), original.body())
                                  .build();

                          return chain.proceed(request);
                      }
                  });

            OkHttpClient okHttpClient =  builder
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
