/**
 * Title: IRxCompositeSubscription.java
 * Package: com.zyao.zcore2
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/20
 */
package com.zyao.zcore2;

import rx.Subscription;

/**
 * Interface: IRxCompositeSubscription
 * Description: Rx订阅管理接口
 * Author: Zyao89
 * Time: 2016/9/20 16:55
 */
public interface IRxCompositeSubscription
{
    boolean isUnsubscribed ();

    /**
     * 添加订阅
     */
    void addSubscription (final Subscription s);

    /**
     * 添加全部
     *
     * @param subscriptions
     */
    void addAllSubscription (final Subscription... subscriptions);

    /**
     * 移除
     *
     * @param s
     */
    void removeSubscription (final Subscription s);

    /**
     * 清空
     */
    void clearSubscription ();

    boolean hasSubscriptions ();
}
