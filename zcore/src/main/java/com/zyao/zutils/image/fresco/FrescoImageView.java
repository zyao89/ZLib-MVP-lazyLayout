package com.zyao.zutils.image.fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyao.zutils.image.ICustomImageView;

/**
 * Class: CustomImageView
 * Description: 自定义ImageView接口
 * Author: zhangyao6
 * Time: 2016/8/17 15:57
 */
public class FrescoImageView extends SimpleDraweeView implements ICustomImageView
{
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;

    private float mCurrentScale = 1f;
    private Matrix mCurrentMatrix;
    private float mMidX;
    private float mMidY;
    private OnClickListener mClickListener;
    /** 是否开启放大 */
    private boolean IsOpenZoom = false;
    /** 状态位 */
    private boolean mIsOnScale = false;

    public FrescoImageView (Context context, GenericDraweeHierarchy hierarchy)
    {
        super(context, hierarchy);
        init();
    }

    public FrescoImageView (Context context)
    {
        super(context);
        init();
    }

    public FrescoImageView (Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FrescoImageView (Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public FrescoImageView (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init ()
    {
        mCurrentMatrix = new Matrix();

        ScaleGestureDetector.OnScaleGestureListener scaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener()
        {
            @Override
            public boolean onScale (ScaleGestureDetector detector)
            {
                float scaleFactor = detector.getScaleFactor();

                mCurrentScale *= scaleFactor;
                if (mMidX == 0f)
                {
                    mMidX = getWidth() / 2f;
                }
                if (mMidY == 0f)
                {
                    mMidY = getHeight() / 2f;
                }
                mCurrentMatrix.postScale(scaleFactor, scaleFactor, mMidX, mMidY);
                invalidate();

                return true;
            }

            @Override
            public boolean onScaleBegin (ScaleGestureDetector detector)
            {
                mIsOnScale = true;
                return super.onScaleBegin(detector);
            }

            @Override
            public void onScaleEnd (ScaleGestureDetector detector)
            {
                super.onScaleEnd(detector);

                if (mCurrentScale < 1f)
                {
                    reset();
                }
                checkBorder();

                mIsOnScale = false;
            }
        };
        mScaleDetector = new ScaleGestureDetector(getContext(), scaleListener);

        GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                if (mIsOnScale)
                {
                    return true;
                }

                if (mCurrentScale > 1f)
                {
                    mCurrentMatrix.postTranslate(-distanceX, -distanceY);
                    invalidate();
                    checkBorder();
                }
                return true;
            }

            @Override
            public boolean onDoubleTap (MotionEvent e)
            {
                if (mMidX == 0f)
                {
                    mMidX = getWidth() / 2f;
                }
                if (mMidY == 0f)
                {
                    mMidY = getHeight() / 2f;
                }
                if (mCurrentScale > 2f)
                {
                    float deltaScale = 1f / mCurrentScale;
                    mCurrentScale = 1f;
                    mCurrentMatrix.postScale(deltaScale, deltaScale, mMidX, mMidY);
                }
                else
                {
                    float deltaScale = 3f / mCurrentScale;
                    mCurrentScale = 3f;
                    mCurrentMatrix.postScale(deltaScale, deltaScale, mMidX, mMidY);
                }
                invalidate();
                checkBorder();
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed (MotionEvent e)
            {
                if (mClickListener != null)
                {
                    mClickListener.onClick(FrescoImageView.this);
                }
                return true;
            }
        };
        mGestureDetector = new GestureDetector(getContext(), gestureListener);
    }

    /**
     * 检查图片边界是否移到view以内
     * 目的是让图片边缘不要移动到view里面
     */
    private boolean checkBorder ()
    {
        RectF rectF = getDisplayRect(mCurrentMatrix);
        boolean reset = false;
        float dx = 0;
        float dy = 0;

        if (rectF.left > 0)
        {
            dx = getLeft() - rectF.left;
            reset = true;
        }
        if (rectF.top > 0)
        {
            dy = getTop() - rectF.top;
            reset = true;
        }
        if (rectF.right < getRight())
        {
            dx = getRight() - rectF.right;
            reset = true;
        }
        if (rectF.bottom < getHeight())
        {
            dy = getHeight() - rectF.bottom;
            reset = true;
        }
        if (reset)
        {
            mCurrentMatrix.postTranslate(dx, dy);
            invalidate();
            return true;
        }
        return false;
    }

    /**
     * Helper method that maps the supplied Matrix to the current Drawable
     *
     * @param matrix - Matrix to map Drawable against
     *
     * @return RectF - Displayed Rectangle
     */
    public RectF getDisplayRect (Matrix matrix)
    {
        RectF rectF = new RectF(getLeft(), getTop(), getRight(), getBottom());
        matrix.mapRect(rectF);
        return rectF;
    }

    @Override
    public void setImageURI (Uri uri)
    {
        reset();
        super.setImageURI(uri);
    }

    @Override
    protected void onDraw (Canvas canvas)
    {
        int saveCount = canvas.save();
        canvas.concat(mCurrentMatrix);
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    public boolean dispatchGestureDetector (MotionEvent event)
    {
        return !mScaleDetector.isInProgress() && mGestureDetector.onTouchEvent(event);
    }

    public boolean dispatchScaleDetector (MotionEvent event)
    {
        return mScaleDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        if (!IsOpenZoom)
        {
            return false;
        }
        mScaleDetector.onTouchEvent(event);
        if (!mScaleDetector.isInProgress())
        {
            mGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    @Override
    public void setImageBitmap (Bitmap bm)
    {
        reset();
        super.setImageBitmap(bm);
    }

    /**
     * Resets the zoom of the attached image.
     * This has no effect if the image has been destroyed
     */
    public void reset ()
    {
        mCurrentMatrix.reset();
        mCurrentScale = 1f;
        invalidate();
    }

    /**
     * 设置放大缩小开关
     *
     * @param isOpenZoom
     */
    public void setIsOpenZoom (boolean isOpenZoom)
    {
        IsOpenZoom = isOpenZoom;
    }

    /**
     * 是否支持放大
     *
     * @return
     */
    public boolean isOpenZoom ()
    {
        return IsOpenZoom;
    }

    public Matrix getImageViewMatrix ()
    {
        return mCurrentMatrix;
    }

    public void setOnClickListener (OnClickListener listener)
    {
        mClickListener = listener;
    }

    public boolean isOnScale ()
    {
        return mIsOnScale;
    }

    public interface OnClickListener
    {
        void onClick (FrescoImageView view);
    }
}
