package ca.uoguelph.socs.uog_mobile.injection.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.webkit.CookieManager;
import ca.uoguelph.socs.uog_mobile.UoGMobileApplication;
import ca.uoguelph.socs.uog_mobile.data.net.LoggingInterceptor;
import ca.uoguelph.socs.uog_mobile.data.net.OkCookieManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

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

    @Provides @Singleton CookieManager provideCookieManager() {
        CookieManager manager = CookieManager.getInstance();
        manager.setAcceptCookie(true);
        return manager;
    }

    @Provides OkHttpClient provideHttpClient(OkCookieManager cookieManager,
          LoggingInterceptor loggingInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(cookieManager);
        builder.addInterceptor(loggingInterceptor);

        return builder.build();
    }

    @Provides Converter.Factory provideConverter() {
        return MoshiConverterFactory.create();
    }

    @Provides CallAdapter.Factory provideCallAdapter() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides Retrofit.Builder provideRetrofitBuilder(OkHttpClient client,
          Converter.Factory converter, CallAdapter.Factory callAdapter) {
        return new Retrofit.Builder().addConverterFactory(converter)
                                     .addCallAdapterFactory(callAdapter)
                                     .client(client);
    }
}
