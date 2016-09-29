package com.zyao.zcore;

import android.support.annotation.Nullable;

import com.zyao.zcore.view.IBaseUIViewHandler;
import com.zyao.zutils.TaskController;
import com.zyao.zutils.Z;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class: IBasePresenter
 * Description: Presenter的基类
 * Author: Zyao89
 * Time: 2016/7/20 17:07
 */
/* package */ abstract class BasePresenter<ViewHandler extends IBaseUIViewHandler>
{
    protected final String TAG = this.getClass().getSimpleName();
    protected TaskController mHandler;
    protected ViewHandler mViewHandler;
    private ConcurrentLinkedQueue<BasePresenter> mSubPresenterLinkedQueue;
    private boolean isAlreadyInit = false;

    public BasePresenter (@Nullable ViewHandler rootViewHandler)
    {
        mViewHandler = rootViewHandler;
        this.mHandler = Z.task();
    }

    public void init ()
    {
        initData();
        initSubPresenter();
        initSubPresenterData();
        initListener();
        initSubPresenterListener();
        initDefaultData();
        initSubPresenterDefaultData();
        isAlreadyInit = true;
    }

    protected abstract void initSubPresenter ();

    private void initSubPresenterDefaultData ()
    {
        if (isAlreadyInit)
        {
            return;
        }
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initData();
            }
        }
    }

    private void initSubPresenterListener ()
    {
        if (isAlreadyInit)
        {
            return;
        }
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initListener();
            }
        }
    }

    private void initSubPresenterData ()
    {
        if (isAlreadyInit)
        {
            return;
        }
        if (mSubPresenterLinkedQueue != null)
        {
            for (BasePresenter subPresenter : mSubPresenterLinkedQueue)
            {
                subPresenter.initDefaultData();
            }
        }
    }

    /**
     * 初始化操作
     */
    protected abstract void initData ();

    protected abstract void initListener ();

    /**
     * 子类重写此方法
     */
    protected void initDefaultData ()
    {

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
     * 退出操作
     */
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

}
