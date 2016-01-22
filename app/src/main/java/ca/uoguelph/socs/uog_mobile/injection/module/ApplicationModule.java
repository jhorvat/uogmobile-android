package ca.uoguelph.socs.uog_mobile.injection.module;

import android.app.Application;
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
    private UoGMobileApplication mApp;

    public ApplicationModule(UoGMobileApplication app) {
        mApp = app;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return this.mApp;
    }

    @Provides @Singleton SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }
}
