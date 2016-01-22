package ca.uoguelph.socs.uog_mobile.injection.component;

import android.support.v7.app.AppCompatActivity;
import ca.uoguelph.socs.uog_mobile.injection.module.ActivityModule;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import dagger.Component;

/**
 * Created by julianhorvat on 2016-01-21.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    AppCompatActivity activity();
}

