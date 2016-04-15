package ca.uoguelph.socs.uog_mobile.events;

import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Session;

/**
 * Created by julianhorvat on 2016-02-02.
 */
public class LoggedIn {
    private final Session user;

    public LoggedIn(Session user) {
        this.user = user;
    }
}
