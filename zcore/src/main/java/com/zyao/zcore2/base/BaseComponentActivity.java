/**
 * Title: BaseComponentActivity.java
 * Package: com.zyao.zcore
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zyao.zcore.BaseActivity;
import com.zyao.zcore.R;
import com.zyao.zcore.anim.FragmentAnimator;
import com.zyao.zcore.view.BaseActivityViewHandler;
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

    protected ActivityModule getActivityModule ()
    {
        return new ActivityModule(this);
    }

    /**
     * 设置标题栏
     *
     * @param toolbar
     * @param title
     */
    protected void setToolBar (Toolbar toolbar, String title)
    {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 设置夜间模式
     *
     * @param isNight
     */
    protected void setDayNightMode (boolean isNight)
    {
        if (isNight)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

    /**
     * 是否为夜间模式
     *
     * @return
     */
    protected boolean isNightMode ()
    {
        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        return defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator ()
    {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        //        return new DefaultHorizontalAnimator();
        // 设置自定义动画
        //        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    /**
     * 初始化组件
     *
     * @param applicationComponent appComponent
     * @param activityModule       activity
     */
    protected abstract void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule);
}
