/**
 * Title: DensityUtil.java
 * Package: com.zyao.views.zloading
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/18
 */
package com.zyao.views.zloading;

import android.content.Context;

/**
 * Class: DensityUtil
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/18 19:25
 */
public class DensityUtil
{
    public static float dip2px (Context context, float dpValue)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}
