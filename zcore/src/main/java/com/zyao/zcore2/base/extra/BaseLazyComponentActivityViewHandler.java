/**
 * Title: BaseLazyComponentActivityViewHandler.java
 * Package: com.zyao.zcore2.base.extra
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/09/23
 */
package com.zyao.zcore2.base.extra;

import android.view.View;

/**
 * Class: BaseLazyComponentActivityViewHandler
 * Description: 带标题和Fragment的懒人模式
 * Author: Zyao89
 * Time: 2016/9/23 18:47
 */
public class BaseLazyComponentActivityViewHandler<RootViewType extends View> extends BaseTitleBarComponentActivityViewHandler<RootViewType>
{
    @Override
    public final int getResourceId ()
    {
        return 0;
    }
}
