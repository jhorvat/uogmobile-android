package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

/**
 * Created by julianhorvat on 2016-01-26.
 */
public class Schedule {
    Course[] courses;
    String term;

    @Override public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Course c : courses) {
            builder.append(c.name).append(", ");
        }
        return builder.toString();
    }
}
