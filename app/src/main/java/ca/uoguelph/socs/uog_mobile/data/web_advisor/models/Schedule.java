package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import java.util.ArrayList;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoValue public abstract class Schedule implements Parcelable {
    public abstract ArrayList<Course> courses();

    public abstract String term();

    public static Schedule create(ArrayList<Course> courses, String term) {
        return new AutoValue_Schedule(courses, term);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Schedule.typeAdapterFactory();
    }
}