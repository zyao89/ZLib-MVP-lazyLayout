package com.zyao.zutils.common;

import android.content.Context;

import com.zyao.zutils.Z;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * Class: LocalFileUtil
 * Description: 本地文件工具类
 * Author: Zyao89
 * Time: 2016/7/28 10:03
 */
public class LocalFileUtil
{
    public final static String TAG = "LocalFileUtils";
    public final static String RECORD_FOLDER_NAME = "record";
    public final static String CAPTURE_FOLDER_NAME = "capture";
    public final static String THUMBNAILS_FOLDER_NAME = ".thumbnails";
    public final static String CHANNELPICTURE_FOLDER_NAME = "channelPic";
    public final static String RECORD_EXT_NAME = ".mp4";
    public final static String PICTURE_EXT_NAME = ".jpg";
    public final static String REGEX = "[/:*?\"<>\\\\|]";

    private static LocalFileUtil mInstance = null;
    private final Context mContext;

    private LocalFileUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static LocalFileUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (LocalFileUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new LocalFileUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取格式化的文件名，不包括扩展名，文件名格式：设备名称_通道号_年月日时分秒毫秒
     *
     * @param deviceName
     * @param channelNo
     *
     * @return 文件名
     */
    public String getFormatFileName (String deviceName, int channelNo)
    {
        String name = "";
        if (deviceName != null)
        {
            name = deviceName.replaceAll(REGEX, "");
        }
        Calendar timeNow = Calendar.getInstance();
        String timeString = String.format(Locale.getDefault(), "%04d%02d%02d%02d%02d%02d%03d", timeNow.get(Calendar.YEAR), timeNow.get(Calendar.MONTH) + 1, timeNow.get(Calendar.DAY_OF_MONTH), timeNow.get(Calendar.HOUR_OF_DAY), timeNow.get(Calendar.MINUTE), timeNow.get(Calendar.SECOND), timeNow.get(Calendar.MILLISECOND));

        return String.format(Locale.getDefault(), "%s_%02d_%s", name, channelNo, timeString);
    }

    /**
     * 获取录像文件全路径，包括扩展名
     *
     * @param fileName 文件名
     * @param mkdirs   是否创建目录
     *
     * @return 录像文件路径
     */
    public String getRecordFileFullPath (String fileName, boolean mkdirs)
    {
        String dirPath = getRecordFolderPathToday();
        if (mkdirs)
        {
            if (!createDirectory(dirPath))
            {
                return null;
            }
        }

        return dirPath + File.separator + fileName + RECORD_EXT_NAME;
    }

    /**
     * 获取抓图文件全路径，包括扩展名
     *
     * @param fileName 文件名
     * @param mkdirs   是否创建目录
     *
     * @return 抓图文件路径
     */
    public String getCaptureFileFullPath (String fileName, boolean mkdirs)
    {
        String dirPath = getCaptureFolderPathToday();
        if (mkdirs)
        {
            if (!createDirectory(dirPath))
            {
                return null;
            }
        }

        return dirPath + File.separator + fileName + PICTURE_EXT_NAME;
    }

    /**
     * 获取缩略图全路径，包括扩展名
     *
     * @param fileName 文件名
     * @param mkdirs   是否创建目录
     *
     * @return 缩略图路径
     */
    public String getThumbnailsFileFullPath (String fileName, boolean mkdirs)
    {
        String dirPath = getThumbnailsFolderPath();
        if (mkdirs)
        {
            createDirectory(dirPath);
        }

        return dirPath + File.separator + fileName + PICTURE_EXT_NAME;
    }

    /**
     * 获取本地文件根目录
     *
     * @return
     */
    public String getLocalFileRootPath ()
    {
        return Z.utils().sdCard().getSdCardPath() + "/" + Z.appInfo().getAppName();
    }

    /**
     * 获取录像根目录
     *
     * @return
     */
    public String getRecordFolderRootPath ()
    {
        return getLocalFileRootPath() + "/" + RECORD_FOLDER_NAME;
    }

    /**
     * 获取抓图根目录
     *
     * @return
     */
    public String getCaptureFolderRootPath ()
    {
        return getLocalFileRootPath() + "/" + CAPTURE_FOLDER_NAME;
    }

    /**
     * 获取当天录像目录
     *
     * @return
     */
    public String getRecordFolderPathToday ()
    {
        return getLocalFileRootPath() + "/" + RECORD_FOLDER_NAME + "/" + String.format(Locale.getDefault(), "%tF", Calendar.getInstance());
    }

    /**
     * 获取当前抓图目录
     *
     * @return
     */
    public String getCaptureFolderPathToday ()
    {
        return getLocalFileRootPath() + "/" + CAPTURE_FOLDER_NAME + "/" + String.format(Locale.getDefault(), "%tF", Calendar.getInstance());
    }

    /**
     * 获取缩略图路径
     *
     * @return
     */
    public String getThumbnailsFolderPath ()
    {
        return getLocalFileRootPath() + "/" + THUMBNAILS_FOLDER_NAME;
    }

    /**
     * 创建目录
     *
     * @param path
     *
     * @return
     */
    public boolean createDirectory (String path)
    {
        File dir = new File(path);
        if (dir.exists())
        {
            return true;
        }

        return dir.mkdirs();
    }

    public String getRecordFolderPathForDate (String date)
    {
        return getLocalFileRootPath() + "/" + RECORD_FOLDER_NAME + "/" + date;
    }

    public String getCaptureFolderPathForDate (String date)
    {
        return getLocalFileRootPath() + "/" + CAPTURE_FOLDER_NAME + "/" + date;
    }

    /**
     * 获取二维码路径
     *
     * @return
     */
    public String getQRCodeFolderPath ()
    {
        return Z.utils().sdCard().getSdCardPath() + "/DCIM/" + Z.appInfo().getAppName();
    }

    /**
     * 获取二维码保存的文件名称
     *
     * @return
     */
    public String getQRCodeFileName ()
    {
        String name = Z.appInfo().getAppName();

        Calendar timeNow = Calendar.getInstance();
        String timeString = String.format(Locale.getDefault(), "%04d%02d%02d%02d%02d%02d%03d", timeNow.get(Calendar.YEAR), timeNow.get(Calendar.MONTH) + 1, timeNow.get(Calendar.DAY_OF_MONTH), timeNow.get(Calendar.HOUR_OF_DAY), timeNow.get(Calendar.MINUTE), timeNow.get(Calendar.SECOND), timeNow.get(Calendar.MILLISECOND));

        return String.format(Locale.getDefault(), "%s_%s_%s", name, "device_QR", timeString) + PICTURE_EXT_NAME;
    }

    /**
     * 获取当前抓图目录
     *
     * @return
     */
    public String getChannelPictureFolderPath ()
    {
        return getLocalFileRootPath() + "/" + CHANNELPICTURE_FOLDER_NAME;
    }

    /**
     * 获取抓图文件全路径，包括扩展名
     *
     * @param fileName 文件名
     * @param mkdirs   是否创建目录
     *
     * @return 截图文件路径
     */
    public String getChannelPicFileFullPath (long deviceID, String fileName, boolean mkdirs)
    {
        String dirPath = getLocalFileRootPath() + "/" + CHANNELPICTURE_FOLDER_NAME + "/" + deviceID;
        if (mkdirs)
        {
            if (!createDirectory(dirPath))
            {
                return null;
            }
        }

        return dirPath + File.separator + fileName;
    }

    public String getChannelPictureDeviceFolderPath (long deviceID)
    {
        return getChannelPictureFolderPath() + "/" + deviceID;
    }

}
