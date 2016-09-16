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

import com.zyao.zcore2.di.ContextLife;
import com.zyao.zcore2.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface: ApplicationComponent
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/16 17:27
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
{
    @ContextLife("application")
    Application getApplication ();

    @ContextLife("application")
    Context getContext ();
}
