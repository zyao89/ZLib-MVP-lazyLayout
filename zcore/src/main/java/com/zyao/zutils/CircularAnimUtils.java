/**
 * Title: CircularAnimUtils.java
 * Package: com.zyao.zutils
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/23
 */
package com.zyao.zutils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.zyao.zutils.anim.CircularAnimUtilsImpl;

/**
 * Interface: CircularAnimUtils
 * Description: View转场动画
 * Author: Zyao89
 * Time: 2016/9/23
 */
public interface CircularAnimUtils
{
    CircularAnimUtilsImpl.VisibleBuilder show (View animView);

    void showGo (View animView);

    CircularAnimUtilsImpl.VisibleBuilder show (View animView, View triggerView);

    void showGo (View animView, View triggerView);

    CircularAnimUtilsImpl.VisibleBuilder show (View animView, View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    void showGo (View animView, View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    CircularAnimUtilsImpl.VisibleBuilder hide (View animView);

    void hideGo (View animView);

    CircularAnimUtilsImpl.VisibleBuilder hide (View animView, View triggerView);

    void hideGo (View animView, View triggerView);

    CircularAnimUtilsImpl.VisibleBuilder hide (View animView, View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    void hideGo (View animView, View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    CircularAnimUtilsImpl.FullActivityBuilder fullActivity (Activity activity, View triggerView);

    void fullActivityGoAndFinish (Activity activity, View triggerView, Class<?> targetClass);

    void fullActivityGoAndFinish (Activity activity, View triggerView, Intent intent);

    void fullActivityGoAndFinish (Activity activity, View triggerView, Class<?> targetClass, Intent intent);

    void fullActivityGoAndFinish (Activity activity, View triggerView, Class<?> targetClass, long durationMills);

    void fullActivityGoAndFinish (Activity activity, View triggerView, Class<?> targetClass, Intent intent, long durationMills);

    CircularAnimUtilsImpl.FullActivityBuilder fullActivity (Activity activity, View triggerView, int colorOrImageRes);

    void fullActivityGoAndFinish (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass);

    void fullActivityGoAndFinish (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass, Intent intent);

    void fullActivityGoAndFinish (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass, Intent intent, long durationMills);

    void fullActivityGoAndFinish (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass, long durationMills);

    void fullActivityGo (Activity activity, View triggerView, int colorOrImageRes, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    void fullActivityGo (Activity activity, View triggerView, int colorOrImageRes, long durationMills, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    void fullActivityGo (Activity activity, View triggerView, long durationMills, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    void fullActivityGo (Activity activity, View triggerView, CircularAnimUtilsImpl.OnAnimationEndListener listener);

    void fullActivityGo (Activity activity, View triggerView, Class<?> targetClass);

    void fullActivityGo (Activity activity, View triggerView, Intent intent);

    void fullActivityGo (Activity activity, View triggerView, Class<?> targetClass, Intent intent);

    void fullActivityGo (Activity activity, View triggerView, Class<?> targetClass, long durationMills);

    void fullActivityGo (Activity activity, View triggerView, Class<?> targetClass, Intent intent, long durationMills);

    void fullActivityGo (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass);

    void fullActivityGo (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass, long durationMills);

    void fullActivityGo (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass, Intent intent);

    void fullActivityGo (Activity activity, View triggerView, int colorOrImageRes, Class<?> targetClass, Intent intent, long durationMills);
}
