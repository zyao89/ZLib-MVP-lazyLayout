/**
 * Title: ActivityComponent.java
 * Package: com.zyao.zcore2.di.component
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zlib.component;

import com.zyao.zcore2.di.FragmentScope;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.component.FragmentComponent;
import com.zyao.zcore2.di.module.FragmentModule;
import com.zyao.zlib.fragment.MainFragment;

import dagger.Component;

/**
 * Interface: ActivityComponent
 * Description: 基类
 * Author: Zyao89
 * Time: 2016/9/16 17:01
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface MainFragmentComponent extends FragmentComponent
{
    void inject (MainFragment fragment);
}
