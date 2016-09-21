/**
 * Title: HelperModule.java
 * Package: com.zyao.zcore2.di.module
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.di.module;

import android.app.Application;

import com.zyao.zcore2.helper.RetrofitHelper;
import com.zyao.zcore2.rx.rxbus.RxBus;
import com.zyao.zcore2.rx.rxbus.RxBusImpl;
import com.zyao.zutils.CommonUtilsManager;
import com.zyao.zutils.Z;

import dagger.Module;
import dagger.Provides;

/**
 * Class: HelperModule
 * Description: 帮助类
 * Author: Zyao89
 * Time: 2016/9/21 14:07
 */
@Module
public class HelperModule
{
    private final Application mApplication;

    public HelperModule (Application application)
    {
        this.mApplication = application;
    }

    @Provides
    RetrofitHelper provideRetrofitHelper ()
    {
        return RetrofitHelper.getInstance();
    }

    @Provides
    RxBus provideRxBus ()
    {
        return RxBusImpl.getInstance();
    }

    @Provides
    CommonUtilsManager provideZUtils ()
    {
        return Z.utils();
    }
}
