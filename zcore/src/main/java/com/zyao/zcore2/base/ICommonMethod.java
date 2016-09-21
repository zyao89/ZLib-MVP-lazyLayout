/**
 * Title: ICommonMethod.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Interface: ICommonMethod
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/21 19:31
 */
interface ICommonMethod
{
    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    void gotoNewActivityAndFinish (@NonNull Class<?> cls);

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    void gotoNewActivityAndFinish (@NonNull Class<?> cls, Bundle bundle);

    /**
     * 跳转新的Activity
     *
     * @param cls
     */
    void gotoNewActivity (@NonNull Class<?> cls);

    /**
     * 跳转新的Activity
     *
     * @param cls
     * @param bundle
     */
    void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle);
}
