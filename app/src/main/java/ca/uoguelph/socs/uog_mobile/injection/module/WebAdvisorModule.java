package ca.uoguelph.socs.uog_mobile.injection.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

/**
 * Created by julianhorvat on 2016-01-22.
 */
@Module public class WebAdvisorModule {

    @Provides String provideMockString() {
        return "Injected!";
    }

    @Provides @Named("BASE_URL") String provideBaseUrl() {
        return "http://10.11.221.76:5000/webadvisor/";
    }

    @Provides Retrofit provideRetrofit(Retrofit.Builder builder,
          @Named("BASE_URL") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }
}
