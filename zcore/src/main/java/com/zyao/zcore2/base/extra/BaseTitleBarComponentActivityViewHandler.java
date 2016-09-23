/**
 * Title: BaseTitleBarComponentActivityViewHandler.java
 * Package: com.zyao.zcore2.base.extra
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/09/23
 */
package com.zyao.zcore2.base.extra;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zyao.zcore.R;
import com.zyao.zcore2.base.BaseComponentActivityViewHandler;

/**
 * Class: BaseTitleBarComponentActivityViewHandler
 * Description: 带有标题栏的ActivityViewHandler
 * Author: Zyao89
 * Time: 2016/9/23 15:47
 */
public abstract class BaseTitleBarComponentActivityViewHandler<RootViewType extends View> extends BaseComponentActivityViewHandler<RootViewType>
{
    private Toolbar mTitleBar;
    private AppBarLayout mAppBarLayout;
    private FrameLayout mAppBarBannerLayout;
    private ImageView mAppBarBannerImageView;

    @Override
    protected void initViews ()
    {
        initTitleBar();
    }

    private void initTitleBar ()
    {
        View view = findViewById(R.id.z_title_bar);
        if (view == null)
        {
            return;
        }
        if (view instanceof Toolbar)
        {
            mTitleBar = (Toolbar) view;
        }
        if (mTitleBar == null)
        {
            return;
        }
        setToolbar(mTitleBar, true);
    }

    private void initAppBarLayout ()
    {
        View view = findViewById(R.id.z_title_app_bar_layout);
        if (view == null)
        {
            mAppBarLayout = null;
            return;
        }
        if (view instanceof AppBarLayout)
        {
            mAppBarLayout = (AppBarLayout) view;
        }
        if (mAppBarLayout == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            this.mAppBarLayout.setElevation(6.6f);
        }
    }

    private void initAppBarBannerLayout ()
    {
        View view = findViewById(R.id.z_title_app_bar_banner);
        if (view == null)
        {
            mAppBarBannerLayout = null;
            return;
        }
        if (view instanceof FrameLayout)
        {
            mAppBarBannerLayout = (FrameLayout) view;
        }
        if (mAppBarBannerLayout == null || mTitleBar == null)
        {
            return;
        }
        super.setToolbar(mTitleBar, false);
    }

    private void initAppBarBannerImageView ()
    {
        if (mAppBarBannerLayout == null)
        {
            return;
        }
        View view = findViewById(R.id.z_title_app_bar_banner_imageView);
        if (view == null)
        {
            mAppBarBannerImageView = null;
            return;
        }
        if (view instanceof ImageView)
        {
            mAppBarBannerImageView = (ImageView) view;
        }
    }

    @Override
    protected void setToolbar (@Nullable Toolbar titleBar, boolean isShowHome)
    {
        super.setToolbar(titleBar, isShowHome);
        initAppBarLayout();
        initAppBarBannerLayout();
        initAppBarBannerImageView();
    }

    protected Toolbar getTitleBar ()
    {
        return mTitleBar;
    }

    /**
     * 隐藏整个标题栏
     */
    protected void hideTitleBar ()
    {
        if (mAppBarLayout != null)
        {
            mAppBarLayout.setVisibility(View.GONE);
        }
        else if (mTitleBar != null)
        {
            mTitleBar.setVisibility(View.GONE);
        }
    }

    /**
     * 显示整个标题栏
     */
    protected void showTitleBar ()
    {
        if (mAppBarLayout != null)
        {
            mAppBarLayout.setVisibility(View.VISIBLE);
        }
        else if (mTitleBar != null)
        {
            mTitleBar.setVisibility(View.VISIBLE);
        }
    }

    protected void setTitleBarText (String titleBarText)
    {
        if (mTitleBar != null)
        {
            mTitleBar.setTitle(titleBarText);
        }
    }

    protected void setTitleBarText (@StringRes int resId)
    {
        if (mTitleBar != null)
        {
            mTitleBar.setTitle(resId);
        }
    }

    protected void setTitleBarBannerImageView (@DrawableRes int resId)
    {
        if (mAppBarBannerImageView == null)
        {
            return;
        }
        mAppBarBannerImageView.setImageResource(resId);
    }

    protected void setTitleBarBannerImageView (@Nullable Drawable drawable)
    {
        if (mAppBarBannerImageView == null)
        {
            return;
        }
        mAppBarBannerImageView.setImageDrawable(drawable);
    }

    protected void setTitleBarBannerImageView (@Nullable Bitmap bm)
    {
        if (mAppBarBannerImageView == null)
        {
            return;
        }
        mAppBarBannerImageView.setImageBitmap(bm);
    }

    protected void setTitleBarBannerCollapsingView (@Nullable View view)
    {
        if (mAppBarBannerLayout == null)
        {
            return;
        }
        mAppBarBannerLayout.removeAllViews();
        mAppBarBannerLayout.addView(view);
    }
}
