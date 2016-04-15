package ca.uoguelph.socs.uog_mobile.data.web_advisor.models.adapters;

import android.os.Parcel;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Course;
import com.ryanharter.auto.value.parcel.TypeAdapter;
import java.util.ArrayList;

/**
 * Created by julianhorvat on 2016-04-15.
 */
public class CourseArrayListTypeAdapter implements TypeAdapter<ArrayList> {

    @Override public ArrayList fromParcel(Parcel in) {
        return in.readArrayList(Course.class.getClassLoader());
    }

    @Override public void toParcel(ArrayList value, Parcel dest) {
        dest.writeTypedList(value);
    }
}