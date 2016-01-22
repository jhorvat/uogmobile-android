package ca.uoguelph.socs.uog_mobile.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by julianhorvat on 2016-01-21.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}