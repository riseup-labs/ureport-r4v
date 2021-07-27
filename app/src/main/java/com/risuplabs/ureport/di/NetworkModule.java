package com.risuplabs.ureport.di;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

import com.risuplabs.ureport.network.data.ApiConstants;
import com.risuplabs.ureport.utils.pref_manager.PrefKeys;
import com.risuplabs.ureport.utils.pref_manager.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zerobranch.androidremotedebugger.logging.NetLoggingInterceptor;

@Module
public class NetworkModule {

    private static final String PRAGMA_HEADER = "pragma";
    private static final String CACHE_CONTROL_HEADER = "Cache-Control";

    @Singleton
    @Provides
    File getFile(Context context) {
        File file = new File(context.getFilesDir(), "cache_dir");
        if (!file.exists())
            file.mkdir();
        return file;
    }

    @Singleton
    @Provides
    Cache getCache(File cache) {
        return new Cache(cache, 10 * 1000 * 1000);
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

//    @Singleton
//    @Provides
//    OkHttpClient provideDebugClientInterceptor() {
//        return new OkHttpClient.Builder().addInterceptor(new NetLoggingInterceptor()).build();
//    }


    @Singleton
    @Provides
    Interceptor getCacheInterceptor(Context context) {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl;

            if (isConnected(context)) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxAge(7, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(PRAGMA_HEADER)
                    .removeHeader(CACHE_CONTROL_HEADER)
                    .addHeader(CACHE_CONTROL_HEADER, cacheControl.toString())
                    .build();
        };
    }

    private boolean isConnected(Context context) {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("", e.toString());
        }

        return false;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient(Cache cache,
                                      HttpLoggingInterceptor logging,
                                      Interceptor cacheInterceptor
    ) {
        return new OkHttpClient.Builder()
//                .addInterceptor(logging)
                .addInterceptor(cacheInterceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder()
                                .addHeader("Authorization", "Token aa74666cb91b76ded40507dc4c02caa2295c65fc");

                        request = builder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new NetLoggingInterceptor())
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    @Named("story")
    Retrofit provideStoryRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.SR_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("surveyor")
    Retrofit provideSurveyorRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.SURVEYOR_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }




}

