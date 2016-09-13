/**
 * Title: MainActivityViewHandler.java
 * Package: com.zyao.zlib
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/13
 */
package com.zyao.zlib;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyao.zcore.BaseActivityViewHandler;

import butterknife.BindView;

/**
 * Class: MainActivityViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/13 16:06
 */
public class MainActivityViewHandler extends BaseActivityViewHandler<RelativeLayout> implements IMainActivityViewHandler
{
    @BindView(R.id.text_zyao)
    TextView mTextView;

    @Override
    public int getResourceId ()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews ()
    {
        mTextView.setText("1111");
    }
}
