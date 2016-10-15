/**
 * Title: BaseLazyCoordinatorComponentActivityViewHandler.java
 * Package: com.zyao.zcore2.base.extra
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/09/23
 */
package com.zyao.zcore2.base.extra;

import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.zyao.zcore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: BaseLazyCoordinatorComponentActivityViewHandler
 * Description: 带标题和ViewPager的懒人模式
 * Author: Zyao89
 * Time: 2016/9/23 18:47
 */
public abstract class BaseLazyViewPagerComponentActivityViewHandler extends BaseTitleBarComponentActivityViewHandler<CoordinatorLayout>
{
    private FrameLayout mContentsFrameLayout;
    private ViewPager mContentsViewPager;
    private TabLayout mTabLayout;

    @Override
    public final int getResourceId ()
    {
        return R.layout.z_lazy_design_tab_pager_layout;
    }

    /** 添加View时调用 */
    protected abstract void initContentViewPager (final CenterViewPagerAdapter viewPagerAdapter);

    @Override
    protected void initViews ()
    {
        super.initViews();
        initAppBarTabLayout();
        initDesignContentsLayout();
        initDesignContentsViewPager();
    }

    private void initAppBarTabLayout ()
    {
        View view = findViewById(R.id.z_title_bar_tabLayout);
        if (view == null)
        {
            return;
        }
        if (view instanceof TabLayout)
        {
            mTabLayout = (TabLayout) view;
        }
        if (mTabLayout == null)
        {
            throw new RuntimeException("mContentsFrameLayout is null...");
        }
    }

    private void initDesignContentsLayout ()
    {
        View view = findViewById(R.id.z_lazy_design_contents_layout);
        if (view == null)
        {
            return;
        }
        if (view instanceof FrameLayout)
        {
            mContentsFrameLayout = (FrameLayout) view;
        }
        if (mContentsFrameLayout == null)
        {
            throw new RuntimeException("mContentsFrameLayout is null...");
        }
    }

    private void initDesignContentsViewPager ()
    {
        View view = findViewById(R.id.z_lazy_design_contents_viewpager);
        if (view == null)
        {
            return;
        }
        if (view instanceof ViewPager)
        {
            mContentsViewPager = (ViewPager) view;
        }
        if (mContentsViewPager == null)
        {
            throw new RuntimeException("mContentsViewPager is null...");
        }
        CenterViewPagerAdapter viewPagerAdapter = new CenterViewPagerAdapter(getFragmentManager());
        initContentViewPager(viewPagerAdapter);
        mContentsViewPager.setAdapter(viewPagerAdapter);//设置适配器
        if (isTabLayoutExist())
        {
            mTabLayout.setupWithViewPager(mContentsViewPager);
        }
    }

    private boolean isContentsViewPagerExist ()
    {
        return mContentsViewPager != null;
    }

    private boolean isTabLayoutExist ()
    {
        return mTabLayout != null;
    }

    /**
     * ViewPagerAdapter
     */
    protected class CenterViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragments = new ArrayList<>();//添加的Fragment的集合
        private final List<String> mFragmentsTitles = new ArrayList<>();//每个Fragment对应的title的集合

        private CenterViewPagerAdapter (FragmentManager fm)
        {
            super(fm);
        }

        /**
         * @param fragment      添加Fragment
         * @param fragmentTitle Fragment的标题，即TabLayout中对应Tab的标题
         */
        public void addFragment (Fragment fragment, String fragmentTitle)
        {
            mFragments.add(fragment);
            if (isTabLayoutExist())
            {
                mFragmentsTitles.add(fragmentTitle);
            }
        }

        public void addFragment (Fragment fragment, @StringRes int stringId)
        {
            mFragments.add(fragment);
            if (isTabLayoutExist())
            {
                String fragmentTitle = mContext.getResources().getString(stringId);
                mFragmentsTitles.add(fragmentTitle);
            }
        }

        @Override
        public Fragment getItem (int position)
        {
            //得到对应position的Fragment
            return mFragments.get(position);
        }

        @Override
        public int getCount ()
        {
            //返回Fragment的数量
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle (int position)
        {
            return mFragmentsTitles.get(position);
        }
    }
}
