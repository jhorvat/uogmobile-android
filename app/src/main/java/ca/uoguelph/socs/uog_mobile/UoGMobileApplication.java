package ca.uoguelph.socs.uog_mobile;

import android.app.Application;
import ca.uoguelph.socs.uog_mobile.injection.ApplicationComponent;
import ca.uoguelph.socs.uog_mobile.injection.ApplicationModule;
import ca.uoguelph.socs.uog_mobile.injection.DaggerApplicationComponent;

/**
 * Created by julianhorvat on 2016-01-20.
 */
public class UoGMobileApplication extends Application {
    private ApplicationComponent mComponent;

    @Override public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.builder()
              .applicationModule(new ApplicationModule(this))
              .build();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
