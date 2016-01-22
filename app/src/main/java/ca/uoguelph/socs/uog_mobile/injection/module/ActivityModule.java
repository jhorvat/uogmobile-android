package ca.uoguelph.socs.uog_mobile.injection.module;

import android.support.v7.app.AppCompatActivity;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by julianhorvat on 2016-01-21.
 */
@Module public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides @PerActivity AppCompatActivity provideActivity() {
        return this.activity;
    }
}
