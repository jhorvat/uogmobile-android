package ca.uoguelph.socs.uog_mobile.data;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by julianhorvat on 2016-01-26.
 */
public class RxUtils {

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
    }
}
