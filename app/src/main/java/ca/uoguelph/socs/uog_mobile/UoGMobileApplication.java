package ca.uoguelph.socs.uog_mobile;

import android.app.Application;
import ca.uoguelph.socs.uog_mobile.injection.component.ApplicationComponent;
import ca.uoguelph.socs.uog_mobile.injection.module.ApplicationModule;
import ca.uoguelph.socs.uog_mobile.injection.component.DaggerApplicationComponent;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-01-20.
 */
public class UoGMobileApplication extends Application {
    private ApplicationComponent mComponent;

    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mComponent = DaggerApplicationComponent.builder()
              .applicationModule(new ApplicationModule(this))
              .build();

        Timber.d("Application started, AppComponent built");
    }

    public ApplicationComponent getApplicationComponent() {
        return mComponent;
    }
}
