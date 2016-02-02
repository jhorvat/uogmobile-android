package ca.uoguelph.socs.uog_mobile.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Course;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-02-02.
 */
public class WebAdvisorScheduleFragment extends BaseFragment {
    @Inject WebAdvisorService webAdvisorService;

    @Bind(R.id.classes) TextView classes;

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

        webAdvisorService.getSchedule().subscribe(schedule -> {
            StringBuilder classNames = new StringBuilder();
            for (Course course : schedule.courses()) {
                classNames.append(course.name()).append("\n");
            }
            classes.setText(classNames.toString());
        }, throwable -> Timber.e(throwable, "Something failed"));
    }
}
