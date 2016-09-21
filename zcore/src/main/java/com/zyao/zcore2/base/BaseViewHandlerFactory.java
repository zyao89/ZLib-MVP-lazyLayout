/**
 * Title: BasePresenterFactory.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.zyao.zcore2.base.inter.IBaseViewHandler;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class: BasePresenterFactory
 * Description: Presenter工厂管理类
 * Author: Zyao89
 * Time: 2016/9/21 19:09
 */
final class BaseViewHandlerFactory
{
    private ConcurrentLinkedQueue<IBaseViewHandler> mSubViewHandlerLinkedQueue;

    private BaseViewHandlerFactory ()
    {

    }

    public static BaseViewHandlerFactory create ()
    {
        return new BaseViewHandlerFactory();
    }

    public void onStart ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onStart();
                }
            }
        }
    }

    public void onStop ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onStop();
                }
            }
        }
    }

    public void onPause ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onPause();
                }
            }
        }
    }

    public void onResume ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onResume();
                }
            }
        }
    }

    public boolean onBackPressed ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    if (((IBaseRootLifeViewHandler) subViewHandler).onBackPressed())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void onConfigurationChanged (Configuration newConfig)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onConfigurationChanged(newConfig);
                }
            }
        }
    }

    public void onDestroy ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onDestroy();
                }
            }
            mSubViewHandlerLinkedQueue.clear();
        }
        mSubViewHandlerLinkedQueue = null;
    }

    public void resetDefaultState (Bundle savedInstanceState)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).resetDefaultState(savedInstanceState);
                }
            }
        }
    }

    public void onViewCreated ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onViewCreated();
                }
            }
        }
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
    public <T extends IBaseViewHandler, V extends View> T createSubViewHandler (Class<T> clazz, V rootView)
    {
        try
        {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T t = constructor.newInstance();
            addSubViewHandler(t);
            onCreate(rootView);
            return t;
        }
        catch (Exception e)
        {
            try
            {
                Constructor<T> constructor = (Constructor<T>) clazz.getConstructors()[0];
                constructor.setAccessible(true);
                T t = constructor.newInstance();
                addSubViewHandler(t);
                onCreate(rootView);
                return t;
            }
            catch (Exception e1)
            {
                throw new RuntimeException("【 非法了!! 】初始化创建失败...", e1);
            }
        }
    }

    public <T extends IBaseViewHandler> T getSubViewHandler (Class<T> iOutViewHandler)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
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

    private <T extends IBaseViewHandler> void addSubViewHandler (T baseViewHandler)
    {
        if (mSubViewHandlerLinkedQueue == null)
        {
            mSubViewHandlerLinkedQueue = new ConcurrentLinkedQueue<>();
        }
        if (!mSubViewHandlerLinkedQueue.contains(baseViewHandler))
        {
            mSubViewHandlerLinkedQueue.add(baseViewHandler);
        }
    }

    private <V extends View> void onCreate (V view)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseRootLifeViewHandler)
                {
                    ((IBaseRootLifeViewHandler) subViewHandler).onCreate(view);
                }
            }
        }
    }

    public void onAttach (Activity activity)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseFragmentLifeViewHandler)
                {
                    ((IBaseFragmentLifeViewHandler) subViewHandler).onAttach(activity);
                }
            }
        }
    }

    public void onActivityCreated ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseFragmentLifeViewHandler)
                {
                    ((IBaseFragmentLifeViewHandler) subViewHandler).onActivityCreated();
                }
            }
        }
    }

    public void onDestroyView ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseFragmentLifeViewHandler)
                {
                    ((IBaseFragmentLifeViewHandler) subViewHandler).onDestroyView();
                }
            }
        }
    }
}
