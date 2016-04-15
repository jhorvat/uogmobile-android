package ca.uoguelph.socs.uog_mobile.data.central_lookup;

import android.content.SharedPreferences;
import ca.uoguelph.socs.uog_mobile.data.central_lookup.models.User;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import com.google.gson.Gson;
import javax.inject.Inject;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by julianhorvat on 2016-04-04.
 */
@PerActivity public class CentralLookupService {

    private final Api service;

    private Gson gson;
    private SharedPreferences prefs;

    @Inject public CentralLookupService(Retrofit retrofit, Gson gson, SharedPreferences prefs) {
        this.service = retrofit.create(Api.class);
        this.gson = gson;
        this.prefs = prefs;
    }

    public Observable<User> lookup(String centralId) {
        return this.service.lookup(centralId)
                           .doOnNext(user -> prefs.edit()
                                                  .putString("user", gson.toJson(user))
                                                  .commit())
                           .compose(RxUtils.applySchedulers());
    }

    private interface Api {
        @GET("lookup/{central_id}") Observable<User> lookup(@Path("central_id") String centralId);
    }
}
