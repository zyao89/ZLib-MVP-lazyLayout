/**
 * Title: MainActivityViewHandler.java
 * Package: com.zyao.zlib
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/13
 */
package com.zyao.zlib.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zyao.zcore2.base.extra.BaseLazyCoordinatorComponentActivityViewHandler;
import com.zyao.zcore2.base.extra.BaseLazyViewPagerComponentActivityViewHandler;
import com.zyao.zcore2.helper.RetrofitHelper;
import com.zyao.zlib.R;
import com.zyao.zlib.contract.MainContract;
import com.zyao.zlib.fragment.MainFragment;
import com.zyao.zutils.Z;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Class: MainActivityViewHandler
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/13 16:06
 */
public class MainActivityViewHandler_2 extends BaseLazyViewPagerComponentActivityViewHandler<CoordinatorLayout> implements MainContract.IViewHandler
{
//    @BindView(R.id.drawer)
//    DrawerLayout mDrawerLayout;
//    @BindView(R.id.tool_bar)
//    Toolbar mToolbar;
//    @BindView(R.id.navigation)
//    NavigationView mNavigationView;
    //    @BindView(R.id.view_search)
    //    MaterialSearchView mSearchView;

//    @BindView(R.id.recyclerView)
//    RecyclerView mRecyclerView;

    @Inject
    MainActivityViewHandler_2 (RetrofitHelper retrofitHelper, Activity activity)
    {
        System.out.println("RetrofitHelper: " + retrofitHelper);
        System.out.println("Activity: " + activity);
    }

    @Override
    public void resetDefaultState (Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
//            setRootFragmentContainerId(R.id.fl_main_content);
        }
        super.resetDefaultState(savedInstanceState);
    }

    @Override
    protected void initSubViewHandler ()
    {
        SubComponentViewHandler subViewHandler = createSubViewHandler(SubComponentViewHandler.class, mRootView);

        System.out.println("subViewHandler: " + subViewHandler);
    }

    @Override
    protected void initListeners ()
    {
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v)
//            {
//                setDayNightMode(!isNightMode());
//                //                mZLoadingView.setVisibility(mZLoadingView.isShown()?View.GONE:View.VISIBLE);
//            }
//        });
    }

    @Override
    protected void initDefaultData ()
    {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setHasFixedSize(true);
//
//        final ArrayList<String> strings = new ArrayList<>();
//        for (int i = 1; i < 33; i++) {
//            String name = "Mom say click text will see a poem  " + i + " :) ";
//            strings.add(name);
//        }
//
//        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                int dp16 = Z.utils().density().dip2px(16);
//                TextView textView = new TextView(mContext);
//                textView.setPadding(dp16, dp16, dp16, dp16);
//
//                return new RecyclerView.ViewHolder(textView) {
//                    @Override
//                    public String toString() {
//                        return super.toString();
//                    }
//                };
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                TextView textView = (TextView) holder.itemView;
//                textView.setText(strings.get(position));
//
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "string: ", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public int getItemCount() {
//                return strings.size();
//            }
//        });
    }

    @Override
    public void getValue ()
    {

    }

    @Override
    protected void initContentViewPager (final CenterViewPagerAdapter viewPagerAdapter)
    {
        viewPagerAdapter.addFragment(new MainFragment(), "123456");
        viewPagerAdapter.addFragment(new MainFragment(), "456789");
        viewPagerAdapter.addFragment(new MainFragment(), "987654");
        viewPagerAdapter.addFragment(new MainFragment(), "654321");
    }
}
