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

import com.zyao.zcore2.base.inter.IBaseViewHandler;
import com.zyao.zutils.Z;
import com.zyao.zutils.anim.CircularAnimUtilsImpl;

/**
 * Class: BaseComponentViewHandler
 * Description: ViewHandler
 * Author: Zyao89
 * Time: 2016/9/17 14:05
 */
public abstract class BaseComponentViewHandler<ViewType extends View> extends BaseViewHandler<ViewType> implements IBaseViewHandler
{
    @Override
    protected void initViews ()
    {
        //do @BindView()
    }

    /**
     * 构造带动画显示View
     *
     * @param animView
     *
     * @return 构造类
     */
    protected final CircularAnimUtilsImpl.VisibleBuilder showViewByAnim (@NonNull View animView)
    {
        return Z.animUtils().show(animView);
    }

    /**
     * 构造带动画隐藏View
     *
     * @param animView
     *
     * @return 构造类
     */
    protected final CircularAnimUtilsImpl.VisibleBuilder hideViewByAnim (@NonNull View animView)
    {
        return Z.animUtils().hide(animView);
    }

    /**
     * 带动画显示View
     *
     * @param animView
     */
    protected final void showViewGoByAnim (View animView)
    {
        Z.animUtils().showGo(animView);
    }

    /**
     * 带动画显示View
     *
     * @param animView
     * @param triggerView
     */
    protected final void showViewGoByAnim (@NonNull View animView, @NonNull View triggerView)
    {
        Z.animUtils().showGo(animView, triggerView);
    }

    /**
     * 带动画显示View
     *
     * @param animView
     * @param triggerView
     * @param listener
     */
    protected final void showViewGoByAnim (@NonNull View animView, @NonNull View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener)
    {
        Z.animUtils().showGo(animView, triggerView, listener);
    }

    /**
     * 带动画隐藏View
     *
     * @param animView
     */
    protected final void hideViewGoByAnim (@NonNull View animView)
    {
        Z.animUtils().hideGo(animView);
    }

    /**
     * 带动画隐藏View
     *
     * @param animView
     * @param triggerView
     */
    protected final void hideViewGoByAnim (@NonNull View animView, @NonNull View triggerView)
    {
        Z.animUtils().hideGo(animView, triggerView);
    }

    /**
     * 带动画隐藏View
     *
     * @param animView
     * @param triggerView
     * @param listener
     */
    protected final void hideViewGoByAnim (@NonNull View animView, @NonNull View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener)
    {
        Z.animUtils().hideGo(animView, triggerView, listener);
    }
}
