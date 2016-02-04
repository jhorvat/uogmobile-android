package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private FragmentManager supportFragmentManager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);

        supportFragmentManager = this.getSupportFragmentManager();
    }

    protected void addFragment(int containerViewId, String tag, Fragment fragment) {
        supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit();
    }

    protected void replaceFragment(int containerViewId, String tag, Fragment fragment) {
        supportFragmentManager.beginTransaction().replace(containerViewId, fragment, tag).commit();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((UoGMobileApplication) this.getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
