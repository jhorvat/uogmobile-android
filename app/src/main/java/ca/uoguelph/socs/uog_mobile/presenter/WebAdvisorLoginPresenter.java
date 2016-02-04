package ca.uoguelph.socs.uog_mobile.presenter;

import ca.uoguelph.socs.uog_mobile.util.RxEventBus;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.events.LoggedIn;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import com.squareup.otto.Bus;
import javax.inject.Inject;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-01-31.
 */
@PerActivity public class WebAdvisorLoginPresenter implements Presenter {
    private final WebAdvisorService service;
    private final RxEventBus bus;

    private Subscription webAdvisorSub;

    @Inject public WebAdvisorLoginPresenter(WebAdvisorService webAdvisorService, RxEventBus bus) {
        this.service = webAdvisorService;
        this.bus = bus;
    }

    @Override public void onResume() {

    }

    @Override public void onPause() {
        RxUtils.unsub(webAdvisorSub);
    }

    @Override public void onDestroy() {

    }

    public void loggedIn(String cookie) {
        webAdvisorSub = this.service.login(cookie)
                                    .subscribe(user -> bus.post(new LoggedIn(user)),
                                               throwable -> Timber.d(throwable,
                                                                     "Something failed"));
        //.flatMap(user -> this.service.getSchedule())
        //.subscribe(schedule -> Timber.d(schedule.toString()),
        //           throwable -> Timber.d(throwable,
        //                                 "Something failed"));
    }
}
