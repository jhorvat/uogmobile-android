package ca.uoguelph.socs.uog_mobile.data.web_advisor;

import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.CookieString;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@PerActivity public class WebAdvisorService {
    private final Api service;

    @Inject public WebAdvisorService(Retrofit retrofit) {
        this.service = retrofit.create(Api.class);
    }

    public void login(String cookies, Callback<String> callback) {
        CookieString cookieString = new CookieString();
        cookieString.cookie = cookies;

        this.service.login(cookieString).enqueue(callback);
    }

    private interface Api {
        @POST("login") Call<String> login(@Body CookieString cookieString);
    }
}
