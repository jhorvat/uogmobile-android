package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.os.Parcel;
import android.os.Parcelable;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.adapters.CourseArrayListTypeAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.parcel.ParcelAdapter;
import com.ryanharter.auto.value.parcel.TypeAdapter;
import java.util.ArrayList;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoValue public abstract class Schedule implements Parcelable {
    @ParcelAdapter(CourseArrayListTypeAdapter.class) public abstract ArrayList<Course> courses();

    public abstract String term();

    public static Schedule create(ArrayList<Course> courses, String term) {
        return new AutoValue_Schedule(courses, term);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Schedule.typeAdapterFactory();
    }
}