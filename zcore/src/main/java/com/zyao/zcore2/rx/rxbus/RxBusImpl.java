/**
 * Title: RxBusImpl.java
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
 * Class: RxBusImpl
 * Description: RxBusImpl
 * Author: Zyao89
 * Time: 2016/9/21 10:09
 */
public class RxBusImpl implements RxBus
{
    private static volatile RxBus mInstance;
    private final Subject<Object, Object> mRxBusObservable = new SerializedSubject<>(PublishSubject.create());

    private RxBusImpl ()
    {

    }

    public static RxBus getInstance ()
    {
        if (null == mInstance)
        {
            synchronized (RxBusImpl.class)
            {
                if (null == mInstance)
                {
                    mInstance = new RxBusImpl();
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
    public <T> Observable<T> toObservable (Class<T> eventType)
    {
        return mRxBusObservable.ofType(eventType);
    }

    @Override
    public boolean hasObservers ()
    {
        return mRxBusObservable.hasObservers();
    }
}
