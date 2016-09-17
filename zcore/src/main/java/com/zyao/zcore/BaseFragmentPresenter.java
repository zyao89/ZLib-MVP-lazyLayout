package com.zyao.zcore;

import android.support.annotation.Nullable;

import com.zyao.zcore.view.IBaseUIViewHandler;

/**
 * Class: BaseFragmentPresenter
 * Description: FragmentPresenter基类
 * Author: Zyao89
 * Time: 2016/7/28 11:13
 */
public abstract class BaseFragmentPresenter<ViewHandler extends IBaseUIViewHandler> extends BasePresenter<ViewHandler>
{
    private final static String TAG = "BaseFragmentPresenter";

    public BaseFragmentPresenter (@Nullable ViewHandler rootViewHandler)
    {
        super(rootViewHandler);
    }

    @Override
    protected void initSubPresenter ()
    {

    }
}
