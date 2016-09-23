package com.zyao.zlib.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.zyao.zcore2.base.BaseComponentActivity;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.ActivityModule;
import com.zyao.zlib.R;
import com.zyao.zlib.component.DaggerMainActivityComponent;
import com.zyao.zlib.fragment.MainFragment;
import com.zyao.zlib.presenter.MainPresenter;
import com.zyao.zlib.view.MainActivityViewHandler;

import butterknife.BindView;

public class MainActivity extends BaseComponentActivity<MainActivityViewHandler, MainPresenter>
{
    @Override
    protected void onCreateRootFragment (Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
            loadRootFragment(new MainFragment());
        }
        initToolbar(savedInstanceState);
    }

    @Override
    protected void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule)
    {
        DaggerMainActivityComponent.builder().applicationComponent(applicationComponent).activityModule(activityModule).build().inject(this);
        System.out.println("mViewHandler: " + mViewHandler);
        System.out.println("mPresenter: " + mPresenter);

    }



    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBarLayout;

    protected void initToolbar(Bundle savedInstanceState) {
        if (mToolbar == null || mAppBarLayout == null) return;
        setSupportActionBar(mToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mAppBarLayout.setElevation(6.6f);
        }
    }
}
