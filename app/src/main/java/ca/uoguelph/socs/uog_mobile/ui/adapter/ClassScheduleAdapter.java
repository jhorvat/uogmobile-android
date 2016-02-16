package ca.uoguelph.socs.uog_mobile.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Course;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Timeslot;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.ui.view.DayBubblesView;
import ca.uoguelph.socs.uog_mobile.ui.view.TimeslotInfoView;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

/**
 * Created by julianhorvat on 2016-02-07.
 */
@PerActivity public class ClassScheduleAdapter
      extends RecyclerView.Adapter<ClassScheduleAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onCourseItemClicked(Course courseModel);
    }

    private final Context context;
    private final LayoutInflater inflater;
    private List<Course> classSchedule;
    private OnItemClickListener clickListener;

    @Inject public ClassScheduleAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.classSchedule = Collections.emptyList();
    }

    public void setClassSchedule(List<Course> classes) {
        classSchedule = classes;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.widget_course_item, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Course course = classSchedule.get(position);
        final Set<Timeslot.DaysOfWeek> activeDays = new HashSet<>();

        if (course.lecture() != null) {
            activeDays.addAll(course.lecture().days());
        }
        if (course.lab() != null) {
            activeDays.addAll(course.lab().days());
        }

        holder.courseName.setText(course.name());
        holder.courseCode.setText(course.code());
        holder.dayBubbles.setActive(activeDays);
        holder.setTimeslots(course.lecture(), course.lab(), course.exam());
    }

    @Override public int getItemCount() {
        return classSchedule.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_course_name) TextView courseName;
        @Bind(R.id.text_course_code) TextView courseCode;

        @Bind(R.id.lecture) TimeslotInfoView lecInfo;
        @Bind(R.id.lab) TimeslotInfoView labInfo;
        @Bind(R.id.exam) TimeslotInfoView examInfo;

        @Bind(R.id.expanded_info_container) ViewGroup expandedContainer;
        @Bind(R.id.bottom_bar) ViewGroup bottomBar;
        @Bind(R.id.day_overview) DayBubblesView dayBubbles;
        @Bind(R.id.expand_card) ImageButton expandCard;

        @BindDrawable(R.drawable.ic_keyboard_arrow_down_black_18dp) Drawable arrowDown;
        @BindDrawable(R.drawable.ic_keyboard_arrow_up_black_18dp) Drawable arrowUp;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            final Interpolator interpolator = AnimationUtils.loadInterpolator(itemView.getContext(),
                                                                              android.R.interpolator.decelerate_cubic);

            expandCard.setOnClickListener(view -> {
                AnimatorSet animation =
                      expandedContainer.getVisibility() == View.GONE ? expand() : collapse();

                animation.setDuration(350).setInterpolator(interpolator);
                animation.start();
            });
        }

        public void setTimeslots(@Nullable Timeslot lecSlot, @Nullable Timeslot labSlot,
              @Nullable Timeslot examSlot) {
            final boolean lec = lecSlot != null;
            final boolean lab = labSlot != null;
            final boolean exam = examSlot != null;

            if (lec || lab || exam) {
                if (lec) {
                    lecInfo.setTimeslot(lecSlot);
                } else {
                    lecInfo.setVisibility(View.GONE);
                }

                if (lab) {
                    labInfo.setTimeslot(labSlot);
                } else {
                    labInfo.setVisibility(View.GONE);
                }

                if (exam) {
                    examInfo.setTimeslot(examSlot);
                } else {
                    examInfo.setVisibility(View.GONE);
                }
            } else {
                bottomBar.setVisibility(View.GONE);
            }
        }

        private AnimatorSet expand() {
            expandedContainer.setVisibility(View.VISIBLE);
            dayBubbles.setAlpha(0);
            dayBubbles.setVisibility(View.INVISIBLE);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec =
                  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

            expandedContainer.measure(widthSpec, heightSpec);

            AnimatorSet set = new AnimatorSet();
            set.play(getHeightAnimator(dayBubbles.getMeasuredHeight(),
                                       expandedContainer.getMeasuredHeight()))
               .with(ObjectAnimator.ofFloat(expandCard, "rotation", 0f, 180f));

            return set;
        }

        private AnimatorSet collapse() {
            final ObjectAnimator bubbleAlpha = ObjectAnimator.ofFloat(dayBubbles, "alpha", 1);
            bubbleAlpha.setStartDelay(250);

            AnimatorSet set = new AnimatorSet();
            set.play(getHeightAnimator(expandedContainer.getHeight(),
                                       dayBubbles.getMeasuredHeight()))
               .with(ObjectAnimator.ofFloat(expandCard, "rotation", 180f, 0f))
               .with(bubbleAlpha);

            return set;
        }

        private ValueAnimator getHeightAnimator(int start, int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);

            animator.addUpdateListener(animation -> {
                ViewGroup.LayoutParams layoutParams = expandedContainer.getLayoutParams();
                layoutParams.height = (Integer) animation.getAnimatedValue();
                expandedContainer.setLayoutParams(layoutParams);
            });

            if (start > end) {
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override public void onAnimationStart(Animator animation) {
                        dayBubbles.setVisibility(View.VISIBLE);
                    }

                    @Override public void onAnimationEnd(Animator animation) {
                        expandedContainer.setVisibility(View.GONE);
                    }
                });
            }

            return animator;
        }
    }
}
