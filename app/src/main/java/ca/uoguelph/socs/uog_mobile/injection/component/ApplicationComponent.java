package ca.uoguelph.socs.uog_mobile.injection.component;

import android.content.Context;
import ca.uoguelph.socs.uog_mobile.injection.module.ApplicationModule;
import ca.uoguelph.socs.uog_mobile.ui.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by julianhorvat on 2016-01-20.
 */
@Singleton @Component(modules = { ApplicationModule.class }) public interface ApplicationComponent {
    void inject(BaseActivity activity);

    Context context();
}
