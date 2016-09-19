/**
 * Title: ApplicationModule.java
 * Package: com.zyao.zcore2.di.module
 * Description: 本内容来源于Zyao89。
 * Author: Zyao89
 * Date: 2016/9/16
 */
package com.zyao.zcore2.di.module;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.service.wallpaper.WallpaperService;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;

import com.zyao.zcore2.di.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Class: ApplicationModule
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/16 17:02
 */
@Module
public class ApplicationModule
{
    private final Application mApplication;

    public ApplicationModule (Application application)
    {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife("application")
    Context provideApplicationContext ()
    {
        return mApplication;
    }

    @Provides
    @Singleton
    @ContextLife("application")
    Application provideApplication ()
    {
        return mApplication;
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater()
    {
        return (LayoutInflater) mApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    @Singleton
    WindowManager provideWindowManager()
    {
        return (WindowManager) mApplication.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    @Singleton
    PowerManager providePowerManager()
    {
        return (PowerManager) mApplication.getSystemService(Context.POWER_SERVICE);
    }

    @Provides
    @Singleton
    AccountManager provideAccountManager()
    {
        return (AccountManager) mApplication.getSystemService(Context.ACCOUNT_SERVICE);
    }

    @Provides
    @Singleton
    ActivityManager provideActivityManager()
    {
        return (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    AlarmManager provideAlarmManager()
    {
        return (AlarmManager) mApplication.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    NotificationManager provideNotificationManager()
    {
        return (NotificationManager) mApplication.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    AccessibilityManager provideAccessibilityManager()
    {
        return (AccessibilityManager) mApplication.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Provides
    @Singleton
    CaptioningManager provideCaptioningManager()
    {
        return (CaptioningManager) mApplication.getSystemService(Context.CAPTIONING_SERVICE);
    }

    @Provides
    @Singleton
    KeyguardManager provideKeyguardManager()
    {
        return (KeyguardManager) mApplication.getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager()
    {
        return (LocationManager) mApplication.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    SearchManager provideSearchManager()
    {
        return (SearchManager) mApplication.getSystemService(Context.SEARCH_SERVICE);
    }

    @Provides
    @Singleton
    SensorManager provideSensorManager()
    {
        return (SensorManager) mApplication.getSystemService(Context.SENSOR_SERVICE);
    }

    @Provides
    @Singleton
    StorageManager provideStorageManager()
    {
        return (StorageManager) mApplication.getSystemService(Context.STORAGE_SERVICE);
    }

    @Provides
    @Singleton
    WallpaperService provideWallpaperService()
    {
        return (WallpaperService) mApplication.getSystemService(Context.WALLPAPER_SERVICE);
    }

    @Provides
    @Singleton
    Vibrator provideVibrator()
    {
        return (Vibrator) mApplication.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Provides
    @Singleton
    NetworkStatsManager provideNetworkStatsManager()
    {
        return (NetworkStatsManager) mApplication.getSystemService(Context.NETWORK_STATS_SERVICE);
    }

    @Provides
    @Singleton
    WifiManager provideWifiManager()
    {
        return (WifiManager) mApplication.getSystemService(Context.WIFI_SERVICE);
    }

    @Provides
    @Singleton
    WifiP2pManager provideWifiP2pManager()
    {
        return (WifiP2pManager) mApplication.getSystemService(Context.WIFI_P2P_SERVICE);
    }
}
