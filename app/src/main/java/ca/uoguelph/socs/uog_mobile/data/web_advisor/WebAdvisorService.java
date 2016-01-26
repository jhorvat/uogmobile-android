package ca.uoguelph.socs.uog_mobile.data.web_advisor;

import ca.uoguelph.socs.uog_mobile.data.RxUtils;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.CookieString;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Schedule;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.User;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
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

    @Inject public WebAdvisorService(Retrofit retrofit) {
        this.service = retrofit.create(Api.class);
    }

    public Observable<User> login(String cookies) {
        CookieString cookieString = new CookieString();
        cookieString.cookie = cookies;

        //return this.service.login(cookieString);
        return this.service.login(cookieString).compose(RxUtils.applySchedulers());
    }

    public Observable<Schedule> getSchedule() {
        //return this.service.schedule();
        return this.service.schedule().compose(RxUtils.applySchedulers());
    }

    private interface Api {
        @POST("login") Observable<User> login(@Body CookieString cookieString);

        @GET("schedule") Observable<Schedule> schedule();
    }
}
