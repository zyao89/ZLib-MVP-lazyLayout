/**
 * Title: BaseComponentActivity.java
 * Package: com.zyao.zcore
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.base;

import android.os.Bundle;

import com.zyao.zcore.BaseActivity;
import com.zyao.zcore.BaseActivityViewHandler;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.ActivityModule;
import com.zyao.zutils.Z;

import javax.inject.Inject;

/**
 * Class: BaseComponentActivity
 * Description: BaseComponentActivity基类
 * Author: Zyao89
 * Time: 2016/9/16 23:17
 */
public abstract class BaseComponentActivity<ViewHandler extends BaseActivityViewHandler, Presenter extends BaseComponentPresenter> extends BaseActivity<ViewHandler>
{
    @Inject
    protected ViewHandler mViewHandler;

    @Inject
    protected Presenter mPresenter;

    @Override
    protected boolean onNewViewHandler (Bundle savedInstanceState)
    {
        initComponent(getApplicationComponent(), getActivityModule());
        return false;
    }

    @Override
    protected void onNewPresenter (Bundle savedInstanceState)
    {
        if (isExistPresenter())
        {
            mPresenter.attachView(mViewHandler);
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

    protected ActivityModule getActivityModule ()
    {
        return new ActivityModule(this);
    }

    /**
     * 初始化组件
     *
     * @param applicationComponent appComponent
     * @param activityModule       activity
     */
    protected abstract void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule);
}
