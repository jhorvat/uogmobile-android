package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoParcelGson public abstract class Course {
    public static Course create(String name, boolean de, Timeslot lecture, Timeslot lab,
          Timeslot exam) {
        return builder().name(name).de(de).lecture(lecture).lab(lab).exam(exam).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Course.Builder();
    }

    public abstract String name();

    public abstract boolean de();

    public abstract Timeslot lab();

    public abstract Timeslot exam();

    public abstract Timeslot lecture();

    @AutoParcelGson.Builder public interface Builder {
        Builder name(String n);

        Builder de(boolean d);

        Builder lab(Timeslot t);

        Builder exam(Timeslot t);

        Builder lecture(Timeslot t);

        Course build();
    }
}
