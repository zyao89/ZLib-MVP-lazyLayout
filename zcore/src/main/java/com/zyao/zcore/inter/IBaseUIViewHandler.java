package com.zyao.zcore.inter;

/**
 * Interface: IBaseUIViewHandler
 * Description: ViewHandler对外公共接口
 * Author: Zyao89
 * Time: 2016/7/20 18:59
 */
public interface IBaseUIViewHandler
{
    boolean isShown ();

    void show ();

    void dismiss ();
}
