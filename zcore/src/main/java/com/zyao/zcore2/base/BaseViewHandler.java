package com.zyao.zcore2.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;

import com.zyao.zcore.BaseActivity;
import com.zyao.zcore2.base.inter.IBaseViewHandler;

import butterknife.ButterKnife;

/**
 * Class: BaseRootViewHandler
 * Description: RootViewHandler抽象类
 * Author: Zyao89
 * Time: 2016/7/19 17:36
 */
/* package */ abstract class BaseViewHandler<ViewType extends View> implements IBaseRootLifeViewHandler
{
    protected final String TAG = this.getClass().getSimpleName();
    protected ViewType mRootView;
    protected Context mContext;
    protected Handler mHandler;
    /* package */ BaseViewHandlerFactory mBaseViewHandlerFactory = BaseViewHandlerFactory.create();
    private Handler.Callback mHandlerCallback = null;
    private boolean isAlreadyInitViewHandler = false;
    /** 中间部分id */
    private int mRootFragmentContainerId = -1;

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
    public final void onViewCreated ()
    {
        initViewHandler(mRootView);
        initListeners();
        initDefaultData();
    }

    @Override
    public void onStart ()
    {
        mBaseViewHandlerFactory.onStart();
    }

    @Override
    public void onStop ()
    {
        mBaseViewHandlerFactory.onStop();
    }

    @Override
    public void onPause ()
    {
        mBaseViewHandlerFactory.onPause();
    }

    @Override
    public void onResume ()
    {
        mBaseViewHandlerFactory.onResume();
    }

    @Override
    public boolean onBackPressed ()
    {
        return mBaseViewHandlerFactory.onBackPressed();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig)
    {
        mBaseViewHandlerFactory.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy ()
    {
        mBaseViewHandlerFactory.onDestroy();
    }

    @Override
    public void resetDefaultState (Bundle savedInstanceState)
    {
        mBaseViewHandlerFactory.resetDefaultState(savedInstanceState);
    }

    /* package */ void initViewHandler (ViewType rootView)
    {
        initViews();
        initSubViewHandler();
        mBaseViewHandlerFactory.onViewCreated();
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

    protected final ViewType getRootView ()
    {
        return mRootView;
    }

    protected final View findViewById (int resId)
    {
        return mRootView.findViewById(resId);
    }

    protected final Context getContext ()
    {
        if (mContext != null)
        {
            return mContext;
        }
        throw new IllegalStateException("mContext is null!!!");
    }

    protected final FragmentManager getFragmentManager ()
    {
        if (getContext() instanceof BaseActivity)
        {
            return ((BaseActivity) getContext()).getSupportFragmentManager();
        }
        return null;
    }

    protected final Window getWindow ()
    {
        if (getContext() instanceof BaseActivity)
        {
            return ((BaseActivity) getContext()).getWindow();
        }
        return null;
    }

    protected final Resources getResources ()
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
    protected final <T extends IBaseViewHandler, V extends View> T createSubViewHandler (Class<T> clazz, V rootView)
    {
        return mBaseViewHandlerFactory.createSubViewHandler(clazz, rootView);
    }

    protected final <T extends IBaseViewHandler> T getSubViewHandler (Class<T> iOutViewHandler)
    {
        return mBaseViewHandlerFactory.getSubViewHandler(iOutViewHandler);
    }

    /**
     * mHandler回调
     *
     * @param callback
     */
    protected final void setOnHandlerCallback (Handler.Callback callback)
    {
        mHandlerCallback = callback;
    }

    /**
     * 获取根FragmentContainerId
     *
     * @return id
     */
    @IdRes
    protected final int getRootFragmentContainerId ()
    {
        return mRootFragmentContainerId;
    }

    /**
     * 设置根FragmentContainerId
     *
     * @param containerId
     */
    protected final void setRootFragmentContainerId (@IdRes int containerId)
    {
        mRootFragmentContainerId = containerId;
    }
}
