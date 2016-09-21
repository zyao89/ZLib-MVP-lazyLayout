/**
 * Title: MainContract.java
 * Package: com.zyao.zlib.contract
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zlib.contract;

import com.zyao.zcore2.base.inter.IBasePresenter;
import com.zyao.zcore2.base.inter.IBaseViewHandler;

/**
 * interface: MainContract
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/17 3:43
 */
public interface MainContract
{
    interface IViewHandler extends IBaseViewHandler
    {
        void getValue ();
    }

    interface IPresenter extends IBasePresenter
    {
        void getName ();
    }
}
