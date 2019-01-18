package com.example.handsomelibrary.retrofit;

import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.gson.GsonClass;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.handsomelibrary.api.ApiService.BASE_URL;

/**
 * RetrofitClient工具类
 * Created by Stefan on 2018/4/23.
 */

public class RetrofitClient {
    private static RetrofitClient instance;

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    public RetrofitClient() {

        mOkHttpBuilder = HttpClient.getInstance().getBuilder();
//        OkHttpClient build = HttpClient.getInstance().getBuilder()
//                .addInterceptor(new tokenInterceptor())
//                .build();

        mRetrofitBuilder = new Retrofit.Builder()
//                .client(build)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonClass.buildGson()))
                .baseUrl(BASE_URL);
    }

    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }

        }
        return instance;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit() {
        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }

//    private class tokenInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            return null;
//        }
//    }
}
