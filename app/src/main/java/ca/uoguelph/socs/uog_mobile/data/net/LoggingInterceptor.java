package ca.uoguelph.socs.uog_mobile.data.net;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@Singleton public class LoggingInterceptor implements Interceptor {
    @Inject public LoggingInterceptor() {
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Timber.v("Sending request " + request.url() + " on " + chain.connection() + "\n"
                       + request.headers());

        Response response = chain.proceed(request);
        if (response != null) {
            long t2 = System.nanoTime();
            Timber.v("Received response for " + response.request().url() + " in " + (t2 - t1) / 1e6d
                           + "ms\n" + "HTTP " + response.code() + "(" + response.message() + ")\n"
                           + "HEADERS:\n" + response.headers());
        }
        return response;
    }
}
