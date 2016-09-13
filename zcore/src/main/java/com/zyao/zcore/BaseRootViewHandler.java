package com.zyao.zcore;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.ButterKnife;

/**
 * Class: BaseRootViewHandler
 * Description: RootViewHandler抽象类
 * Author: Zyao89
 * Time: 2016/7/19 17:36
 */
/* package */ abstract class BaseRootViewHandler<ViewType extends View> implements IBaseRootViewHandler
{
    protected ViewType mRootView;
    protected Context mContext;
    protected Handler mHandler;
    /* package */ ConcurrentLinkedQueue<IBaseRootViewHandler> mSubViewHandlerLinkedQueue;
    private Handler.Callback mHandlerCallback = null;
    private boolean isAlreadyInitViewHandler = false;

    @Override
    public void onCreate (@NonNull View rootView)
    {
        mRootView = (ViewType) rootView;
        mContext = mRootView.getContext();
        mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback()
        {
            @Override
            public boolean handleMessage (Message msg)
            {
                return mHandlerCallback != null && mHandlerCallback.handleMessage(msg);
            }
        });
        ButterKnife.bind(this, mRootView);
    }

    @Override
    public void onViewCreated ()
    {
        initViewHandler(mRootView);
        initListeners();
        initDefaultData();
    }

    @Override
    public void onStart ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onStart();
            }
        }
    }

    @Override
    public void onStop ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onStop();
            }
        }
    }

    @Override
    public void onPause ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onPause();
            }
        }
    }

    @Override
    public void onResume ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onResume();
            }
        }
    }

    @Override
    public boolean onBackPressed ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler.onBackPressed())
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onConfigurationChanged(newConfig);
            }
        }
    }

    @Override
    public void onDestroy ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onDestroy();
            }
            mSubViewHandlerLinkedQueue.clear();
        }
    }

    @Override
    public void resetDefaultState (Bundle savedInstanceState)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.resetDefaultState(savedInstanceState);
            }
        }
    }

    /* package */ void initViewHandler (ViewType rootView)
    {
        initViews();
        initSubViewHandler();
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                subViewHandler.onViewCreated();
            }
        }
        isAlreadyInitViewHandler = true;
    }

    public void show ()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run ()
            {
                mRootView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void dismiss ()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run ()
            {
                mRootView.setVisibility(View.GONE);
            }
        });
    }

    public boolean isShown ()
    {
        return mRootView.isShown();
    }

    protected ViewType getRootView ()
    {
        return mRootView;
    }

    protected View findViewById (int resId)
    {
        return mRootView.findViewById(resId);
    }

    protected Context getContext ()
    {
        if (mContext != null)
        {
            return mContext;
        }
        throw new IllegalStateException("mContext is null!!!");
    }

    protected FragmentManager getFragmentManager ()
    {
        if (getContext() instanceof BaseActivity)
        {
            return ((BaseActivity) getContext()).getSupportFragmentManager();
        }
        return null;
    }

    protected Window getWindow ()
    {
        if (getContext() instanceof BaseActivity)
        {
            return ((BaseActivity) getContext()).getWindow();
        }
        return null;
    }

    protected Resources getResources ()
    {
        if (getContext() instanceof BaseActivity)
        {
            return getContext().getResources();
        }
        return null;
    }

    protected abstract void initViews ();

    protected void initSubViewHandler ()
    {

    }

    protected void initListeners ()
    {

    }

    protected void initDefaultData ()
    {

    }

    /**
     * 系统自动创建子ViewHandler
     *
     * @param clazz    子ViewHandler
     * @param rootView rootview
     * @param <T>      ViewHandler基类
     *
     * @return
     */
    protected <T extends BaseViewHandler<V>, V extends View> T createSubViewHandler (Class<T> clazz, V rootView)
    {
        try
        {
            Constructor<T> constructor = clazz.getDeclaredConstructor(rootView.getClass());
            constructor.setAccessible(true);
            T t = constructor.newInstance(rootView);
            addSubViewHandler(t);
            return t;
        }
        catch (Exception e)
        {
            try
            {
                Constructor<T> constructor = (Constructor<T>) clazz.getConstructors()[0];
                constructor.setAccessible(true);
                T t = constructor.newInstance(rootView);
                addSubViewHandler(t);
                return t;
            }
            catch (Exception e1)
            {
                throw new RuntimeException("【 非法了!! 】初始化创建失败...", e1);
            }
        }
    }

    protected <T extends IBaseUIViewHandler> T getSubViewHandler (Class<T> iOutViewHandler)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                Class<?>[] interfaces = subViewHandler.getClass().getInterfaces();
                for (Class<?> clazz : interfaces)
                {
                    if (clazz.equals(iOutViewHandler))
                    {
                        return (T) subViewHandler;
                    }
                }
            }
        }
        return null;
    }

    protected <T extends IBaseRootViewHandler> void addSubViewHandler (T baseViewHandler)
    {
        if (mSubViewHandlerLinkedQueue == null)
        {
            mSubViewHandlerLinkedQueue = new ConcurrentLinkedQueue<>();
        }
        if (!mSubViewHandlerLinkedQueue.contains(baseViewHandler))
        {
            mSubViewHandlerLinkedQueue.add(baseViewHandler);
            if (isAlreadyInitViewHandler)
            {
                baseViewHandler.onViewCreated();
            }
        }
    }

    protected boolean isAlreadyExist ()
    {
        return mSubViewHandlerLinkedQueue != null && mSubViewHandlerLinkedQueue.contains(this);
    }

    protected void setOnHandlerCallback (Handler.Callback callback)
    {
        mHandlerCallback = callback;
    }
}
