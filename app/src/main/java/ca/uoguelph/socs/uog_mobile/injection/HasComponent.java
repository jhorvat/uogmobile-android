package ca.uoguelph.socs.uog_mobile.injection;

/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 *
 * Created by julianhorvat on 2016-01-22.
 */
public interface HasComponent<C> {
    C getComponent();
}
