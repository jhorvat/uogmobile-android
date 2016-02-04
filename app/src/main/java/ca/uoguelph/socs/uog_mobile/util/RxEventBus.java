package ca.uoguelph.socs.uog_mobile.util;

import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by julianhorvat on 2016-02-03.
 */
@Singleton public class RxEventBus {
    private final Subject<Object, Object> subject;

    @Inject public RxEventBus() {
        this.subject = new SerializedSubject<>(PublishSubject.create());
    }

    public <E> void post(E event) {
        subject.onNext(event);
    }

    public <E> Observable<E> subscribe(Class<E> eventClass) {
        return subject.ofType(eventClass).compose(RxUtils.applySchedulers());
    }
}
