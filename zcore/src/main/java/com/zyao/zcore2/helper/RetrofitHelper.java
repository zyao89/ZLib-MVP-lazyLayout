/**
 * Title: RetrofitHelper.java
 * Package: com.zyao.zcore2.helper
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.helper;

import com.zyao.config.Configs;
import com.zyao.zutils.Z;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class: RetrofitHelper
 * Description: Retrofit帮助类
 * Author: Zyao89
 * Time: 2016/9/21 14:11
 */
public class RetrofitHelper
{
    private static volatile RetrofitHelper mInstance;
    private final RxJavaCallAdapterFactory mRxJavaCallAdapterFactory;
    private final GsonConverterFactory mGsonConverterFactory;
    private OkHttpClient mOkHttpClient;

    private RetrofitHelper ()
    {
        initOkHttpClient();
        mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
        mGsonConverterFactory = GsonConverterFactory.create();
    }

    public static RetrofitHelper getInstance ()
    {
        if (null == mInstance)
        {
            synchronized (RetrofitHelper.class)
            {
                if (null == mInstance)
                {
                    mInstance = new RetrofitHelper();
                }
            }
        }

        return mInstance;
    }

    /**
     * 获取一个新的Retrofit对象
     *
     * @param url
     *
     * @return
     */
    public Retrofit getNewRetrofit (String url)
    {
        return new Retrofit.Builder().baseUrl(url).client(mOkHttpClient).addCallAdapterFactory(mRxJavaCallAdapterFactory).addConverterFactory(mGsonConverterFactory).build();
    }

    /**
     * 初始化mOkHttpClient
     */
    private void initOkHttpClient ()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (Z.isDebug())
        {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }

        //缓存机制
        setCache(builder);

        //公共参数
        setParameter(builder);

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

    /**
     * 缓存机制 (无网络时，也能显示数据)
     *
     * @param builder
     */
    private void setCache (OkHttpClient.Builder builder)
    {
        //无网络时，也能显示数据
        File cacheFile = new File(Configs.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();
                if (!Z.utils().net().isConnected())
                {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (Z.utils().net().isConnected())
                {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                }
                else
                {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).removeHeader("nyn").build();
                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
    }

    /**
     * 公共参数 （可能接口有某些参数是公共的，不可能一个个接口都去加吧）
     *
     * @param builder
     */
    private void setParameter (OkHttpClient.Builder builder)
    {
        Interceptor addQueryParameterInterceptor = new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request originalRequest = chain.request();
                Request request;
                String method = originalRequest.method();
                Headers headers = originalRequest.headers();
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("platform", "android").addQueryParameter("version", "1.0.0").build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        //公共参数
        builder.addInterceptor(addQueryParameterInterceptor);
    }

    /**
     * 设置头
     *
     * @param builder (有的接口可能对请求头要设置)
     */
    private void setHeader (OkHttpClient.Builder builder)
    {
        Interceptor headerInterceptor = new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder().header("AppType", "TPOS").header("Content-Type", "application/json").header("Accept", "application/json").method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        //设置头
        builder.addInterceptor(headerInterceptor);
    }
}
