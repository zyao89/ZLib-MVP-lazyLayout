/**
 * Title: BaseComponentPresenter.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zcore2.base;

import com.zyao.zcore.view.IBaseRootViewHandler;
import com.zyao.zutils.TaskController;
import com.zyao.zutils.Z;

/**
 * Class: BaseComponentPresenter
 * Description: ComponentPresenter基类
 * Author: Zyao89
 * Time: 2016/9/17 2:22
 */
public abstract class BaseComponentPresenter<ViewHandler extends IBaseRootViewHandler>
{
    protected final String TAG = this.getClass().getSimpleName();
    protected TaskController mHandler;
    protected ViewHandler mViewHandler;

    /* package */ void attachView (ViewHandler viewHandler)
    {
        mViewHandler = viewHandler;
        this.mHandler = Z.task();
    }

    /* package */ void detachView ()
    {
        doExit();
        this.mViewHandler = null;
    }

    /**
     * 初始化操作
     */
    protected abstract void initData ();

    protected abstract void initListener ();

    /**
     * 子类重写此方法
     */
    protected void initDefaultData ()
    {

    }

    /**
     * 退出操作
     */
    protected void doExit ()
    {

    }
}
