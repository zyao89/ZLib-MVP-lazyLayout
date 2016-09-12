package com.zyao.zcore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class: BaseFragment
 * Description: Fragment抽象基类
 * Author: Zyao89
 * Time: 2016/7/21 20:08
 */
public abstract class BaseFragment<ViewHandler extends IBaseFragmentViewHandler> extends Fragment
{
    protected ViewHandler mViewHandler;
    protected OnFragmentCallback mOnFragmentCallback = null;
    private View mRootView;
    private ConcurrentLinkedQueue<BasePresenter> mSubPresenterLinkedQueue;

    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        if (isExistViewHandler())
        {
            mViewHandler.onAttach(activity);
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (isExistViewHandler() && savedInstanceState == null)
        {
            mViewHandler.onDestroy();
            mViewHandler = newViewHandler();
        }
        else if (!isExistViewHandler())
        {
            mViewHandler = newViewHandler();
        }
        else
        {
            mViewHandler.resetDefaultState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (isExistViewHandler())
        {
            final int resourceId = mViewHandler.getResourceId();
            if (resourceId < 0)
            {
                throw new IllegalStateException("resourceId < 0 操作失败，引用不正规...");
            }
            mRootView = inflater.inflate(mViewHandler.getResourceId(), container, false);
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

        return mRootView;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (isExistViewHandler())
        {
            mViewHandler.onViewCreated();
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
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
        if (isExistOnFragmentLifeCallback())
        {
            mOnFragmentCallback.onActivityCreated();
        }
        if (isExistViewHandler())
        {
            mViewHandler.onActivityCreated();
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
    }

    @Override
    public void onStart ()
    {
        super.onStart();
        if (isExistViewHandler())
        {
            mViewHandler.onStart();
        }
    }

    @Override
    public void onResume ()
    {
        super.onResume();
        if (isExistViewHandler())
        {
            mViewHandler.onResume();
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
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
    }

    @Override
    public void onPause ()
    {
        super.onPause();
        if (isExistViewHandler())
        {
            mViewHandler.onPause();
        }
    }

    @Override
    public void onStop ()
    {
        super.onStop();
        if (isExistViewHandler())
        {
            mViewHandler.onStop();
        }
    }

    @Override
    public void onDestroyView ()
    {
        super.onDestroyView();
        if (isExistOnFragmentLifeCallback())
        {
            mOnFragmentCallback.onDestroyView();
        }
        if (isExistViewHandler())
        {
            mViewHandler.onDestroyView();
        }
        doExit();
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if (isExistViewHandler())
        {
            mViewHandler.onDestroy();
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
        getActivity().finish();
    }

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls, Bundle bundle)
    {
        this.gotoNewActivity(cls, bundle);
        getActivity().finish();
    }

    /**
     * 跳转新的Activity
     *
     * @param cls
     */
    public void gotoNewActivity (@NonNull Class<?> cls)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        this.startActivity(intent);
    }

    /**
     * 设置Fragment生命周期回调
     *
     * @param callback
     */
    public void setOnFragmentCallback (OnFragmentCallback callback)
    {
        this.mOnFragmentCallback = callback;
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

    protected void doExit ()
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.doExit();
            }
        }
    }

    /**
     * 判断ViewHandler是否存在
     *
     * @return true-存在， false - 不存在
     */
    private boolean isExistViewHandler ()
    {
        return mViewHandler != null;
    }

    /**
     * 判断OnFragmentLifeCallback是否存在
     *
     * @return true-存在， false - 不存在
     */
    protected boolean isExistOnFragmentLifeCallback ()
    {
        return mOnFragmentCallback != null;
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

    /**
     * Interface: OnFragmentCallback
     * Description: Fragment生命周期回调
     * Author: Zyao89
     * Time: 2016/7/26 12:32
     */
    public interface OnFragmentCallback
    {
        void onActivityCreated ();

        void onDestroyView ();
    }
}
