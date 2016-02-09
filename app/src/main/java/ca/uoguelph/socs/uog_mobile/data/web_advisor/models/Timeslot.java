package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoParcelGson public abstract class Timeslot {

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
        return builder().location(location).time(time).days(days).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Timeslot.Builder();
    }

    @AutoParcelGson.Builder public interface Builder {
        Builder location(String l);

        Builder time(String t);

        Builder days(ArrayList<DaysOfWeek> d);

        Timeslot build();
    }
}
//public class Timeslot {
//    String location, time;
//    String[] days;
//}
