package com.zyao.zlib.activity;

import com.zyao.zlib.component.DaggerMainActivityComponent;

public class MainActivity extends BaseComponentActivity<MainActivityViewHandler, MainPresenter>
{
    @Override
    protected void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule)
    {
        DaggerMainActivityComponent.builder().applicationComponent(applicationComponent).activityModule(activityModule).build().inject(this);
        System.out.println("mViewHandler: " + mViewHandler);
        System.out.println("mPresenter: " + mPresenter);
    }
}
