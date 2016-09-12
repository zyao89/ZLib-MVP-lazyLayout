package com.zyao.zutils.image;

import android.net.Uri;
import android.support.annotation.DrawableRes;

/**
 * Interface: ImageLoader
 * Description: 图片加载工具接口
 * Author: zhangyao6
 * Time: 2016/8/17 15:48
 */
public interface ImageLoader
{
    void bind (ICustomImageView imageView, String uri);

    void bind (ICustomImageView imageView, Uri uri);

    void bind (ICustomImageView imageView, String uri, @DrawableRes int placeHolder);

    void bind (ICustomImageView imageView, Uri uri, @DrawableRes int placeHolder);

    void bind (ICustomImageView imageView, String uri, float ratio);

    void bind (ICustomImageView imageView, Uri uri, float ratio);

    void bind (ICustomImageView imageView, String uri, float ratio, @DrawableRes int placeHolder);

    void bind (ICustomImageView imageView, Uri uri, float ratio, @DrawableRes int placeHolder);

    void bind (ICustomImageView imageView, String uri, float ratio, boolean isCrop);

    void bind (ICustomImageView imageView, Uri uri, float ratio, boolean isCrop);

    void bind (ICustomImageView imageView, String uri, float ratio, CROP_TYPE cropType);

    void bind (ICustomImageView imageView, Uri uri, float ratio, CROP_TYPE cropType);

    void bind (ICustomImageView imageView, Uri uri, float ratio, boolean isCrop, @DrawableRes int placeHolder);

    void bind (ICustomImageView imageView, String uri, float ratio, boolean isCrop, @DrawableRes int placeHolder);

    void showThumb (ICustomImageView imageView, Uri uri, int width, int height);

    void showThumb (ICustomImageView imageView, Uri uri, int width, int height, boolean isCrop);

    void showThumb (ICustomImageView imageView, Uri uri, int width, int height, boolean isCrop, @DrawableRes int placeHolder);

    enum CROP_TYPE
    {
        CENTER,
        //居中，无缩放。
        CENTER_CROP,
        //保持宽高比缩小或放大，使得两边都大于或等于显示边界，且宽或高契合显示边界。居中显示。
        FOCUS_CROP,
        //同centerCrop, 但居中点不是中点，而是指定的某个点。
        CENTER_INSIDE,
        //缩放图片使两边都在显示边界内，居中显示。和 fitCenter 不同，不会对图片进行放大。如果图尺寸大于显示边界，则保持长宽比缩小图片。
        FIT_CENTER,
        //保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，且宽或高契合显示边界。居中显示。
        FIT_START,
        //同上。但不居中，和显示边界左上对齐。
        FIT_END,
        //同fitCenter， 但不居中，和显示边界右下对齐。
        FIT_XY,
        //不保存宽高比，填充满显示边界。
        NONE,//如要使用tile mode显示, 需要设置为none
    }
}
