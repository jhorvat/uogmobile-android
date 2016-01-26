package ca.uoguelph.socs.uog_mobile.injection.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by julianhorvat on 2016-01-22.
 */
@Module public class WebAdvisorModule {

    @Provides String provideMockString() {
        return "Injected!";
    }

    @Provides Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl("http://192.168.0.105:5000/webadvisor/").build();
    }
}
