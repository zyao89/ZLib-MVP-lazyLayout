/**
 * Title: MainPresenter.java
 * Package: com.zyao.zlib
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/17
 */
package com.zyao.zlib.presenter;

import android.os.Bundle;

import com.zyao.zcore2.base.BaseComponentPresenter;
import com.zyao.zlib.contract.MainContract;

import javax.inject.Inject;

/**
 * Class: MainPresenter
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/17 2:43
 */
public class MainFragmentPresenter extends BaseComponentPresenter<MainContract.IFragmentViewHandler> implements MainContract.IFragmentPresenter
{
    @Inject
    MainFragmentPresenter ()
    {
    }

    @Override
    protected void initData (Bundle savedInstanceState)
    {

    }

    @Override
    protected void initListener ()
    {

    }

    @Override
    public void getName ()
    {

    }
}
