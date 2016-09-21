/**
 * Title: BaseComponentFragment.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyao.zcore.support.SupportFragment;
import com.zyao.zcore2.base.inter.IBasePresenter;
import com.zyao.zcore2.base.inter.IBaseViewHandler;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.FragmentModule;
import com.zyao.zutils.Z;

import javax.inject.Inject;

/**
 * Class: BaseComponentFragment
 * Description: BaseComponentFragment基类
 * Author: Zyao89
 * Time: 2016/9/17 3:05
 */
public abstract class BaseComponentFragment<ViewHandler extends IBaseViewHandler, Presenter extends IBasePresenter> extends SupportFragment implements ICommonMethod
{
    protected final String TAG = this.getClass().getSimpleName();
    private final BasePresenterFactory mSubPresenterBasePresenterFactory = BasePresenterFactory.create();
    protected Activity mActivity;
    protected Context mContext;
    protected View mRootView;

    @Inject
    protected ViewHandler mViewHandler;
    @Inject
    protected Presenter mPresenter;
    private BaseComponentFragmentViewHandler _mViewHandler;
    private BaseComponentPresenter<ViewHandler> _Presenter;

    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        if (isExistViewHandler())
        {
            _mViewHandler.onAttach(activity);
        }
        mActivity = activity;
        mContext = activity;
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initComponent(getApplicationComponent(), getFragmentModule());

        if (mViewHandler instanceof BaseComponentFragmentViewHandler)
        {
            _mViewHandler = (BaseComponentFragmentViewHandler) mViewHandler;
        }
        if (mPresenter instanceof BaseComponentPresenter)
        {
            _Presenter = (BaseComponentPresenter<ViewHandler>) mPresenter;
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        onNewPresenter();
        initPresenter(savedInstanceState);
        initListener();
        initDefaultData();
        if (isExistViewHandler())
        {
            _mViewHandler.onActivityCreated();
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
            _mViewHandler.onPause();
        }
    }

    @Override
    public void onDestroyView ()
    {
        super.onDestroyView();
        if (isExistViewHandler())
        {
            _mViewHandler.onDestroyView();
        }
        doExit();
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        onDestroyPresenter();
        if (isExistViewHandler())
        {
            _mViewHandler.onDestroy();
        }
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    @Override
    public boolean onBackPressedSupport ()
    {
        if (isExistViewHandler())
        {
            if (_mViewHandler.onBackPressed())
            {
                return true;
            }
        }
        return super.onBackPressedSupport();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (isExistViewHandler())
        {
            final int resourceId = _mViewHandler.getResourceId();
            if (resourceId < 0)
            {
                throw new IllegalStateException("resourceId < 0 操作失败，引用不正规...");
            }
            mRootView = inflater.inflate(_mViewHandler.getResourceId(), container, false);
            if (mRootView == null)
            {
                throw new IllegalStateException("mRootView is null...");
            }
            _mViewHandler.onCreate(mRootView);
            _mViewHandler.resetDefaultState(savedInstanceState);//恢复
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
            _mViewHandler.onViewCreated();
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
            _mViewHandler.onStart();
        }
    }

    @Override
    public void onResume ()
    {
        super.onResume();
        if (isExistViewHandler())
        {
            _mViewHandler.onResume();
        }
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (isExistViewHandler())
        {
            _mViewHandler.onConfigurationChanged(newConfig);
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
    }

    @Override
    public void onStop ()
    {
        super.onStop();
        if (isExistViewHandler())
        {
            _mViewHandler.onStop();
        }
    }

    private void onNewPresenter ()
    {
        if (isExistPresenter())
        {
            _Presenter.attachView(mViewHandler);
        }
        else
        {
            Z.log().e(TAG, "【 ERROR 】 mPresenter is null...");
        }
    }

    private void onDestroyPresenter ()
    {
        if (isExistPresenter())
        {
            _Presenter.detachView();
        }
        mSubPresenterBasePresenterFactory.onDestroyPresenter();
    }

    private void initPresenter (Bundle savedInstanceState)
    {
        if (isExistPresenter())
        {
            _Presenter.initDataAndSubPresenterData(savedInstanceState);
        }
        mSubPresenterBasePresenterFactory.initPresenter(savedInstanceState);
    }

    private void initListener ()
    {
        if (isExistPresenter())
        {
            _Presenter.initListenerAndSubPresenterListener();
        }
        mSubPresenterBasePresenterFactory.initListener();
    }

    private void initDefaultData ()
    {
        if (isExistPresenter())
        {
            _Presenter.initDefaultDataAndSubPresenterDefaultData();
        }
        mSubPresenterBasePresenterFactory.initDefaultData();
    }

    /**
     * 判断ViewHandler是否存在
     *
     * @return true-存在， false - 不存在
     */
    private boolean isExistViewHandler ()
    {
        return mViewHandler != null && _mViewHandler != null;
    }

    private boolean isExistPresenter ()
    {
        return mPresenter != null && _Presenter != null;
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(getActivity(), cls);
        this.startActivity(intent);
    }

    protected void doExit ()
    {

    }

    protected ApplicationComponent getApplicationComponent ()
    {
        return Z.appComponent();
    }

    protected FragmentModule getFragmentModule ()
    {
        return new FragmentModule(this);
    }

    /**
     * 初始化组件
     *
     * @param applicationComponent appComponent
     * @param activityModule       activity
     */
    protected abstract void initComponent (ApplicationComponent applicationComponent, FragmentModule activityModule);
}
