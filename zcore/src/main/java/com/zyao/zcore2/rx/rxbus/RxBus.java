/**
 * Title: RxBus.java
 * Package: com.zyao.zcore2.rx
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.rx.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Class: RxBus
 * Description: RxBus
 * Author: Zyao89
 * Time: 2016/9/21 10:09
 */
public class RxBus implements IRxBus
{
    private static volatile IRxBus mInstance;
    private final Subject<Object, Object> mRxBusObservable = new SerializedSubject<>(PublishSubject.create());

    private RxBus ()
    {

    }

    public static IRxBus getInstance ()
    {
        if (null == mInstance)
        {
            synchronized (RxBus.class)
            {
                if (null == mInstance)
                {
                    mInstance = new RxBus();
                }
            }
        }

        return mInstance;
    }

    @Override
    public void post (Object o)
    {
        mRxBusObservable.onNext(o);
    }

    @Override
    public Observable<Object> toObservable ()
    {
        return mRxBusObservable;
    }

    @Override
    public boolean hasObservers ()
    {
        return mRxBusObservable.hasObservers();
    }
}
