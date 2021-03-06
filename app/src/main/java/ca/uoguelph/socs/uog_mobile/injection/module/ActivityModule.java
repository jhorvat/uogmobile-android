package ca.uoguelph.socs.uog_mobile.injection.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
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
    @Provides @PerActivity Context provideActivity() {
        return this.activity;
    }

    @Provides @PerActivity FragmentManager provideFragmentManager() {
        return this.activity.getSupportFragmentManager();
    }
}
