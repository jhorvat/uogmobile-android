package ca.uoguelph.socs.uog_mobile.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Timeslot;

/**
 * Created by julianhorvat on 2016-02-10.
 */
public class TimeslotInfoView extends LinearLayout {

    @Bind(R.id.type) TextView type;
    @Bind(R.id.location) TextView location;
    @Bind(R.id.time) TextView time;
    @Bind(R.id.days) DayBubblesView days;

    public TimeslotInfoView(Context context) {
        this(context, null);
    }

    public TimeslotInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeslotInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_timeslot_info, this, true);
        ButterKnife.bind(this, this);

        TypedArray a =
              context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimeslotInfoView, 0, 0);

        try {
            type.setText(a.getString(R.styleable.TimeslotInfoView_type));
        } catch (Exception e) {
            throw new InflateException("Error inflating view", e);
        } finally {
            a.recycle();
        }
    }

    public void setTimeslot(Timeslot timeslot) {
        location.setText(timeslot.location());
        time.setText(timeslot.time());
        days.setActive(timeslot.days());

        setVisibility(View.VISIBLE);
        invalidate();
        requestLayout();
    }
}
