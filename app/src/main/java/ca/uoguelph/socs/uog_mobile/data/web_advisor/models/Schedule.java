package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;
import java.util.ArrayList;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoParcelGson public abstract class Schedule {
    public static Schedule create(ArrayList<Course> courses, String term) {
        return builder().courses(courses).term(term).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Schedule.Builder();
    }

    public abstract ArrayList<Course> courses();

    public abstract String term();

    @AutoParcelGson.Builder public interface Builder {
        Builder courses(ArrayList<Course> c);

        Builder term(String t);

        Schedule build();
    }
}

//public class Schedule {
//    Course[] courses;
//    String term;
//
//    @Override public String toString() {
//        StringBuilder builder = new StringBuilder();
//        for (Course c : courses) {
//            builder.append(c.name).append(", ");
//        }
//        return builder.toString();
//    }
//}
