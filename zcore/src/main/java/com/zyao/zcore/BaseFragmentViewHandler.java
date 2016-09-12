package com.zyao.zcore;

import android.app.Activity;
import android.view.View;

/**
 * Class: BaseFragmentViewHandler
 * Description: BaseFragmentViewHandler抽象类
 * Author: Zyao89
 * Time: 2016/7/20 18:10
 */
public abstract class BaseFragmentViewHandler<ViewType extends View> extends BaseRootViewHandler<ViewType> implements IBaseFragmentViewHandler
{
    @Override
    public void onAttach (Activity activity)
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseFragmentViewHandler)
                {
                    ((IBaseFragmentViewHandler) subViewHandler).onAttach(activity);
                }
            }
        }
    }

    @Override
    public void onActivityCreated ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseFragmentViewHandler)
                {
                    ((IBaseFragmentViewHandler) subViewHandler).onActivityCreated();
                }
            }
        }
    }

    @Override
    public void onDestroyView ()
    {
        if (mSubViewHandlerLinkedQueue != null)
        {
            for (IBaseRootViewHandler subViewHandler : mSubViewHandlerLinkedQueue)
            {
                if (subViewHandler instanceof IBaseFragmentViewHandler)
                {
                    ((IBaseFragmentViewHandler) subViewHandler).onDestroyView();
                }
            }
        }
    }
}
