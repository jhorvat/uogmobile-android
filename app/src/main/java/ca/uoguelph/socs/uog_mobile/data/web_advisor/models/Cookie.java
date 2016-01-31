package ca.uoguelph.socs.uog_mobile.data.web_advisor.models;

import auto.parcelgson.AutoParcelGson;

/**
 * Created by julianhorvat on 2016-01-25.
 */
@AutoParcelGson public abstract class Cookie {
    abstract String value();

    public static Cookie create(String v) {
        return builder().value(v).build();
    }

    public static Builder builder() {
        return new AutoParcelGson_Cookie.Builder();
    }

    @AutoParcelGson.Builder public interface Builder {
        Builder value(String v);

        Cookie build();
    }
}
