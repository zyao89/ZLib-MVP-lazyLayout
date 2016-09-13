package com.zyao.zutils.image;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.zyao.zutils.ImageManager;
import com.zyao.zutils.Z;
import com.zyao.zutils.image.fresco.FrescoImageLoader;

/**
 * Class: ImageManagerImpl
 * Description: 图片加载管理工具类
 * Author: Zyao89
 * Time: 2016/7/18 16:49
 */
public class ImageManagerImpl implements ImageManager
{
    private static volatile ImageManager instance;
    private ImageLoader mLoader;

    private ImageManagerImpl ()
    {
    }

    public static void registerInstance ()
    {
        if (instance == null)
        {
            synchronized (ImageManager.class)
            {
                if (instance == null)
                {
                    instance = new ImageManagerImpl();
                }
            }
        }
        Z.Ext.setImageManager(instance);
    }

    @Override
    public void init (Context context)
    {
        mLoader = new FrescoImageLoader(context);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, placeHolder);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, placeHolder);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, placeHolder);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, placeHolder);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, boolean isCrop)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, isCrop);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, boolean isCrop)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, isCrop);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, CROP_TYPE cropType)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, cropType);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, CROP_TYPE cropType)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, cropType);
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, boolean isCrop, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, isCrop, placeHolder);
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, boolean isCrop, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.bind(imageView, uri, ratio, isCrop, placeHolder);
    }

    @Override
    public void showThumb (ICustomImageView imageView, Uri uri, int width, int height)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.showThumb(imageView, uri, width, height);
    }

    @Override
    public void showThumb (ICustomImageView imageView, Uri uri, int width, int height, boolean isCrop)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.showThumb(imageView, uri, width, height, isCrop);
    }

    @Override
    public void showThumb (ICustomImageView imageView, Uri uri, int width, int height, boolean isCrop, @DrawableRes int placeHolder)
    {
        if (mLoader == null)
        {
            throw new NullPointerException("mLoader is Null...");
        }
        mLoader.showThumb(imageView, uri, width, height, isCrop, placeHolder);
    }
}
