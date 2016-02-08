package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;
import java.util.ArrayList;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoParcelGson public abstract class Timeslot {
    public static Timeslot create(String location, String time, ArrayList<String> days) {
        return builder().location(location).time(time).days(days).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Timeslot.Builder();
    }

    abstract String location();

    abstract String time();

    abstract ArrayList<String> days();

    @AutoParcelGson.Builder public interface Builder {
        Builder location(String l);

        Builder time(String t);

        Builder days(ArrayList<String> d);

        Timeslot build();
    }
}
//public class Timeslot {
//    String location, time;
//    String[] days;
//}
