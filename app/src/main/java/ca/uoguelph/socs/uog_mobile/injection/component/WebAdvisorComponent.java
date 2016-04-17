package ca.uoguelph.socs.uog_mobile.injection.component;

import ca.uoguelph.socs.uog_mobile.injection.module.ActivityModule;
import ca.uoguelph.socs.uog_mobile.injection.module.WebAdvisorModule;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.ui.activity.WebAdvisorActivity;
import ca.uoguelph.socs.uog_mobile.ui.fragment.WebAdvisorLoginFragment;
import ca.uoguelph.socs.uog_mobile.ui.fragment.WebAdvisorScheduleFragment;
import dagger.Component;

/**
 * Created by julianhorvat on 2016-01-22.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
      ActivityModule.class, WebAdvisorModule.class
}) public interface WebAdvisorComponent {

    void inject(WebAdvisorActivity webAdvisorActivity);

    void inject(WebAdvisorLoginFragment loginFragment);

    void inject(WebAdvisorScheduleFragment scheduleFragment);
}
