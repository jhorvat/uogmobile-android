package ca.uoguelph.socs.uog_mobile.injection.component;

import ca.uoguelph.socs.uog_mobile.injection.module.ActivityModule;
import ca.uoguelph.socs.uog_mobile.injection.module.CentralLookupModule;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.ui.activity.CentralLookupActivity;
import dagger.Component;

/**
 * Created by julianhorvat on 2016-04-04.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
      ActivityModule.class, CentralLookupModule.class
}) public interface CentralLookupComponent {
    void inject(CentralLookupActivity activity);
}
