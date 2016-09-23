/**
 * Title: MainFragment.java
 * Package: com.zyao.zlib.fragment
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/22
 */
package com.zyao.zlib.fragment;

import android.os.Build;
import android.os.Bundle;

import com.zyao.zcore2.base.BaseComponentFragment;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.FragmentModule;
import com.zyao.zlib.component.DaggerMainFragmentComponent;
import com.zyao.zlib.presenter.MainFragmentPresenter;
import com.zyao.zlib.view.MainFragmentViewHandler;

/**
 * Class: MainFragment
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/22 10:18
 */
public class MainFragment extends BaseComponentFragment<MainFragmentViewHandler, MainFragmentPresenter>
{
    @Override
    protected void initComponent (ApplicationComponent applicationComponent, FragmentModule fragmentModule)
    {
        DaggerMainFragmentComponent.builder().applicationComponent(applicationComponent).fragmentModule(fragmentModule).build().inject(this);
        System.out.println("mViewHandler: " + mViewHandler);
        System.out.println("mPresenter: " + mPresenter);
    }
}
