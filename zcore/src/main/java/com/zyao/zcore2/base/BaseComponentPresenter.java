/**
 * Title: BaseComponentPresenter.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.zyao.zcore2.base.inter.IBasePresenter;
import com.zyao.zcore2.base.inter.IBaseViewHandler;
import com.zyao.zcore2.rx.IRxCompositeSubscription;
import com.zyao.zcore2.rx.rxbus.RxBus;
import com.zyao.zcore2.rx.rxbus.RxBusImpl;
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
public abstract class BaseComponentPresenter<ViewHandler extends IBaseViewHandler> implements IRxCompositeSubscription, ICommonMethod
{
    protected final String TAG = this.getClass().getSimpleName();
    private final BasePresenterFactory mSubPresenterBasePresenterFactory = BasePresenterFactory.create();
    /** RxJava */
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    protected TaskController mHandler;
    protected ViewHandler mViewHandler;
    protected RxBus mRxBus;
    /** 特殊方法代理 */
    private ICommonMethod mCommonMethodProxy;

    /* package */ void attachView (ViewHandler viewHandler)
    {
        this.mViewHandler = viewHandler;
        this.mHandler = Z.task();
        this.mRxBus = RxBusImpl.getInstance();
    }

    /* package */ void detachView ()
    {
        doExitAndSubPresenterExit();
        this.mCompositeSubscription.unsubscribe();//rxJava
        this.mViewHandler = null;
    }

    /**
     * 绑定代理
     *
     * @param commonMethod
     */
    /* package */ void attachCommonMethod (ICommonMethod commonMethod)
    {
        if (commonMethod == null)
        {
            throw new RuntimeException("commonMethod is null... error!! ");
        }
        this.mCommonMethodProxy = commonMethod;
    }

    /**
     * 初始化操作
     *
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

    /**
     * 返回键操作
     * @return true-中断
     */
    protected boolean onBackPressed ()
    {
        return mSubPresenterBasePresenterFactory.onBackPressed();
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


    //*********************** ICommonMethod ****************************

    @Override
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls)
    {
        mCommonMethodProxy.gotoNewActivityAndFinish(cls);
    }

    @Override
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls, Bundle bundle)
    {
        mCommonMethodProxy.gotoNewActivityAndFinish(cls, bundle);
    }

    @Override
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls, Intent intent)
    {
        mCommonMethodProxy.gotoNewActivityAndFinish(cls, intent);
    }

    @Override
    public void gotoNewActivity (@NonNull Class<?> cls)
    {
        mCommonMethodProxy.gotoNewActivity(cls);
    }

    @Override
    public void gotoNewActivity (@NonNull Class<?> cls, Intent intent)
    {
        mCommonMethodProxy.gotoNewActivity(cls, intent);
    }

    @Override
    public void gotoNewActivityForResult (@NonNull Class<?> cls, Intent intent, int requestCode)
    {
        mCommonMethodProxy.gotoNewActivityForResult(cls, intent, requestCode);
    }

    @Override
    public void gotoNewActivityForResult (@NonNull Class<?> cls, Intent intent, Bundle bundle, int requestCode)
    {
        mCommonMethodProxy.gotoNewActivityForResult(cls, intent, bundle, requestCode);
    }

    @Override
    public void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle)
    {
        mCommonMethodProxy.gotoNewActivity(cls, bundle);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Intent intent)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, intent);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Intent intent)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, intent);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, colorOrImageRes);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, colorOrImageRes);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, colorOrImageRes, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, colorOrImageRes, durationMills);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, durationMills);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, intent);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, intent);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, intent, colorOrImageRes);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, intent, colorOrImageRes);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, intent, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, intent, durationMills);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnim(triggerView, targetClass, intent, colorOrImageRes, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityByAnimAndFinish(triggerView, targetClass, intent, colorOrImageRes, durationMills);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, int requestCode)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, requestCode);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, requestCode);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode, int colorOrImageRes)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, requestCode, colorOrImageRes);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, requestCode, durationMills);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode, int colorOrImageRes, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, requestCode, colorOrImageRes, durationMills);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Bundle bundle, int requestCode)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, bundle, requestCode);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, bundle, requestCode);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode, int colorOrImageRes)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, bundle, requestCode, colorOrImageRes);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, bundle, requestCode, requestCode, durationMills);
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode, int colorOrImageRes, long durationMills)
    {
        mCommonMethodProxy.gotoNewActivityForResultByAnim(triggerView, targetClass, intent, bundle, requestCode, colorOrImageRes, durationMills);
    }

    @Override
    public void loadRootFragment (BaseComponentFragment toFragment)
    {
        mCommonMethodProxy.loadRootFragment(toFragment);
    }

    @Override
    public void replaceLoadRootFragment (BaseComponentFragment toFragment, boolean addToBack)
    {
        mCommonMethodProxy.replaceLoadRootFragment(toFragment, addToBack);
    }

    @Override
    public void loadMultipleRootFragment (int showPosition, BaseComponentFragment... toFragments)
    {
        mCommonMethodProxy.loadMultipleRootFragment(showPosition, toFragments);
    }
}
