package ca.uoguelph.socs.uog_mobile.injection.component;

import android.app.Application;
import android.content.SharedPreferences;
import android.webkit.CookieManager;
import ca.uoguelph.socs.uog_mobile.injection.module.ApplicationModule;
import ca.uoguelph.socs.uog_mobile.ui.activity.BaseActivity;
import com.squareup.otto.Bus;
import dagger.Component;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by julianhorvat on 2016-01-20.
 */
@Singleton @Component(modules = { ApplicationModule.class }) public interface ApplicationComponent {
    void inject(BaseActivity activity);

    Application app();

    Retrofit.Builder retrofitBuilder();

    CookieManager manager();

    SharedPreferences prefs();

    Bus bus();
}
