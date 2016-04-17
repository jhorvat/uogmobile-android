package ca.uoguelph.socs.uog_mobile.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.ui.adapter.ClassScheduleAdapter;
import ca.uoguelph.socs.uog_mobile.util.RxEventBus;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import javax.inject.Inject;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-02-02.
 */
public class WebAdvisorScheduleFragment extends BaseFragment {
    public static final String STATE_SCHEDULE = "Schedule";

    @Inject ClassScheduleAdapter scheduleAdapter;
    @Inject RecyclerView.LayoutManager layoutManager;
    @Inject WebAdvisorService webAdvisorService;
    @Inject RxEventBus bus;

    @Bind(R.id.class_list) RecyclerView recyclerView;
    @Bind(R.id.loading_indicator) ProgressBar loadingIndicator;

    private Subscription subscription;

    public static WebAdvisorScheduleFragment newInstance() {
        return new WebAdvisorScheduleFragment();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_advisor_schedule, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(WebAdvisorComponent.class).inject(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override public void onResume() {
        super.onResume();
        subscription = webAdvisorService.getSchedule()
                                        .doOnSubscribe(() -> loadingIndicator.setVisibility(View.VISIBLE))
                                        .subscribe(schedule -> {
                                            loadingIndicator.setVisibility(View.GONE);
                                            scheduleAdapter.setClassSchedule(schedule.courses());
                                        }, throwable -> {
                                            Timber.e(throwable, "Something failed");
                                            loadingIndicator.setVisibility(View.GONE);
                                        });
    }

    @Override public void onPause() {
        super.onPause();
        RxUtils.resetSub(subscription);
    }
}
