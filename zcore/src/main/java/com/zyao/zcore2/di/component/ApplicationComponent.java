/**
 * Title: ApplicationComponent.java
 * Package: com.zyao.zcore2.di.component
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.di.component;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

import com.zyao.zcore2.di.ContextLife;
import com.zyao.zcore2.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface: ApplicationComponent
 * Description: 应用层提供的服务组件
 * Author: Zyao89
 * Time: 2016/9/16 17:27
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
{
    @ContextLife("application")
    Context getContext ();

    @ContextLife("application")
    Application getApplication ();

    LayoutInflater getLayoutInflater();
}
