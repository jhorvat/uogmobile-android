package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.events.LoggedIn;
import ca.uoguelph.socs.uog_mobile.events.RefreshSchedule;
import ca.uoguelph.socs.uog_mobile.injection.HasComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.DaggerWebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.injection.module.WebAdvisorModule;
import ca.uoguelph.socs.uog_mobile.ui.fragment.WebAdvisorLoginFragment;
import ca.uoguelph.socs.uog_mobile.ui.fragment.WebAdvisorScheduleFragment;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class WebAdvisorActivity extends BaseActivity implements HasComponent {

    @Inject SharedPreferences prefs;
    @Inject FragmentManager supportFragmentManager;

    @Bind(R.id.toolbar) Toolbar toolbar;

    private WebAdvisorComponent webAdvisorComponent;
    private CompositeSubscription eventSub;
    private Parcelable user;
    private Fragment currentFragment;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.initializeInjection();

        if (savedInstanceState == null) {
            user = getIntent().getParcelableExtra("user");
            replaceFragment(prefs.contains(WebAdvisorService.SCHEDULE_KEY)
                                  ? WebAdvisorScheduleFragment.newInstance()
                                  : WebAdvisorLoginFragment.newInstance(user));
        } else {
            user = savedInstanceState.getParcelable("user");
        }
    }

    @Override protected void onResume() {
        super.onResume();
        eventSub = new CompositeSubscription();
        eventSub.add(loggedInSub());
        eventSub.add(refreshScheduleSub());
    }

    @Override protected void onPause() {
        super.onPause();
        RxUtils.resetSub(eventSub);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("user", user);
    }

    @Override public Object getComponent() {
        return this.webAdvisorComponent;
    }

    private Subscription refreshScheduleSub() {
        return bus.observeEvent(RefreshSchedule.class)
                  .subscribe(refresh -> replaceFragment(WebAdvisorLoginFragment.newInstance(user)));
    }

    private Subscription loggedInSub() {
        return bus.observeEvent(LoggedIn.class)
                  .subscribe(loggedIn -> replaceFragment(WebAdvisorScheduleFragment.newInstance()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();

        if (currentFragment != null) {
            transaction = transaction.remove(currentFragment);
        }

        transaction.add(R.id.frag_container, fragment).addToBackStack(null).commit();
        currentFragment = fragment;
    }

    private void initializeInjection() {
        webAdvisorComponent = DaggerWebAdvisorComponent.builder()
                                                       .applicationComponent(getApplicationComponent())
                                                       .activityModule(getActivityModule())
                                                       .webAdvisorModule(new WebAdvisorModule())
                                                       .build();
        webAdvisorComponent.inject(this);
    }
}
