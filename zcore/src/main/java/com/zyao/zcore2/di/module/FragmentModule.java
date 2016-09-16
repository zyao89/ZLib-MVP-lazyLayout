/**
 * Title: FragmentModule.java
 * Package: com.zyao.zcore2.di.module
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.zyao.zcore2.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Class: FragmentModule
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/17 1:55
 */
@Module
public class FragmentModule
{
    private Fragment mFragment;

    public FragmentModule (Fragment fragment)
    {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity ()
    {
        return mFragment.getActivity();
    }
}
