/**
 * Title: BasePresenterFactory.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.base;

import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class: BasePresenterFactory
 * Description: Presenter工厂管理类
 * Author: Zyao89
 * Time: 2016/9/21 19:09
 */
final class BasePresenterFactory
{
    private ConcurrentLinkedQueue<IBasePresenter> mSubPresenterLinkedQueue;

    private BasePresenterFactory ()
    {

    }

    public static BasePresenterFactory create ()
    {
        return new BasePresenterFactory();
    }

    public synchronized void initPresenter (Bundle savedInstanceState)
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (IBasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                if (subPresenter instanceof BaseComponentPresenter)
                {
                    ((BaseComponentPresenter) subPresenter).initDataAndSubPresenterData(savedInstanceState);
                }
            }
        }
    }

    public synchronized void initListener ()
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (IBasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                if (subPresenter instanceof BaseComponentPresenter)
                {
                    ((BaseComponentPresenter) subPresenter).initListenerAndSubPresenterListener();
                }
            }
        }
    }

    public synchronized void initDefaultData ()
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (IBasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                if (subPresenter instanceof BaseComponentPresenter)
                {
                    ((BaseComponentPresenter) subPresenter).initDefaultDataAndSubPresenterDefaultData();
                }
            }
        }
    }

    public synchronized void onDestroyPresenter ()
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (IBasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                if (subPresenter instanceof BaseComponentPresenter)
                {
                    ((BaseComponentPresenter) subPresenter).doExit();
                }
            }
            mSubPresenterLinkedQueue.clear();
        }
        mSubPresenterLinkedQueue = null;
    }

    public synchronized <T extends IBasePresenter, V extends IBaseViewHandler> T createSubPresenter (Class<T> clazz, V rootViewHandler)
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

    public synchronized <T extends IBasePresenter> T getSubPresenter (Class<T> presenter)
    {
        if (mSubPresenterLinkedQueue != null)
        {
            for (IBasePresenter subPresenter : mSubPresenterLinkedQueue)
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

    private <T extends IBasePresenter> void addSubPresenter (T basePresenter)
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
