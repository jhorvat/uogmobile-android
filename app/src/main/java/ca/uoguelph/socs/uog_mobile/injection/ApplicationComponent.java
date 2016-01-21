package ca.uoguelph.socs.uog_mobile.injection;

import ca.uoguelph.socs.uog_mobile.UoGMobileApplication;
import ca.uoguelph.socs.uog_mobile.ui.activity.MainActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by julianhorvat on 2016-01-20.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(UoGMobileApplication app);
    void inject(MainActivity activity);
}
