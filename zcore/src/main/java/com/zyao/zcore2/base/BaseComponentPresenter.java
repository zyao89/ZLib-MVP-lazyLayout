/**
 * Title: BaseComponentPresenter.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import android.os.Bundle;

import com.zyao.zcore2.base.inter.IBasePresenter;
import com.zyao.zcore2.base.inter.IBaseViewHandler;
import com.zyao.zcore2.rx.IRxCompositeSubscription;
import com.zyao.zutils.TaskController;
import com.zyao.zutils.Z;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Class: BaseComponentPresenter
 * Description: ComponentPresenter基类
 * Author: Zyao89
 * Time: 2016/9/17 2:22
 */
public abstract class BaseComponentPresenter<ViewHandler extends IBaseViewHandler> implements IRxCompositeSubscription, IBasePresenter
{
    protected final String TAG = this.getClass().getSimpleName();
    private final BasePresenterFactory mSubPresenterBasePresenterFactory = BasePresenterFactory.create();
    /** RxJava */
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    protected TaskController mHandler;
    protected ViewHandler mViewHandler;

    /* package */ void attachView (ViewHandler viewHandler)
    {
        this.mViewHandler = viewHandler;
        this.mHandler = Z.task();
    }

    /* package */ void detachView ()
    {
        doExitAndSubPresenterExit();
        this.mCompositeSubscription.unsubscribe();//rxJava
        this.mViewHandler = null;
    }

    /**
     * 初始化操作
     * @param savedInstanceState
     */
    /* package */  void initDataAndSubPresenterData (Bundle savedInstanceState)
    {
        initData(savedInstanceState);
        mSubPresenterBasePresenterFactory.initPresenter(savedInstanceState);
    }

    protected abstract void initData (Bundle savedInstanceState);

    /* package */  void initListenerAndSubPresenterListener ()
    {
        initListener();
        mSubPresenterBasePresenterFactory.initListener();
    }

    protected abstract void initListener ();

    /**
     * 子类重写此方法
     */
    /* package */  void initDefaultDataAndSubPresenterDefaultData ()
    {
        initDefaultData();
        mSubPresenterBasePresenterFactory.initDefaultData();
    }

    protected void initDefaultData ()
    {

    }

    /**
     * 退出操作
     */
    private void doExitAndSubPresenterExit ()
    {
        doExit();
        mSubPresenterBasePresenterFactory.onDestroyPresenter();
    }

    protected void doExit ()
    {

    }

    protected <T extends IBasePresenter, V extends IBaseViewHandler> T createSubPresenter (Class<T> clazz, V rootViewHandler)
    {
        return mSubPresenterBasePresenterFactory.createSubPresenter(clazz, rootViewHandler);
    }

    protected <T extends IBasePresenter> T getSubPresenter (Class<T> presenter)
    {
        return mSubPresenterBasePresenterFactory.getSubPresenter(presenter);
    }

    @Override
    public boolean isUnsubscribed ()
    {
        return mCompositeSubscription.isUnsubscribed();
    }

    @Override
    public void addSubscription (Subscription s)
    {
        mCompositeSubscription.add(s);
    }

    @Override
    public void addAllSubscription (Subscription... subscriptions)
    {
        mCompositeSubscription.addAll(subscriptions);
    }

    @Override
    public void removeSubscription (Subscription s)
    {
        mCompositeSubscription.remove(s);
    }

    @Override
    public void clearSubscription ()
    {
        mCompositeSubscription.clear();
    }

    @Override
    public boolean hasSubscriptions ()
    {
        return mCompositeSubscription.hasSubscriptions();
    }
}
