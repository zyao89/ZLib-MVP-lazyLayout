package com.zyao.zlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zyao.zcore.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<IMainActivityViewHandler>
{

    @Override
    protected IMainActivityViewHandler newViewHandler ()
    {
        return new MainActivityViewHandler();
    }

    @Override
    protected void initPresenter (Bundle savedInstanceState)
    {

    }

    @Override
    protected void initListener ()
    {

    }

    @Override
    protected void initDefaultData ()
    {

    }
}
