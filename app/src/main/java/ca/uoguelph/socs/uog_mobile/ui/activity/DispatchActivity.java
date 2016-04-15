package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import ca.uoguelph.socs.uog_mobile.data.central_lookup.models.User;
import ca.uoguelph.socs.uog_mobile.injection.component.ActivityComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.DaggerActivityComponent;
import com.google.gson.Gson;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-03-30.
 */
public class DispatchActivity extends BaseActivity {

    @Inject Gson gson;
    @Inject SharedPreferences prefs;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjection();

        final String user = prefs.getString("user", "");
        final Class toLaunch =
              TextUtils.isEmpty(user) ? CentralLookupActivity.class : WebAdvisorActivity.class;
        final Intent in = new Intent(this, toLaunch);

        if (toLaunch == WebAdvisorActivity.class) {
            Timber.d("User has been verified");
            in.putExtra("user", gson.fromJson(user, User.class));
        } else {
            Timber.d("New user, verifying identity");
        }

        startActivity(in);
    }

    private void initializeInjection() {
        ActivityComponent component = DaggerActivityComponent.builder()
                                                             .applicationComponent(
                                                                   getApplicationComponent())
                                                             .activityModule(getActivityModule())
                                                             .build();
        component.inject(this);
    }
}
