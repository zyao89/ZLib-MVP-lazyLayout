/**
 * Title: SubComponentViewHandler.java
 * Package: com.zyao.zlib.view
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/22
 */
package com.zyao.zlib.view;

import android.view.View;

import com.zyao.zcore2.base.BaseComponentViewHandler;

/**
 * Class: SubComponentViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/22 2:44
 */
public class SubComponentViewHandler extends BaseComponentViewHandler<View>
{
    @Override
    protected void initListeners ()
    {
        System.out.println("mRootView " + mRootView);
    }
}
