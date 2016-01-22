package ca.uoguelph.socs.uog_mobile.injection.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ca.uoguelph.socs.uog_mobile.UoGMobileApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by julianhorvat on 2016-01-20.
 */
@Singleton @Module public class ApplicationModule {
    private UoGMobileApplication app;

    public ApplicationModule(UoGMobileApplication app) {
        this.app = app;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return this.app;
    }

    @Provides @Singleton SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }
}
