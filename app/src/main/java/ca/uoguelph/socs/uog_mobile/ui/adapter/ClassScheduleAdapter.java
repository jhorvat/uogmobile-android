package ca.uoguelph.socs.uog_mobile.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Course;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-02-07.
 */
@PerActivity public class ClassScheduleAdapter
      extends RecyclerView.Adapter<ClassScheduleAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Course> classSchedule;

    @Inject public ClassScheduleAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        classSchedule = Collections.emptyList();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(inflater.inflate(R.layout.widget_course_item, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Course course = classSchedule.get(position);

        final String[] courseTextParts = course.name().split(" ", 2);
        holder.courseName.setText(courseTextParts[1]);
        holder.courseCode.setText(courseTextParts[0]);
    }

    @Override public int getItemCount() {
        return classSchedule.size();
    }

    public void setClassSchedule(List<Course> classes) {
        classSchedule = classes;
        notifyDataSetChanged();
        Timber.d("Schedule adapter list set\n%s", classSchedule.toString());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_course_name) TextView courseName;
        @Bind(R.id.text_course_code) TextView courseCode;

        ViewHolder(View itemView) {
            super(itemView);
        }

        public static ViewHolder create(View v) {
            final ViewHolder holder = new ViewHolder(v);
            ButterKnife.bind(holder, v);
            return holder;
        }
    }
}
