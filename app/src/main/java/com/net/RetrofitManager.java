package com.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {
    public static String baseUrl = "http://api.qmirco.com";
    //    public static final String baseUrl = "http://192.168.31.204:17091";\
    public static String imgBaseUrl = "http://gl.qmirco.com";



    public static Retrofit retrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(new StethoInterceptor())
            .retryOnConnectionFailure(true)
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
////                    if (App.getInstance().getTokenInfo() == null) {
////                        return chain.proceed(originalRequest);
////                    }
////                    Request authorised = originalRequest.newBuilder()
////                            .header("Authorization", App.getInstance().getTokenInfo().getAccesstoken())
////                            .header("Content-Type", "application/json;charset=utf-8")
////                            .addHeader("Connection", "close")
////                            .build();
//
//                    return chain.proceed(authorised);
//                }
//            })
            .connectTimeout(100, TimeUnit.SECONDS)
            .build();

    private static OkHttpClient okHttpClientNoHeader = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(new StethoInterceptor())
            .retryOnConnectionFailure(true)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();


    //常规请求
    public static <T> T builder(Class<T> clazz) {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder.addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .build();
            }
        }
        return retrofit.create(clazz);
    }

    //常规请求  自定义baseUrl
    public static <T> T builderNoheader(Class<T> clazz, String baseUrl) {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder.addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(baseUrl)
                        .client(okHttpClientNoHeader)
                        .build();
            }
        }
        return retrofit.create(clazz);
    }

    //常规请求  自定义baseUrl
    public static <T> T builder(Class<T> clazz, String baseUrl) {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                okHttpClient.newCall(new Request.Builder().url(baseUrl).addHeader("Content-Type","application/json;charset=utf-8").build());

                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder.addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .build();
            }
        }
        return retrofit.create(clazz);
    }

    //下载进度监听
    public static <T> T builder(Class<T> tClass, final FileSubscribe callback) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new FileResponseBody(originalResponse.body(), callback))
                        .build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .build();
        return retrofit.create(tClass);
    }

    //下载进度监听  自定义基础URL
    public static <T> T builder(Class<T> tClass, final FileSubscribe callback, String baseUrl) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                //将ResponseBody转换成我们需要的FileResponseBody
                return response.newBuilder().body(new FileResponseBody(response.body(), callback)).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(tClass);
    }


    public static TreeMap<String, String> getMap() {
        return new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });

    }

}
