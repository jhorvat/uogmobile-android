package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.User;
import ca.uoguelph.socs.uog_mobile.events.LoggedIn;
import ca.uoguelph.socs.uog_mobile.injection.HasComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.DaggerWebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.injection.module.WebAdvisorModule;
import ca.uoguelph.socs.uog_mobile.ui.fragment.WebAdvisorLoginFragment;
import ca.uoguelph.socs.uog_mobile.ui.fragment.WebAdvisorScheduleFragment;
import com.squareup.otto.Subscribe;
import timber.log.Timber;

public class WebAdvisorActivity extends BaseActivity implements HasComponent {

    private static final String TAG_WA_LOGIN = "WALoginFrag";
    private static final String TAG_WA_SCHEDULE = "WAScheduleFrag";

    @Bind(R.id.toolbar) Toolbar toolbar;

    private WebAdvisorComponent webAdvisorComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.initializeInjection();

        addFragment(R.id.frag_container, TAG_WA_LOGIN, new WebAdvisorLoginFragment());
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public Object getComponent() {
        return this.webAdvisorComponent;
    }

    @Subscribe public void onLoggedIn(LoggedIn e) {
        Timber.d("WebAdvisorActivity; we're logged in");
        replaceFragment(R.id.frag_container, TAG_WA_SCHEDULE, new WebAdvisorScheduleFragment());
    }

    private void initializeInjection() {
        this.webAdvisorComponent = DaggerWebAdvisorComponent.builder()
                                                            .applicationComponent(
                                                                  getApplicationComponent())
                                                            .activityModule(getActivityModule())
                                                            .webAdvisorModule(new WebAdvisorModule())
                                                            .build();
    }
}
