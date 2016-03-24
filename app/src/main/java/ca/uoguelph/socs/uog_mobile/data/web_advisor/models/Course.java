package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoValue public abstract class Course implements Parcelable {
    @SerializedName("name") protected abstract String fullName();

    public abstract boolean de();

    @Nullable public abstract Timeslot lab();

    @Nullable public abstract Timeslot exam();

    @SerializedName("lec") @Nullable public abstract Timeslot lecture();

    private transient String name, code;

    public static Course create(String fullName, boolean de, Timeslot lecture, Timeslot lab,
          Timeslot exam) {
        return new AutoValue_Course(fullName, de, lecture, lab, exam);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Course.typeAdapterFactory();
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
}
