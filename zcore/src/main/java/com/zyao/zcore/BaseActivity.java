package com.zyao.zcore;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.zyao.zcore.support.SupportActivity;
import com.zyao.zcore.view.IBaseActivityViewHandler;
import com.zyao.zcore.view.IBaseUIViewHandler;
import com.zyao.zutils.Z;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class: BaseActivity
 * Description: Activity抽象类
 * Author: Zyao89
 * Time: 2016/7/19 17:09
 */
public abstract class BaseActivity<ViewHandler extends IBaseActivityViewHandler> extends SupportActivity
{
    protected final String TAG = this.getClass().getSimpleName();
    protected ViewHandler mViewHandler;
    protected View mRootView;
    protected Context mContext;
    private ConcurrentLinkedQueue<BasePresenter> mSubPresenterLinkedQueue;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        if (onNewViewHandler(savedInstanceState))
        {
            if (isExistViewHandler())
            {
                if (mRootView == null)
                {
                    createRootView();
                }
                else
                {
                    mViewHandler.onCreate(mRootView);
                }
            }
            else
            {
                mViewHandler = newViewHandler();
                createRootView();
            }
        }
        else
        {
            mViewHandler = newViewHandler();
            createRootView();
        }

        mViewHandler.resetDefaultState(savedInstanceState);//恢复

        if (!Z.activityCtrl().containsActivity(this))
        {
            Z.activityCtrl().addActivity(this);
        }

        mContext = this;
    }

    @Override
    public void onBackPressedSupport ()
    {
        if (isExistViewHandler())
        {
            if (mViewHandler.onBackPressed())
            {
                return;
            }
        }
        super.onBackPressedSupport();
    }

    /**
     * 是否用新方法创建
     *
     * @param savedInstanceState
     *
     * @return true-不中断旧方法， false-中断现有方法
     */
    protected boolean onNewViewHandler (Bundle savedInstanceState)
    {
        return true;
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        if (isExistViewHandler())
        {
            mViewHandler.onViewCreated();
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
        onNewPresenter(savedInstanceState);
        initPresenter(savedInstanceState);
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initData();
            }
        }
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initSubPresenter();
            }
        }
        initListener();
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initListener();
            }
        }
        initDefaultData();
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initDefaultData();
            }
        }
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (isExistViewHandler())
        {
            mViewHandler.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy();
        onDestroyPresenter();
        if (isExistViewHandler())
        {
            mViewHandler.onDestroy();
        }
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.doExit();
            }
        }
        Z.activityCtrl().removeActivity(this);
    }

    protected void onNewPresenter (Bundle savedInstanceState)
    {
        //do someting
    }

    protected void onDestroyPresenter ()
    {
        //do someting
    }

    /**
     * 重新创建RootView
     */
    private void createRootView ()
    {
        if (isExistViewHandler())
        {
            final int resourceId = mViewHandler.getResourceId();
            if (resourceId < 0)
            {
                throw new IllegalStateException("resourceId < 0 操作失败，引用不正规...");
            }
            setContentView(resourceId);
            mRootView = getWindow().getDecorView();
            //            mRootView = getLayoutInflater().inflate(resourceId, null, false);
            if (mRootView == null)
            {
                throw new IllegalStateException("mRootView is null...");
            }
            mViewHandler.onCreate(mRootView);
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
    }

    /**
     * 获取RootView
     *
     * @return RootView
     */
    public View getRootView ()
    {
        return mRootView;
    }

    /**
     * 获取ViewHandler
     *
     * @return
     */
    public ViewHandler getViewHandler ()
    {
        return mViewHandler;
    }

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls)
    {
        this.gotoNewActivity(cls);
        this.finish();
    }

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls, Bundle bundle)
    {
        this.gotoNewActivity(cls, bundle);
        this.finish();
    }

    /**
     * 跳转新的Activity
     *
     * @param cls
     */
    public void gotoNewActivity (@NonNull Class<?> cls)
    {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        this.startActivity(intent);
    }

    /**
     * 跳转新的Activity
     *
     * @param cls
     * @param bundle
     */
    public void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, cls);
        this.startActivity(intent);
    }

    public void changeViewHandler (ViewHandler newViewHandler)
    {
        if (isExistViewHandler())
        {
            mViewHandler.onDestroy();
        }
        mViewHandler = newViewHandler;
    }

    protected abstract ViewHandler newViewHandler ();

    protected abstract void initPresenter (Bundle savedInstanceState);

    protected abstract void initListener ();

    protected abstract void initDefaultData ();

    /**
     * 判断ViewHandler是否存在
     *
     * @return true-存在， false - 不存在
     */
    private boolean isExistViewHandler ()
    {
        return mViewHandler != null;
    }

    protected <T extends BasePresenter<V>, V extends IBaseUIViewHandler> T createSubPresenter (Class<T> clazz, V rootViewHandler)
    {
        try
        {
            Constructor<T> constructor = clazz.getDeclaredConstructor(rootViewHandler.getClass());
            constructor.setAccessible(true);
            T t = constructor.newInstance(rootViewHandler);
            addSubPresenter(t);
            return t;
        }
        catch (Exception e)
        {
            try
            {
                Constructor<T> constructor = (Constructor<T>) clazz.getConstructors()[0];
                constructor.setAccessible(true);
                T t = constructor.newInstance(rootViewHandler);
                addSubPresenter(t);
                return t;
            }
            catch (Exception e1)
            {
                throw new RuntimeException("【 非法了!! 】初始化创建失败...", e1);
            }
        }
    }

    protected <T extends BasePresenter> T getSubPresenter (Class<T> presenter)
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                Class<?>[] interfaces = subPresenter.getClass().getInterfaces();
                for (Class<?> clazz : interfaces)
                {
                    if (clazz.equals(presenter))
                    {
                        return (T) subPresenter;
                    }
                }
            }
        }
        return null;
    }

    private <T extends BasePresenter> void addSubPresenter (T basePresenter)
    {
        if (mSubPresenterLinkedQueue == null)
        {
            mSubPresenterLinkedQueue = new ConcurrentLinkedQueue<>();
        }
        if (!mSubPresenterLinkedQueue.contains(basePresenter))
        {
            mSubPresenterLinkedQueue.add(basePresenter);
        }
    }
}
