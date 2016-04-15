package ca.uoguelph.socs.uog_mobile.injection.module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

/**
 * Created by julianhorvat on 2016-01-22.
 */
@Module public class WebAdvisorModule {

    @Provides @Named("BASE_URL") String provideBaseUrl() {
        return "http://131.104.49.59:5000/webadvisor/";
    }

    @Provides RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides Retrofit provideRetrofit(Retrofit.Builder builder,
          @Named("BASE_URL") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }
}
