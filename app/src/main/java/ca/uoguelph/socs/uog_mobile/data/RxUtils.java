package ca.uoguelph.socs.uog_mobile.data;

import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by julianhorvat on 2016-01-26.
 */
public class RxUtils {
    private static final Transformer schedulersTransformer =
          new Transformer<Observable, Observable>() {
              @SuppressWarnings("unchecked") @Override public Observable call(Observable o) {
                  return o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
              }
          };

    @SuppressWarnings("unchecked") public static <T> Transformer<T, T> applySchedulers() {
        return schedulersTransformer;
    }
}
