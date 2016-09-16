/**
 * Title: ActivityModule.java
 * Package: com.zyao.zcore2.di.module
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.di.module;

import android.app.Activity;

import com.zyao.zcore2.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Class: ActivityModule
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/16 17:02
 */
@Module
public class ActivityModule
{
    private final Activity activity;

    public ActivityModule (Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity ()
    {
        return activity;
    }
}
