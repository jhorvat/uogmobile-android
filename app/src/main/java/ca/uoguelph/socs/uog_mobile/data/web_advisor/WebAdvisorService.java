package ca.uoguelph.socs.uog_mobile.data.web_advisor;

import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Schedule;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Session;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import javax.inject.Inject;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@PerActivity public class WebAdvisorService {
    private final Api service;
    private final String baseUrl;

    @Inject public WebAdvisorService(Retrofit retrofit) {
        this.service = retrofit.create(Api.class);
        this.baseUrl = retrofit.baseUrl().url().toString();
    }

    public Observable<Session> login(String cookies) {
        return this.service.login(Session.create(cookies)).compose(RxUtils.applySchedulers());
    }

    public Observable<Schedule> getSchedule() {
        return this.service.schedule().compose(RxUtils.applySchedulers());
    }

    private interface Api {
        @POST("login") Observable<Session> login(@Body Session c);

        @GET("schedule") Observable<Schedule> schedule();
    }
}
