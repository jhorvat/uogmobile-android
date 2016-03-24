package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@AutoValue public abstract class Session implements Parcelable {
    abstract String cookie();

    @SerializedName("test_session") abstract boolean testSession();

    public static Session create(String c, boolean test) {
        return new AutoValue_Session(c, test);
    }

    public static Session create(String c) {
        return create(c, false);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Session.typeAdapterFactory();
    }
}
