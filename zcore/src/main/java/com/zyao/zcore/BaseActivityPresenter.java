package com.zyao.zcore;

import android.support.annotation.Nullable;

import com.zyao.zcore.view.IBaseUIViewHandler;

/**
 * Class: BaseActivityPresenter
 * Description: ActivityPresenter基类
 * Author: Zyao89
 * Time: 2016/7/28 11:30
 */
public abstract class BaseActivityPresenter<ViewHandler extends IBaseUIViewHandler> extends BasePresenter<ViewHandler>
{
    private final static String TAG = "BaseActivityPresenter";

    public BaseActivityPresenter (@Nullable ViewHandler rootViewHandler)
    {
        super(rootViewHandler);
    }

    @Override
    protected void initSubPresenter ()
    {

    }
}
