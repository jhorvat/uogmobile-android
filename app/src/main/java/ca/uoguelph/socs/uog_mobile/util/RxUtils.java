package ca.uoguelph.socs.uog_mobile.util;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by julianhorvat on 2016-01-26.
 */
public class RxUtils {
    private static final Transformer schedulersTransformer =
          new Transformer<Observable, Observable>() {
              @SuppressWarnings("unchecked") @Override public Observable call(Observable o) {
                  return o.subscribeOn(Schedulers.newThread())
                          .observeOn(AndroidSchedulers.mainThread());
              }
          };

    @SuppressWarnings("unchecked") public static <T> Transformer<T, T> applySchedulers() {
        return schedulersTransformer;
    }

    public static boolean isSubbed(Subscription sub) {
        return (sub != null && !sub.isUnsubscribed());
    }

    public static Subscription resetSub(Subscription sub) {
        if (isSubbed(sub)) {
            sub.unsubscribe();
        }

        return Subscriptions.unsubscribed();
    }
}
