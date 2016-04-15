package ca.uoguelph.socs.uog_mobile.data.central_lookup.models;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@AutoValue public abstract class User implements Parcelable {
    public abstract String name();

    @SerializedName("central_id") public abstract String id();

    public static User create(String name, String centralId) {
        return new AutoValue_User(name, centralId);
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_User.typeAdapterFactory();
    }
}