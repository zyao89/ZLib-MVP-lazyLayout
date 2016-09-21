package com.zyao.zcore2.base;

import android.support.annotation.LayoutRes;

/**
 * Interface: IBaseActivityLifeViewHandler
 * Description: Activity公共View基础接口类
 * Author: Zyao89
 * Time: 2016/7/30 13:44
 */
interface IBaseActivityLifeViewHandler
{
    /**
     * 获取主Activity的Layout布局id
     *
     * @return ResourceId
     */
    @LayoutRes
    int getResourceId ();
}
