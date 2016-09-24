/**
 * Title: MainActivityViewHandler.java
 * Package: com.zyao.zlib
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/13
 */
package com.zyao.zlib.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.zyao.zcore2.base.extra.BaseTitleBarComponentActivityViewHandler;
import com.zyao.zcore2.helper.RetrofitHelper;
import com.zyao.zlib.R;
import com.zyao.zlib.contract.MainContract;

import javax.inject.Inject;

/**
 * Class: MainActivityViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/13 16:06
 */
public class MainActivityViewHandler extends BaseTitleBarComponentActivityViewHandler<CoordinatorLayout> implements MainContract.IViewHandler
{
//    @BindView(R.id.drawer)
//    DrawerLayout mDrawerLayout;
//    @BindView(R.id.tool_bar)
//    Toolbar mToolbar;
//    @BindView(R.id.navigation)
//    NavigationView mNavigationView;
    //    @BindView(R.id.view_search)
    //    MaterialSearchView mSearchView;

    @Inject
    MainActivityViewHandler (RetrofitHelper retrofitHelper, Activity activity)
    {
        System.out.println("RetrofitHelper: " + retrofitHelper);
        System.out.println("Activity: " + activity);
    }

    @Override
    public int getResourceId ()
    {
        return R.layout.fragment_main;
    }

    @Override
    public void resetDefaultState (Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
//            setRootFragmentContainerId(R.id.fl_main_content);
        }
        super.resetDefaultState(savedInstanceState);
    }

    @Override
    protected void initSubViewHandler ()
    {
        SubComponentViewHandler subViewHandler = createSubViewHandler(SubComponentViewHandler.class, mRootView);

        System.out.println("subViewHandler: " + subViewHandler);
    }

    @Override
    protected void initListeners ()
    {
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v)
//            {
//                setDayNightMode(!isNightMode());
//                //                mZLoadingView.setVisibility(mZLoadingView.isShown()?View.GONE:View.VISIBLE);
//            }
//        });
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

        //        observable.subscribe(subscriber);

        //        Observable.just("123345").map(new Func1<String, String>()
        //        {
        //            @Override
        //            public String call (String s)
        //            {
        //                System.out.println("zzzzzzzz: Func1 call: " + s);
        //                return s + ":  haha";
        //            }
        //        }).subscribe(new Action1<String>()
        //        {
        //            @Override
        //            public void call (String s)
        //            {
        //                System.out.println("zzzzzzzz: Action1 call: " + s);
        //            }
        //        });
        //
        //        Observable.from(Arrays.asList("1", "2", "3")).subscribe(new Action1<String>()
        //        {
        //            @Override
        //            public void call (String s)
        //            {
        //                System.out.println("zzzzzzzz: Action1 call: " + s);
        //            }
        //        });

        //        Observable.create(new Observable.OnSubscribe<List<String>>()
        //        {
        //            @Override
        //            public void call (Subscriber<? super List<String>> subscriber)
        //            {//子线程
        //                System.out.println("zzzzzzzz create  call  Thread Name: " + Thread.currentThread().getName());
        //                List<String> strings = Arrays.asList("1", "2", "3", "4", "5", "6");
        //                subscriber.onNext(strings);
        //                subscriber.onCompleted();
        //            }
        //        }).subscribeOn(Schedulers.io()).flatMap(new Func1<List<String>, Observable<String>>()
        //        {
        //            @Override
        //            public Observable<String> call (List<String> strings)
        //            {
        //                System.out.println("zzzzzzzz   Thread Name: " + Thread.currentThread().getName());
        //                return Observable.from(strings);
        //            }
        //        }).observeOn(AndroidSchedulers.mainThread())//主线程
        //                .map(new Func1<String, String>()
        //                {
        //                    @Override
        //                    public String call (String s)
        //                    {
        //                        System.out.println("zzzzzzzz   Thread Name: " + Thread.currentThread().getName());
        //                        System.out.println("zzzzzzzz: map call: " + s);
        //                        return s;
        //                    }
        //                }).observeOn(Schedulers.io())//主线程
        //                .filter(new Func1<String, Boolean>()
        //                {
        //                    @Override
        //                    public Boolean call (String s)
        //                    {
        //                        return !s.equals("2");
        //                    }
        //                }).take(3)//过滤结果为3个
        //                .doOnNext(new Action1<String>()
        //                {
        //                    @Override
        //                    public void call (String s)
        //                    {
        //                        System.out.println("zzzzzzzz doOnNext  Thread Name: " + Thread.currentThread().getName());
        //                    }
        //                }).subscribe(new Action1<String>()
        //        {
        //            @Override
        //            public void call (String s)
        //            {
        //                System.out.println("zzzzzzzz   Thread Name: " + Thread.currentThread().getName());
        //                System.out.println("zzzzzzzz: flatMap call: " + s);
        //            }
        //        });


    }

    @Override
    public void getValue ()
    {

    }
}
