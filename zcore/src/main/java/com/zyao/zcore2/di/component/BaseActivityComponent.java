/**
 * Title: BaseActivityComponent.java
 * Package: com.zyao.zcore2.di.component
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.di.component;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.zyao.zcore2.di.ActivityScope;
import com.zyao.zcore2.di.module.ActivityModule;

import dagger.Component;

/**
 * Interface: BaseActivityComponent
 * Description: 基类
 * Author: Zyao89
 * Time: 2016/9/16 17:01
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface BaseActivityComponent
{
    AppCompatActivity getAppCompatActivity (); // Expose the activity to sub-graphs.

    Activity getActivity ();

    Window getWindow ();
}
