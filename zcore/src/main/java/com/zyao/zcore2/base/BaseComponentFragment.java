/**
 * Title: BaseComponentFragment.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import android.os.Bundle;

import com.zyao.zcore.BaseFragment;
import com.zyao.zcore.inter.IBaseFragmentViewHandler;
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
public abstract class BaseComponentFragment<ViewHandler extends IBaseFragmentViewHandler, Presenter extends BaseComponentPresenter> extends BaseFragment<ViewHandler>
{
    @Inject
    protected ViewHandler mViewHandler;

    @Inject
    protected Presenter mPresenter;

    @Override
    protected boolean onNewViewHandler (Bundle savedInstanceState)
    {
        initComponent(getApplicationComponent(), getFragmentModule());
        return false;
    }

    @Override
    protected void onNewPresenter (Bundle savedInstanceState)
    {
        if (isExistPresenter())
        {
            mPresenter.attachView(mViewHandler);
        }
        else
        {
            Z.log().e(TAG, "【 ERROR 】 mPresenter is null...");
        }
    }

    @Override
    protected void onDestroyPresenter ()
    {
        if (isExistPresenter())
        {
            mPresenter.detachView();
        }
    }

    @Override
    protected ViewHandler newViewHandler ()
    {
        return this.mViewHandler;
    }

    @Override
    protected void initPresenter (Bundle savedInstanceState)
    {
        if (isExistPresenter())
        {
            mPresenter.initData();
        }
    }

    @Override
    protected void initListener ()
    {
        if (isExistPresenter())
        {
            mPresenter.initListener();
        }
    }

    @Override
    protected void initDefaultData ()
    {
        if (isExistPresenter())
        {
            mPresenter.initDefaultData();
        }
    }

    private boolean isExistPresenter ()
    {
        return mPresenter != null;
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
