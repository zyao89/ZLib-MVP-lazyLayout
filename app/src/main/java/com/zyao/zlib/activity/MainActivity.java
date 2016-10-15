package com.zyao.zlib.activity;

import android.os.Bundle;

import com.zyao.zcore2.base.BaseComponentActivity;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.ActivityModule;
import com.zyao.zlib.component.DaggerMainActivityComponent;
import com.zyao.zlib.fragment.MainFragment;
import com.zyao.zlib.presenter.MainPresenter;
import com.zyao.zlib.view.MainActivityViewHandler;
import com.zyao.zlib.view.MainActivityViewHandler_2;
import com.zyao.zlib.view.MainFragmentViewHandler;

public class MainActivity extends BaseComponentActivity<MainActivityViewHandler_2, MainPresenter>
{
    @Override
    protected void onCreateRootFragment (Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
            loadRootFragment(new MainFragment());
        }
    }

    @Override
    protected void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule)
    {
        DaggerMainActivityComponent.builder().applicationComponent(applicationComponent).activityModule(activityModule).build().inject(this);
        System.out.println("mViewHandler: " + mViewHandler);
        System.out.println("mPresenter: " + mPresenter);
    }
}
