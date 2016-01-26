package ca.uoguelph.socs.uog_mobile.injection.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ca.uoguelph.socs.uog_mobile.UoGMobileApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by julianhorvat on 2016-01-20.
 */
@Singleton @Module public class ApplicationModule {
    private UoGMobileApplication app;

    public ApplicationModule(UoGMobileApplication app) {
        this.app = app;
    }

    @Provides @Singleton Application provideApplicationContext() {
        return this.app;
    }

    @Provides @Singleton SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides MoshiConverterFactory provideMoshi() {
        return MoshiConverterFactory.create();
    }

    @Provides Retrofit.Builder provideRetrofitBuilder(MoshiConverterFactory moshiConverterFactory) {
        return new Retrofit.Builder().addConverterFactory(moshiConverterFactory);
    }
}
