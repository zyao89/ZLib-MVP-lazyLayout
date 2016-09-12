package com.zyao.zutils;

import android.content.Context;

import com.zyao.zutils.image.ImageLoader;


/**
 * Interface: ImageManager
 * Description: 图片绑定接口
 * Author: Zyao89
 * Time: 2016/7/18 16:48
 */
public interface ImageManager extends ImageLoader
{
    void init (Context context);
}
