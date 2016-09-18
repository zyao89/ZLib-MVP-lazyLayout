/**
 * Title: LoadingView.java
 * Package: com.zyao.views.zloading
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/18
 */
package com.zyao.views.zloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zyao.views.zloading.render.LoadingDrawable;
import com.zyao.views.zloading.render.LoadingRenderer;
import com.zyao.views.zloading.render.LoadingRendererFactory;

/**
 * Class: LoadingView
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/18 19:26
 */
public class LoadingView extends ImageView
{
    private LoadingDrawable mLoadingDrawable;

    public LoadingView (Context context)
    {
        super(context);
    }

    public LoadingView (Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs (Context context, AttributeSet attrs)
    {
        try
        {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
            int loadingRendererId = ta.getInt(R.styleable.LoadingView_loading_renderer, 0);
            LoadingRenderer loadingRenderer = LoadingRendererFactory.createLoadingRenderer(context, loadingRendererId);
            setLoadingRenderer(loadingRenderer);
            ta.recycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setLoadingRenderer (LoadingRenderer loadingRenderer)
    {
        mLoadingDrawable = new LoadingDrawable(loadingRenderer);
        setImageDrawable(mLoadingDrawable);
    }

    @Override
    protected void onAttachedToWindow ()
    {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow ()
    {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    protected void onVisibilityChanged (View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE)
        {
            startAnimation();
        }
        else
        {
            stopAnimation();
        }
    }

    private void startAnimation ()
    {
        if (mLoadingDrawable != null)
        {
            mLoadingDrawable.start();
        }
    }

    private void stopAnimation ()
    {
        if (mLoadingDrawable != null)
        {
            mLoadingDrawable.stop();
        }
    }
}
