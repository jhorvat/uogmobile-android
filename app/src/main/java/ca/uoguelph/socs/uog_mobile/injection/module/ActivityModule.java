package ca.uoguelph.socs.uog_mobile.injection.module;

import android.app.Activity;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by julianhorvat on 2016-01-21.
 */
@Module public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides @PerActivity Activity provideActivity() {
        return this.activity;
    }
}
