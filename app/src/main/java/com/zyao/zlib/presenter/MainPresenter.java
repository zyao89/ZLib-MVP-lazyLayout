/**
 * Title: MainPresenter.java
 * Package: com.zyao.zlib
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zlib.presenter;

import android.os.Bundle;

import com.zyao.zcore2.base.BaseComponentPresenter;
import com.zyao.zlib.contract.MainContract;
import com.zyao.zlib.serves.RequestServes;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Class: MainPresenter
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/17 2:43
 */
public class MainPresenter extends BaseComponentPresenter<MainContract.IViewHandler> implements MainContract.IPresenter
{
    @Inject
    public MainPresenter ()
    {
    }

    @Override
    protected void initData (Bundle savedInstanceState)
    {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());//用于返回Rxjava调用
        final Retrofit build = builder.baseUrl("http://tieba.baidu.com").build();
        RequestServes requestServes = build.create(RequestServes.class);
        Observable<ResponseBody> observable = requestServes.getContext();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseBody>()
        {
            @Override
            public void onCompleted ()
            {
                System.out.println("zzzzzzzz:  onCompleted");
            }

            @Override
            public void onError (Throwable e)
            {
                System.out.println("zzzzzzzz:  onError: " + e);
            }

            @Override
            public void onNext (ResponseBody responseBody)
            {
                System.out.println("zzzzzzzz   Thread Name: " + Thread.currentThread().getName());
                System.out.println("zzzzzzzz:  onNext: START");
                try
                {
                    System.out.println("zzzzzzzz:  onNext: " + responseBody.bytes().length);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                System.out.println("zzzzzzzz:  onNext: END");
            }
        });
    }

    @Override
    protected void initListener ()
    {

    }

    @Override
    public void getName ()
    {

    }
}
