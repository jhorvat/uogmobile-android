package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoValue public abstract class Timeslot implements Parcelable {

    // WebAdvisor currently has no sunday dates so one here is unnecessary
    public enum DaysOfWeek {
        @SerializedName("Mon")MON,
        @SerializedName("Tues")TUES,
        @SerializedName("Wed")WED,
        @SerializedName("Thur")THURS,
        @SerializedName("Fri")FRI,
        @SerializedName("Sat")SAT,
    }

    public abstract String location();

    public abstract String time();

    @NotNull public abstract ArrayList<DaysOfWeek> days();

    public static Timeslot create(String location, String time, ArrayList<DaysOfWeek> days) {
        return new AutoValue_Timeslot(location, time, days);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Timeslot.typeAdapterFactory();
    }
}
