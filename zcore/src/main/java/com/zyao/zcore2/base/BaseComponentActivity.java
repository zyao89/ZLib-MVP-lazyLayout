/**
 * Title: BaseComponentActivity.java
 * Package: com.zyao.zcore
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zyao.zcore.R;
import com.zyao.zcore.anim.FragmentAnimator;
import com.zyao.zcore.support.SupportActivity;
import com.zyao.zcore2.base.inter.IBasePresenter;
import com.zyao.zcore2.base.inter.IBaseViewHandler;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.module.ActivityModule;
import com.zyao.zutils.Z;
import com.zyao.zutils.anim.CircularAnimUtilsImpl;

import javax.inject.Inject;

/**
 * Class: BaseComponentActivity
 * Description: BaseComponentActivity基类
 * Author: Zyao89
 * Time: 2016/9/16 23:17
 */
public abstract class BaseComponentActivity<ViewHandler extends IBaseViewHandler, Presenter extends IBasePresenter> extends SupportActivity implements ICommonMethod
{
    protected final String TAG = this.getClass().getSimpleName();
    private final BasePresenterFactory mSubPresenterBasePresenterFactory = BasePresenterFactory.create();
    protected View mRootView;
    protected Context mContext;

    @Inject
    protected ViewHandler mViewHandler;
    @Inject
    protected Presenter mPresenter;
    private BaseComponentActivityViewHandler _mViewHandler;
    private BaseComponentPresenter<ViewHandler> _Presenter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initComponent(getApplicationComponent(), getActivityModule());
        if (mViewHandler instanceof BaseComponentActivityViewHandler)
        {
            _mViewHandler = (BaseComponentActivityViewHandler) mViewHandler;
        }
        if (mPresenter instanceof BaseComponentPresenter)
        {
            _Presenter = (BaseComponentPresenter<ViewHandler>) mPresenter;
        }

        this.createRootView();

        _mViewHandler.resetDefaultState(savedInstanceState);//恢复

        this.onCreateRootFragment(savedInstanceState);

        if (!Z.activityCtrl().containsActivity(this))
        {
            Z.activityCtrl().addActivity(this);
        }

        mContext = this;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator ()
    {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        //        return new DefaultHorizontalAnimator();
        // 设置自定义动画
        //        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    @Override
    public void onBackPressedSupport ()
    {
        if (isExistViewHandler())
        {
            if (_mViewHandler.onBackPressed())
            {
                return;
            }
        }
        super.onBackPressedSupport();
    }

    /**
     * 创建RootFragment
     *
     * @param savedInstanceState
     */
    protected void onCreateRootFragment (Bundle savedInstanceState)
    {

    }

    /**
     * 重新创建RootView
     */
    private void createRootView ()
    {
        if (isExistViewHandler())
        {
            final int resourceId = _mViewHandler.getResourceId();
            if (resourceId < 0)
            {
                throw new IllegalStateException("resourceId < 0 操作失败，引用不正规...");
            }
            setContentView(resourceId);
            mRootView = getWindow().getDecorView();
            //            mRootView = getLayoutInflater().inflate(resourceId, null, false);
            if (mRootView == null)
            {
                throw new IllegalStateException("mRootView is null...");
            }
            _mViewHandler.onCreate(mRootView);
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        if (isExistViewHandler())
        {
            _mViewHandler.onViewCreated();
        }
        else
        {
            throw new IllegalStateException("mViewHandler is null...");
        }
        onNewPresenter();
        initPresenter(savedInstanceState);
        initListener();
        initDefaultData();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (isExistViewHandler())
        {
            _mViewHandler.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy();
        onDestroyPresenter();
        if (isExistViewHandler())
        {
            _mViewHandler.onDestroy();
        }
        Z.activityCtrl().removeActivity(this);
    }

    /**
     * 获取RootView
     *
     * @return RootView
     */
    public View getRootView ()
    {
        return mRootView;
    }

    /**
     * 获取ViewHandler
     *
     * @return
     */
    public ViewHandler getViewHandler ()
    {
        return mViewHandler;
    }

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    @Override
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls)
    {
        this.gotoNewActivity(cls);
        this.finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    @Override
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls, Bundle bundle)
    {
        this.gotoNewActivity(cls, bundle);
        this.finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    @Override
    public void gotoNewActivityAndFinish (@NonNull Class<?> cls, Intent intent)
    {
        this.gotoNewActivity(cls, intent);
        this.finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 跳转新的Activity
     *
     * @param cls
     */
    @Override
    public void gotoNewActivity (@NonNull Class<?> cls)
    {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        this.startActivity(intent);
    }

    /**
     * 跳转新的Activity
     *
     * @param cls
     */
    @Override
    public void gotoNewActivity (@NonNull Class<?> cls, Intent intent)
    {
        intent.setClass(this, cls);
        this.startActivity(intent);
    }

    /**
     * 跳转新的Activity，带返回值
     *
     * @param cls
     * @param intent
     * @param requestCode
     */
    @Override
    public void gotoNewActivityForResult (@NonNull Class<?> cls, Intent intent, int requestCode)
    {
        intent.setClass(this, cls);
        this.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转新的Activity，带返回值
     *
     * @param cls
     * @param intent
     * @param bundle
     * @param requestCode
     */
    @Override
    public void gotoNewActivityForResult (@NonNull Class<?> cls, Intent intent, Bundle bundle, int requestCode)
    {
        intent.setClass(this, cls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            this.startActivityForResult(intent, requestCode, bundle);
        }
        else
        {
            intent.putExtras(bundle);
            this.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 跳转新的Activity
     *
     * @param cls
     * @param bundle
     */
    @Override
    public void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, cls);
        this.startActivity(intent);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Intent intent)
    {
        Z.animUtils().fullActivityGo(this, triggerView, intent);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Intent intent)
    {
        Z.animUtils().fullActivityGo(this, triggerView, intent);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass)
    {
        Z.animUtils().fullActivityGo(this, triggerView, targetClass);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, targetClass);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, targetClass);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, colorOrImageRes, targetClass);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, targetClass, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes, long durationMills)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, colorOrImageRes, targetClass, durationMills);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, targetClass, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, long durationMills)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, targetClass, durationMills);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent)
    {
        Z.animUtils().fullActivityGo(this, triggerView, targetClass, intent);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, targetClass, intent);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, targetClass, intent);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, colorOrImageRes, targetClass, intent);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, targetClass, intent, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, long durationMills)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, targetClass, intent, durationMills);
    }

    @Override
    public void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, targetClass, intent, durationMills);
    }

    @Override
    public void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes, long durationMills)
    {
        Z.animUtils().fullActivityGoAndFinish(this, triggerView, colorOrImageRes, targetClass, intent, durationMills);
    }

    @Override
    public void gotoNewActivityForResultByAnim (@NonNull View triggerView, @NonNull final Class<?> targetClass, final int requestCode)
    {
        Z.animUtils().fullActivityGo(this, triggerView, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                Intent intent = new Intent(mContext, targetClass);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (@NonNull View triggerView, @NonNull final Class<?> targetClass, @NonNull final Intent intent, final int requestCode)
    {
        Z.animUtils().fullActivityGo(this, triggerView, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final int requestCode, int colorOrImageRes)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final int requestCode, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, durationMills, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final int requestCode, int colorOrImageRes, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, durationMills, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Bundle bundle, final int requestCode)
    {
        Z.animUtils().fullActivityGo(this, triggerView, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                Intent intent = new Intent();
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode, bundle);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final Bundle bundle, final int requestCode)
    {
        Z.animUtils().fullActivityGo(this, triggerView, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode, bundle);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final Bundle bundle, final int requestCode, int colorOrImageRes)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode, bundle);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final Bundle bundle, final int requestCode, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, durationMills, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode, bundle);
            }
        });
    }

    @Override
    public void gotoNewActivityForResultByAnim (View triggerView, @NonNull final Class<?> targetClass, final Intent intent, final Bundle bundle, final int requestCode, int colorOrImageRes, long durationMills)
    {
        Z.animUtils().fullActivityGo(this, triggerView, colorOrImageRes, durationMills, new CircularAnimUtilsImpl.OnAnimationEndListener()
        {
            @Override
            public void onAnimationEnd ()
            {
                intent.setClass(mContext, targetClass);
                startActivityForResult(intent, requestCode, bundle);
            }
        });
    }

    @Override
    public void loadRootFragment (BaseComponentFragment toFragment)
    {
        if (!isExistViewHandler())
        {
            return;
        }
        int rootFragmentContainerId = _mViewHandler.getRootFragmentContainerId();
        if (rootFragmentContainerId > 0)
        {
            loadRootFragment(rootFragmentContainerId, toFragment);
        }
    }

    @Override
    public void replaceLoadRootFragment (BaseComponentFragment toFragment, boolean addToBack)
    {
        if (!isExistViewHandler())
        {
            return;
        }
        int rootFragmentContainerId = _mViewHandler.getRootFragmentContainerId();
        if (rootFragmentContainerId > 0)
        {
            replaceLoadRootFragment(rootFragmentContainerId, toFragment, addToBack);
        }
    }

    @Override
    public void loadMultipleRootFragment (int showPosition, BaseComponentFragment... toFragments)
    {
        if (!isExistViewHandler())
        {
            return;
        }
        int rootFragmentContainerId = _mViewHandler.getRootFragmentContainerId();
        if (rootFragmentContainerId > 0)
        {
            loadMultipleRootFragment(rootFragmentContainerId, toFragments);
        }
    }

    protected <T extends IBasePresenter, V extends IBaseViewHandler> T createSubPresenter (Class<T> clazz, V rootViewHandler)
    {
        return mSubPresenterBasePresenterFactory.createSubPresenter(clazz, rootViewHandler);
    }

    protected <T extends IBasePresenter> T getSubPresenter (Class<T> presenter)
    {
        return mSubPresenterBasePresenterFactory.getSubPresenter(presenter);
    }

    private void onNewPresenter ()
    {
        if (isExistPresenter())
        {
            _Presenter.attachView(mViewHandler);
        }
        else
        {
            Z.log().e(TAG, "【 ERROR 】 mPresenter is null...");
        }
    }

    private void onDestroyPresenter ()
    {
        if (isExistPresenter())
        {
            _Presenter.detachView();
        }
        mSubPresenterBasePresenterFactory.onDestroyPresenter();
    }

    private void initPresenter (Bundle savedInstanceState)
    {
        if (isExistPresenter())
        {
            _Presenter.initDataAndSubPresenterData(savedInstanceState);
        }
        mSubPresenterBasePresenterFactory.initPresenter(savedInstanceState);
    }

    private void initListener ()
    {
        if (isExistPresenter())
        {
            _Presenter.initListenerAndSubPresenterListener();
        }
        mSubPresenterBasePresenterFactory.initListener();
    }

    private void initDefaultData ()
    {
        if (isExistPresenter())
        {
            _Presenter.initDefaultDataAndSubPresenterDefaultData();
        }
        mSubPresenterBasePresenterFactory.initDefaultData();
    }

    /**
     * 判断ViewHandler是否存在
     *
     * @return true-存在， false - 不存在
     */
    protected boolean isExistViewHandler ()
    {
        return mViewHandler != null && _mViewHandler != null;
    }

    protected boolean isExistPresenter ()
    {
        return mPresenter != null && _Presenter != null;
    }

    protected ApplicationComponent getApplicationComponent ()
    {
        return Z.appComponent();
    }

    protected ActivityModule getActivityModule ()
    {
        return new ActivityModule(this);
    }

    /**
     * 设置标题栏
     *
     * @param toolbar
     * @param title
     */
    protected void setToolBar (Toolbar toolbar, String title)
    {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 设置夜间模式
     *
     * @param isNight
     */
    protected void setDayNightMode (boolean isNight)
    {
        if (isNight)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

    /**
     * 是否为夜间模式
     *
     * @return
     */
    protected boolean isNightMode ()
    {
        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        return defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES;
    }

    /**
     * 初始化组件
     *
     * @param applicationComponent appComponent
     * @param activityModule       activity
     */
    protected abstract void initComponent (ApplicationComponent applicationComponent, ActivityModule activityModule);

}
