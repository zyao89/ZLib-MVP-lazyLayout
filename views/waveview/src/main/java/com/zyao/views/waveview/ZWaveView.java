package com.zyao.views.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 水波纹特效
 * Created by Zyao89 on 2016/10/16.
 */
public class ZWaveView extends View
{
    //最高透明度值
    private static final int ALPHA_MAX_NUM = 180;
    //最小透明度值
    private static final int ALPHA_MIN_NUM = 60;
    //默认颜色
    private static final int COLOR_DEFAULT = Color.WHITE;
    //基础动画时长
    private static final int ANIMATOR_DURATION = 2000;
    //最大波数
    private static final int MAX_COUNT = 6;
    private Paint mViewPaint;
    private Path mViewPath;
    //View高
    private int mViewHeight;
    //View宽
    private int mViewWidth;
    //波高
    private float mWaveHeight;
    //波宽
    private float mWaveWidth;
    //水位线
    private float mLevelLine;
    //点集合
    private final SparseArray<List<PointF>> mPointsMap = new SparseArray<>();
    //动画集合
    private final SparseArray<ValueAnimator> mValueAnimatorsMap = new SparseArray<>();
    //最大数
    private int mMaxCount;
    //颜色
    private int mWaveColor;

    public ZWaveView (Context context)
    {
        this(context, null);
    }

    public ZWaveView (Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ZWaveView (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs (Context context, AttributeSet attrs)
    {
        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.ZWaveView);
        mMaxCount = t.getInteger(R.styleable.ZWaveView_maxCount, MAX_COUNT);
        mWaveColor = t.getColor(R.styleable.ZWaveView_waveColor, COLOR_DEFAULT);
        t.recycle();
    }

    private void init ()
    {
        mViewPaint = new Paint();
        mViewPaint.setAntiAlias(true);
        mViewPaint.setColor(mWaveColor);
        mViewPath = new Path();
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHeight = h;
        mViewWidth = w;
        mLevelLine = h * 0.5f;
        initPointMap();
    }

    private void initPointMap ()
    {
        // 根据View宽度计算波形峰值
        mWaveHeight = mViewHeight * 0.75f;
        // 波长等于四倍View宽度也就是View中只能看到四分之一个波形，这样可以使起伏更明显
        mWaveWidth = mViewWidth * 1.5f;
        mPointsMap.clear();

        cancelValueAnimatorsMap();
        mValueAnimatorsMap.clear();

        for (int i = 0; i < mMaxCount; i++)
        {
            initPointList(i);
            initAnimation(i);
        }
    }

    private void initPointList (int index)
    {
        ArrayList<PointF> pointFs = new ArrayList<>();
        // 这里计算在可见的View宽度中能容纳几个波形，注意n上取整
        float offset = index % 6 / 6.0f;
        float offsetX = mWaveWidth * offset;
        int n = (int) Math.round(mViewWidth / mWaveWidth + 0.5) + 1;//增加一个峰值
        // n个波形需要4n+1个点
        for (int i = 0; i < (4 * n + 1); i++)
        {
            float x = i * mWaveWidth / 4 - offsetX;
            float y = 0;
            switch (i % 4)
            {
                case 0:
                case 2:
                    // 零点位于水位线上
                    y = mLevelLine;
                    break;
                case 1:
                    // 往下波动的控制点
                    y = mLevelLine + mWaveHeight;
                    break;
                case 3:
                    // 往上波动的控制点
                    y = mLevelLine - mWaveHeight;
                    break;
            }
            pointFs.add(new PointF(x, y));
        }
        mPointsMap.put(index, pointFs);
    }

    private void initAnimation (final int index)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-1.0f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate (ValueAnimator animation)
            {
                float value = (float) animation.getAnimatedValue();
                calcPointY(index, value);
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(index % 4 * 500 + ANIMATOR_DURATION);
        valueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimatorsMap.put(index, valueAnimator);
    }

    private void startValueAnimatorsMap ()
    {
        for (int i = 0; i < mValueAnimatorsMap.size(); i++)
        {
            mValueAnimatorsMap.get(i).start();
        }
    }

    private void cancelValueAnimatorsMap ()
    {
        for (int i = 0; i < mValueAnimatorsMap.size(); i++)
        {
            mValueAnimatorsMap.get(i).cancel();
        }
    }

    @Override
    protected void onDraw (Canvas canvas)
    {
        for (int i = 0; i < mPointsMap.size(); i++)
        {
            List<PointF> pointFs = mPointsMap.get(i);
            int alpha = ALPHA_MAX_NUM - i * 15;
            if (alpha < ALPHA_MIN_NUM)
            {
                alpha = ALPHA_MIN_NUM;
            }
            mViewPaint.setAlpha(alpha);
            drawWavePath(canvas, pointFs);
        }
    }

    private void drawWavePath (Canvas canvas, List<PointF> pointList)
    {
        mViewPath.reset();
        mViewPath.moveTo(pointList.get(0).x, pointList.get(0).y);
        for (int i = 0; i < pointList.size() - 2; i = i + 2)
        {
            mViewPath.quadTo(pointList.get(i + 1).x, pointList.get(i + 1).y, pointList.get(i + 2).x, pointList.get(i + 2).y);
        }
        mViewPath.lineTo(mViewWidth, mViewHeight);
        mViewPath.lineTo(0, mViewHeight);
        mViewPath.close();
        // mPaint的Style是FILL，会填充整个Path区域
        canvas.drawPath(mViewPath, mViewPaint);
    }

    @Override
    public void onWindowFocusChanged (boolean hasWindowFocus)
    {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus)
        {
            startValueAnimatorsMap();
        }
        else
        {
            cancelValueAnimatorsMap();
        }
    }

    /**
     * 改变Y轴
     *
     * @param index
     * @param offsetY 0~1f
     */
    private void calcPointY (int index, float offsetY)
    {
        synchronized (mPointsMap)
        {
            List<PointF> pointFs = mPointsMap.get(index);
            for (int j = 0; j < pointFs.size(); j++)
            {
                switch (j % 4)
                {
                    case 1:
                        pointFs.get(j).y = mLevelLine + mWaveHeight * offsetY;
                        break;
                    case 3:
                        pointFs.get(j).y = mLevelLine - mWaveHeight * offsetY;
                        break;
                    case 0:
                    case 2:
                        break;
                }
            }
            postInvalidate();
        }
    }

}
