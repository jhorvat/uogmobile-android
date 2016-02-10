package ca.uoguelph.socs.uog_mobile.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Timeslot;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by julianhorvat on 2016-02-09.
 */
public class DayBubblesView extends GridLayout {
    @Bind({
          R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday
    }) List<TextView> bubbleViews;

    @BindDrawable(R.drawable.background_widget_course_item_day_inactive) Drawable inactiveBg;
    @BindDrawable(R.drawable.background_widget_course_item_day_active) Drawable activeBg;

    private final EnumMap<Timeslot.DaysOfWeek, TextView> dayBubbles;

    public DayBubblesView(Context context) {
        this(context, null);
    }

    public DayBubblesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayBubblesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.layout_day_bubbles, this, true);
        ButterKnife.bind(this, this);

        dayBubbles = new EnumMap<>(Timeslot.DaysOfWeek.class);

        final Iterator<TextView> viewIt = bubbleViews.iterator();
        final Iterator<Timeslot.DaysOfWeek> daysIt =
              Arrays.asList(Timeslot.DaysOfWeek.values()).iterator();

        while (daysIt.hasNext() && viewIt.hasNext()) {
            dayBubbles.put(daysIt.next(), viewIt.next());
        }
    }

    public void setActive(Collection<Timeslot.DaysOfWeek> days) {
        for (Timeslot.DaysOfWeek dayOfWeek : Timeslot.DaysOfWeek.values()) {
            TextView bubble = dayBubbles.get(dayOfWeek);

            if (days.contains(dayOfWeek)) {
                bubble.setBackground(activeBg);
                bubble.setTextColor(Color.WHITE);
            } else {
                bubble.setBackground(inactiveBg);
                bubble.setTextColor(Color.BLACK);
            }
        }

        invalidate();
        requestLayout();
    }
}
