package com.zyao.zutils.image.fresco;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zyao.zutils.image.ICustomImageView;
import com.zyao.zutils.image.ImageLoader;

/**
 * Class: FrescoImageLoader
 * Description: Fresco
 * Author: zhangyao6
 * Time: 2016/8/17 15:49
 */
public class FrescoImageLoader implements ImageLoader
{
    public FrescoImageLoader (Context context)
    {
        Fresco.initialize(context);
    }

    /**
     * 类型	SCHEME	示例
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
     * 本地文件	file://	FileInputStream
     * Content provider	content://	ContentResolver
     * asset目录下的资源	asset://	AssetManager
     * res目录下的资源	res://	Resources.openRawResource
     * Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
     */
    @Override
    public void bind (ICustomImageView imageView, String uri)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setImageURI(uri);
        }
    }

    /**
     * Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);
     *
     * @param imageView
     * @param uri
     */
    @Override
    public void bind (ICustomImageView imageView, Uri uri)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setImageURI(uri);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            hierarchy.setPlaceholderImage(placeHolder);
            simpleDraweeView.setImageURI(uri);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            hierarchy.setPlaceholderImage(placeHolder);
            simpleDraweeView.setImageURI(uri);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri, placeHolder);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri, placeHolder);
        }
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, boolean isCrop)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri);
            if (!isCrop)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            }
        }
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, boolean isCrop)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri);
            if (!isCrop)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            }
        }
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, CROP_TYPE cropType)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri);
            if (null != cropType)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                switch (cropType)
                {
                    case CENTER:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
                        break;
                    case CENTER_CROP:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                        break;
                    case CENTER_INSIDE:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
                        break;
                    case FIT_CENTER:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                        break;
                    case FIT_END:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END);
                        break;
                    case FIT_START:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START);
                        break;
                    case FIT_XY:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                        break;
                    case FOCUS_CROP:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
                        break;
                    case NONE:
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, CROP_TYPE cropType)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri);
            if (null != cropType)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                switch (cropType)
                {
                    case CENTER:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
                        break;
                    case CENTER_CROP:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                        break;
                    case CENTER_INSIDE:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
                        break;
                    case FIT_CENTER:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                        break;
                    case FIT_END:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END);
                        break;
                    case FIT_START:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START);
                        break;
                    case FIT_XY:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                        break;
                    case FOCUS_CROP:
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
                        break;
                    case NONE:
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void bind (ICustomImageView imageView, Uri uri, float ratio, boolean isCrop, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri, placeHolder);
            if (!isCrop)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            }
        }
    }

    @Override
    public void bind (ICustomImageView imageView, String uri, float ratio, boolean isCrop, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            simpleDraweeView.setAspectRatio(ratio);
            bind(imageView, uri, placeHolder);
            if (!isCrop)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            }
        }
    }

    @Override
    public void showThumb (ICustomImageView imageView, Uri uri, int width, int height)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).setResizeOptions(new ResizeOptions(width, height)).build();
            DraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request).setOldController(simpleDraweeView.getController()).setControllerListener(new BaseControllerListener<ImageInfo>()).build();
            simpleDraweeView.setController(controller);
        }
    }

    @Override
    public void showThumb (ICustomImageView imageView, Uri uri, int width, int height, boolean isCrop)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            showThumb(imageView, uri, width, height);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            if (!isCrop)
            {
                GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            }
        }
    }

    @Override
    public void showThumb (ICustomImageView imageView, Uri uri, int width, int height, boolean isCrop, @DrawableRes int placeHolder)
    {
        if (imageView instanceof SimpleDraweeView)
        {
            showThumb(imageView, uri, width, height, isCrop);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            hierarchy.setPlaceholderImage(placeHolder);
        }
    }
}
