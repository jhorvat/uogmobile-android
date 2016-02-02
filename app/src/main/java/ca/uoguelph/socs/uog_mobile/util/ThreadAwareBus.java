package ca.uoguelph.socs.uog_mobile.util;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by julianhorvat on 2016-02-02.
 */
public class ThreadAwareBus extends Bus {
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    public ThreadAwareBus() {
        super(ThreadEnforcer.MAIN, "Main Thread");
    }

    public void post(final Object event) {
        // Makes sure that all events are posted to the main thread. This simplifies
        // the work of sending events from a background service
        final boolean isMainThread = Looper.myLooper() == Looper.getMainLooper();

        if (isMainThread) {
            super.post(event);
        } else {
            mainThread.post(() -> ThreadAwareBus.super.post(event));
        }
    }
}
