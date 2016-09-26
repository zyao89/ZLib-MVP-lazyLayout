/**
 * Title: BaseComponentActivityViewHandler.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Class: BaseComponentActivityViewHandler
 * Description: Activity的ViewHandler基类
 * Author: Zyao89
 * Time: 2016/9/17 14:00
 */
public abstract class BaseComponentActivityViewHandler<RootViewType extends View> extends BaseViewHandler<RootViewType> implements IBaseActivityLifeViewHandler
{
    @Override
    protected void initViews ()
    {
        //do @BindView()
    }

    /**
     * 设置标题栏
     *
     * @param toolbar
     * @param title
     */
    protected void setToolbar (Toolbar toolbar, String title, boolean isShowHome)
    {
        if (mContext instanceof BaseComponentActivity)
        {
            ((BaseComponentActivity) mContext).setToolBar(toolbar, title, isShowHome);
        }
    }

    protected void setToolbar (Toolbar toolbar, boolean isShowHome)
    {
        if (mContext instanceof BaseComponentActivity)
        {
            ((BaseComponentActivity) mContext).setToolBar(toolbar, null, isShowHome);
        }
    }

    /**
     * 白天夜间切换
     *
     * @param isNight true-夜，false-白
     */
    protected final void setDayNightMode (boolean isNight)
    {
        if (getContext() instanceof BaseComponentActivity)
        {
            ((BaseComponentActivity) getContext()).setDayNightMode(isNight);
        }
    }

    /**
     * 是否为夜晚模式
     *
     * @return true-Y, false-N
     */
    protected final boolean isNightMode ()
    {
        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        return defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES;
    }
}
