/**
 * Title: BaseComponentViewHandler.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.zyao.zcore.view.BaseViewHandler;

/**
 * Class: BaseComponentViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/17 14:05
 */
public class BaseComponentViewHandler<ViewType extends View> extends BaseViewHandler<ViewType>
{
    public BaseComponentViewHandler (@NonNull ViewType rootView)
    {
        super(rootView);
    }

    @Override
    protected void initViews ()
    {
        //do @BindView()
    }
}
