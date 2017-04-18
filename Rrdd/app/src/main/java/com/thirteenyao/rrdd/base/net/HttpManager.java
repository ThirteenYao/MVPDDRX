package com.thirteenyao.rrdd.base.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.thirteenyao.rrdd.base.MApplication;
import com.thirteenyao.rrdd.base.bean.BaseConfig;
import com.thirteenyao.rrdd.base.net.convertor.HttpLogInterceptor;
import com.thirteenyao.rrdd.base.net.convertor.JsonConverterFactory;
import com.thirteenyao.rrdd.base.util.NetUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by ThirteenYao on 2017/4/11.
 * 网络请求管理类（没有网的时候读缓存）
 */

public class HttpManager implements BaseConfig {


    // 设缓存有效期为1天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;
    // 10秒内直接读缓存
    private static final long CACHE_AGE_SEC = 10;

    private static OkHttpClient instance;
    private static Retrofit retrofit;

    private static HttpManager httpManager;

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    System.err.println("请在application文件中初始化okhttpclient");
                }
            }
        }
        return httpManager;
    }

    // 云端响应头拦截器，用来配置缓存策略
    private static Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetUtils.isNetworkAvailable(MApplication.getContext())) {
                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC)
                        .build();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC)
                        .build();
            } else {
                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .build();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .build();
            }

        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    public static void initCertificates(Context context) {

        httpManager = new HttpManager();


        // OkHttpClient配置是一样的,静态创建一次即可
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MApplication.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

        instance = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(sLoggingInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_NET_OUTDATE, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_NET_OUTDATE, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_NET_OUTDATE, TimeUnit.SECONDS)
                .build();

    }

    /**
     * 防止一个项目里有两个服务器根路径
     *
     * @param service
     * @param baseUrl
     * @param <T>
     * @return
     */
    public <T> T req(final Class<T> service, String baseUrl) {
        retrofit(baseUrl);
        return retrofit.create(service);

    }

    public <T> T req(final Class<T> service) {
        retrofit();
        return retrofit.create(service);

    }

    public Retrofit retrofit(String baseUrl) {
        if (retrofit == null) {
            synchronized (HttpManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(instance)
                            .addConverterFactory(JsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }

        return retrofit;
    }

    public Retrofit retrofit() {
        if (retrofit == null) {
            synchronized (HttpManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(ROOT_SERVER)
                            .client(instance)
                            .addConverterFactory(JsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }

        return retrofit;
    }

}

