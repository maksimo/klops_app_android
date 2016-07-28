package ru.klops.klops.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.utils.Constants;

public class RetrofitServiceGenerator {
    static KlopsApplication app = KlopsApplication.getINSTANCE();

    public static String baseURL = app.loadBaseURL();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.connectTimeout(50, TimeUnit.SECONDS).readTimeout(50, TimeUnit.SECONDS).writeTimeout(50, TimeUnit.SECONDS).build()).build();
        return retrofit.create(serviceClass);
    }
}
