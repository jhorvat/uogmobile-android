package ca.uoguelph.socs.uog_mobile.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Course;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Timeslot;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-02-07.
 */
@PerActivity public class ClassScheduleAdapter
      extends RecyclerView.Adapter<ClassScheduleAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<Course> classSchedule;

    @Inject public ClassScheduleAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.classSchedule = Collections.emptyList();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.widget_course_item, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Course course = classSchedule.get(position);

        holder.courseName.setText(course.name());
        holder.courseCode.setText(course.code());

        if (course.lecture() != null) {
            holder.setActive(course.lecture().days());
        }

        if (course.lab() != null) {
            holder.setActive(course.lab().days());
        }
    }

    @Override public int getItemCount() {
        return classSchedule.size();
    }

    public void setClassSchedule(List<Course> classes) {
        classSchedule = classes;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_course_name) TextView courseName;
        @Bind(R.id.text_course_code) TextView courseCode;
        @Bind({
              R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday
        }) List<TextView> daysBubbles;

        @BindDrawable(R.drawable.background_widget_course_item_day_active) Drawable activeBg;

        private final EnumMap<Timeslot.DaysOfWeek, TextView> daysMap;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            daysMap = new EnumMap<>(Timeslot.DaysOfWeek.class);

            final Iterator<TextView> viewIt = daysBubbles.iterator();
            final Iterator<Timeslot.DaysOfWeek> daysIt =
                  Arrays.asList(Timeslot.DaysOfWeek.values()).iterator();

            while (daysIt.hasNext() && viewIt.hasNext()) {
                daysMap.put(daysIt.next(), viewIt.next());
            }
        }

        public void setActive(List<Timeslot.DaysOfWeek> days) {
            for (Timeslot.DaysOfWeek day : days) {
                final TextView bubble = daysMap.get(day);
                bubble.setBackground(activeBg);
                bubble.setTextColor(Color.WHITE);
            }
        }
    }
}
