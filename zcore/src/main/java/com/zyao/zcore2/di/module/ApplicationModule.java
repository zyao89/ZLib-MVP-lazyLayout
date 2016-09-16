/**
 * Title: ApplicationModule.java
 * Package: com.zyao.zcore2.di.module
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.di.module;

import android.app.Application;
import android.content.Context;

import com.zyao.zcore2.di.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Class: ApplicationModule
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/16 17:02
 */
@Module
public class ApplicationModule
{
    private final Application mApplication;

    public ApplicationModule (Application application)
    {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife("application")
    Context provideApplicationContext ()
    {
        return mApplication;
    }

    @Provides
    @Singleton
    @ContextLife("application")
    Application provideApplication ()
    {
        return mApplication;
    }
}
