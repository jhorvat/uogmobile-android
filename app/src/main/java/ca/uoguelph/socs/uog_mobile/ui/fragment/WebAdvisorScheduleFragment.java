package ca.uoguelph.socs.uog_mobile.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.ui.adapter.ClassScheduleAdapter;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-02-02.
 */
public class WebAdvisorScheduleFragment extends BaseFragment {
    @Inject ClassScheduleAdapter classScheduleAdapter;
    @Inject RecyclerView.LayoutManager layoutManager;
    @Inject WebAdvisorService webAdvisorService;

    @Bind(R.id.class_list) RecyclerView recyclerView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(WebAdvisorComponent.class).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_advisor_schedule, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(classScheduleAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override public void onResume() {
        super.onResume();
        webAdvisorService.getSchedule()
                         .subscribe(schedule -> classScheduleAdapter.setClassSchedule(schedule.courses()),
                                    throwable -> Timber.e(throwable, "Something failed"));
    }
}
