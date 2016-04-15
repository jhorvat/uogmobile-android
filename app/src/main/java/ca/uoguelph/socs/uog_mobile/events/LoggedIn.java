package ca.uoguelph.socs.uog_mobile.events;

import ca.uoguelph.socs.uog_mobile.data.central_lookup.models.User;

/**
 * Created by julianhorvat on 2016-02-02.
 */
public class LoggedIn {
    private final User user;

    public LoggedIn(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
