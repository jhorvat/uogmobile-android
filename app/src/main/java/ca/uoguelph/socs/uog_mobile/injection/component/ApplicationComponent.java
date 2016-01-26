package ca.uoguelph.socs.uog_mobile.injection.component;

import android.app.Application;
import ca.uoguelph.socs.uog_mobile.injection.module.ApplicationModule;
import ca.uoguelph.socs.uog_mobile.ui.activity.BaseActivity;
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
}
