package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import ca.uoguelph.socs.uog_mobile.UoGMobileApplication;
import ca.uoguelph.socs.uog_mobile.injection.component.ApplicationComponent;
import ca.uoguelph.socs.uog_mobile.injection.module.ActivityModule;
import ca.uoguelph.socs.uog_mobile.util.RxEventBus;
import javax.inject.Inject;

/**
 * Created by julianhorvat on 2016-01-21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject RxEventBus bus;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((UoGMobileApplication) this.getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
