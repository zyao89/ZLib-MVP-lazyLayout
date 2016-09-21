package com.zyao.zlib.activity;

import com.zyao.zcore2.base.BaseComponentActivity;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.ActivityModule;
import com.zyao.zlib.component.DaggerMainActivityComponent;
import com.zyao.zlib.presenter.MainPresenter;
import com.zyao.zlib.view.MainActivityLifeViewHandler;

public class MainActivity extends BaseComponentActivity<MainActivityLifeViewHandler, MainPresenter>
{
    @Override
    protected void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule)
    {
        DaggerMainActivityComponent.builder().applicationComponent(applicationComponent).activityModule(activityModule).build().inject(this);
        System.out.println("mViewHandler: " + mViewHandler);
        System.out.println("mPresenter: " + mPresenter);
    }
}
