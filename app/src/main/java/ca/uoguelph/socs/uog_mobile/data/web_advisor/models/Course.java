package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoParcelGson public abstract class Course {
    abstract String name();

    abstract boolean de();

    abstract Timeslot lab();

    abstract Timeslot exam();

    abstract Timeslot lecture();

    public static Course create(String name, boolean de, Timeslot lecture, Timeslot lab,
          Timeslot exam) {
        return builder().name(name).de(de).lecture(lecture).lab(lab).exam(exam).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Course.Builder();
    }

    @AutoParcelGson.Builder public interface Builder {
        Builder name(String n);

        Builder de(boolean d);

        Builder lab(Timeslot t);

        Builder exam(Timeslot t);

        Builder lecture(Timeslot t);

        Course build();
    }
}
