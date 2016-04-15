package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@AutoValue public abstract class Session implements Parcelable {
    abstract String cookie();

    public static Session create(String c) {
        return new AutoValue_Session(c);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Session.typeAdapterFactory();
    }
}
