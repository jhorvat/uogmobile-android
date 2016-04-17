package ca.uoguelph.socs.uog_mobile.data.web_advisor;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Schedule;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Session;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@PerActivity public class WebAdvisorService {
    public static final String SCHEDULE_KEY = "schedule";

    private final Api service;
    private final SharedPreferences prefs;
    private final Gson gson;

    private Schedule schedule;

    @Inject public WebAdvisorService(Retrofit retrofit, SharedPreferences prefs, Gson gson) {
        this.gson = gson;
        this.prefs = prefs;
        this.service = retrofit.create(Api.class);
    }

    public Observable<Session> login(String cookies) {
        return service.login(Session.create(cookies)).compose(RxUtils.applySchedulers());
    }

    public Observable<Schedule> getSchedule() {
        return getScheduleFromCache().concatWith(service.getScheduleFromApi()
                                                        .doOnNext(cacheScheduleInMemory())
                                                        .doOnNext(cacheScheduleOnDisk())
                                                        .subscribeOn(Schedulers.newThread()))
                                     .compose(RxUtils.applySchedulers());
    }

    @NonNull private Action1<Schedule> cacheScheduleOnDisk() {
        return s -> {
            synchronized (WebAdvisorService.this) {
                prefs.edit().putString(SCHEDULE_KEY, gson.toJson(schedule)).apply();
            }
        };
    }

    @NonNull private Action1<Schedule> cacheScheduleInMemory() {
        return s -> {
            synchronized (WebAdvisorService.this) {
                schedule = s;
            }
        };
    }

    @NonNull private Observable<Schedule> getScheduleFromCache() {
        return getScheduleFromMemory().concatWith(getScheduleFromDisk().doOnNext(
              cacheScheduleInMemory())).take(1);
    }

    @NonNull private Observable<Schedule> getScheduleFromMemory() {
        return Observable.create(subscriber -> {
            synchronized (WebAdvisorService.this) {
                if (schedule != null) {
                    subscriber.onNext(schedule);
                }

                subscriber.onCompleted();
            }
        });
    }

    @NonNull private Observable<Schedule> getScheduleFromDisk() {
        return Observable.create(subscriber -> {
            synchronized (WebAdvisorService.this) {
                if (prefs.contains(SCHEDULE_KEY)) {
                    Schedule schedule = gson.fromJson(prefs.getString(SCHEDULE_KEY, null),
                                                      new TypeToken<Schedule>() {
                                                      }.getType());
                    //gson.fromJson(prefs.getString(SCHEDULE_KEY, null), Schedule.class);

                    subscriber.onNext(schedule);
                }

                subscriber.onCompleted();
            }
        });
    }

    private interface Api {
        @POST("login") Observable<Session> login(@Body Session c);

        @GET("schedule") Observable<Schedule> getScheduleFromApi();
    }
}
