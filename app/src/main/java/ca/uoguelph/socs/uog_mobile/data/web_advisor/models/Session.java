package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@AutoParcelGson public abstract class Session {
    abstract String cookie();

    @SerializedName("test_session") abstract boolean testSession();

    public static Session create(String c, boolean test) {
        return builder().cookie(c).testSession(test).build();
    }

    public static Session create(String c) {
        return create(c, false);
    }

    public static Builder builder() {
        return new AutoParcelGson_Session.Builder();
    }

    @AutoParcelGson.Builder public interface Builder {
        Builder testSession(boolean test);

        Builder cookie(String v);

        Session build();
    }
}
