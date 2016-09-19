/**
 * Title: MainActivityViewHandler.java
 * Package: com.zyao.zlib
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/13
 */
package com.zyao.zlib.view;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zyao.views.zloading.LoadingView;
import com.zyao.zcore2.base.BaseComponentActivityViewHandler;
import com.zyao.zlib.R;
import com.zyao.zlib.contract.MainContract;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Class: MainActivityViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/13 16:06
 */
public class MainActivityViewHandler extends BaseComponentActivityViewHandler<RelativeLayout> implements MainContract.IViewHandler
{
    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.loading)
    LoadingView mZLoadingView;
    //    @BindView(R.id.view_search)
    //    MaterialSearchView mSearchView;

    @Inject
    public MainActivityViewHandler (LayoutInflater inflater)
    {
        System.out.println("LayoutInflater: " + inflater);
    }

    @Override
    public int getResourceId ()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews ()
    {
        setToolbar(mToolbar, "haha");
    }

    @Override
    protected void initListeners ()
    {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                setDayNightMode(!isNightMode());
//                mZLoadingView.setVisibility(mZLoadingView.isShown()?View.GONE:View.VISIBLE);
            }
        });
    }

    @Override
    protected void initDefaultData ()
    {
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>()
//        {
//            @Override
//            public void call (Subscriber<? super String> subscriber)
//            {
//                subscriber.onNext("Hello World!!!");
//                subscriber.onCompleted();
//            }
//        });
//
//        Subscriber<String> subscriber = new Subscriber<String>()
//        {
//
//            @Override
//            public void onCompleted ()
//            {
//                System.out.println("zzzzzzzz:  onCompleted");
//            }
//
//            @Override
//            public void onError (Throwable e)
//            {
//                System.out.println("zzzzzzzz:  onError: " + e);
//            }
//
//            @Override
//            public void onNext (String s)
//            {
//                System.out.println("zzzzzzzz:  onNext: " + s);
//            }
//        };
//
//        observable.subscribe(subscriber);
    }
}