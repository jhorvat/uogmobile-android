package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.support.annotation.Nullable;
import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoParcelGson public abstract class Course {
    @SerializedName("name") protected abstract String fullName();

    public abstract boolean de();

    @Nullable public abstract Timeslot lab();

    @Nullable public abstract Timeslot exam();

    @SerializedName("lec") @Nullable public abstract Timeslot lecture();

    private transient String name, code;

    public static Course create(String fullName, boolean de, Timeslot lecture, Timeslot lab,
          Timeslot exam) {
        return builder().fullName(fullName).de(de).lecture(lecture).lab(lab).exam(exam).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Course.Builder();
    }

    public String name() {
        if (name == null) {
            cacheNameParts();
        }

        return name;
    }

    public String code() {
        if (code == null) {
            cacheNameParts();
        }

        return code;
    }

    private void cacheNameParts() {
        String[] nameParts = fullName().split(" ", 2);
        if (nameParts.length == 2) {
            name = nameParts[1];
            code = nameParts[0];
        }
    }

    @AutoParcelGson.Builder public interface Builder {
        Builder fullName(String n);

        Builder de(boolean d);

        Builder lab(Timeslot t);

        Builder exam(Timeslot t);

        Builder lecture(Timeslot t);

        Course build();
    }
}
