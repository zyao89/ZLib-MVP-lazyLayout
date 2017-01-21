/**
 * Title: BaseFragmentComponent.java
 * Package: com.zyao.zcore2.di.component
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.di.component;

import android.app.Activity;

import com.zyao.zcore2.di.FragmentScope;
import com.zyao.zcore2.di.module.FragmentModule;

import dagger.Component;

/**
 * Interface: BaseFragmentComponent
 * Description: 基类
 * Author: Zyao89
 * Time: 2016/9/17 1:57
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface BaseFragmentComponent
{
    Activity getActivity (); // Expose the activity to sub-graphs.
}
