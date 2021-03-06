package ca.uoguelph.socs.uog_mobile.injection.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

/**
 * Created by julianhorvat on 2016-04-04.
 */
@Module public class CentralLookupModule {
    @Provides @Named("BASE_URL") String provideBaseUrl() {
        return "http://131.104.49.59:5000/studentdir/";
    }

    @Provides Retrofit provideRetrofit(Retrofit.Builder builder,
          @Named("BASE_URL") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }
}
