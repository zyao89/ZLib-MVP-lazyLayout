package com.zyao.zcore.view;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Class: BaseViewHandler
 * Description: ViewHandler抽象类
 * Author: Zyao89
 * Time: 2016/7/20 18:10
 */
public abstract class BaseViewHandler<ViewType extends View> extends BaseRootViewHandler<ViewType>
{
    public BaseViewHandler (@NonNull ViewType rootView)
    {
        onCreate(rootView);
    }

    public void init ()
    {
        if (isAlreadyExist())
        {
            throw new IllegalStateException("重复初始化..., 请去掉 init 操作..");
        }
        onViewCreated();
    }
}
