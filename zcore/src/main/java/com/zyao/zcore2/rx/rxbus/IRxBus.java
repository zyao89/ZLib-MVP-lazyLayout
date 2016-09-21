/**
 * Title: IRxBus.java
 * Package: com.zyao.zcore2.rx
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.rx.rxbus;

import rx.Observable;

/**
 * interface: IRxBus
 * Description: RxBus接口
 * Author: Zyao89
 * Time: 2016/9/21 10:05
 */
public interface IRxBus
{
    void post (Object o);

    Observable<Object> toObservable ();

    boolean hasObservers ();
}
