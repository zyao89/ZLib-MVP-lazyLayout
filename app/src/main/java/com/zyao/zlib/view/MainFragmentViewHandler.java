/**
 * Title: MainFragmentViewHandler.java
 * Package: com.zyao.zlib.view
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/22
 */
package com.zyao.zlib.view;

import android.widget.LinearLayout;

import com.zyao.views.zloading.LoadingView;
import com.zyao.zcore2.base.BaseComponentFragmentViewHandler;
import com.zyao.zlib.R;
import com.zyao.zlib.contract.MainContract;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Class: MainFragmentViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/22 10:19
 */
public class MainFragmentViewHandler extends BaseComponentFragmentViewHandler<LinearLayout> implements MainContract.IFragmentViewHandler
{
    @BindView(R.id.loading)
    LoadingView mZLoadingView;

    @Inject
    public MainFragmentViewHandler ()
    {
    }

    @Override
    public int getResourceId ()
    {
        return R.layout.fragment_main;
    }

    @Override
    public void getValue ()
    {

    }
}
