package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import ca.uoguelph.socs.uog_mobile.UoGMobileApplication;
import ca.uoguelph.socs.uog_mobile.injection.module.ActivityModule;

/**
 * Created by julianhorvat on 2016-01-21.
 */
public class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UoGMobileApplication application = (UoGMobileApplication) this.getApplication();
        application.getApplicationComponent().inject(this);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction =
              this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
