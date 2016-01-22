package ca.uoguelph.socs.uog_mobile.injection.module;

import dagger.Module;
import dagger.Provides;

/**
 * Created by julianhorvat on 2016-01-22.
 */
@Module
public class WebAdvisorModule {

    @Provides String provideMockString() {
        return "Injected!";
    }
}
