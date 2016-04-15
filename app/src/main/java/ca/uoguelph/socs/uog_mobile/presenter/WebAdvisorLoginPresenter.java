package ca.uoguelph.socs.uog_mobile.presenter;

import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.events.LoggedIn;
import ca.uoguelph.socs.uog_mobile.injection.scope.PerActivity;
import ca.uoguelph.socs.uog_mobile.util.RxEventBus;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
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
        this.webAdvisorSub = RxUtils.resetSub(webAdvisorSub);
    }

    @Override public void onResume() {

    }

    @Override public void onPause() {
        RxUtils.resetSub(webAdvisorSub);
    }

    @Override public void onDestroy() {

    }

    public void login(String cookie) {
        webAdvisorSub = service.login(cookie)
                               .subscribe(session -> bus.post(new LoggedIn(session)),
                                          e -> Timber.d(e, "Something failed"),
                                          () -> webAdvisorSub = RxUtils.resetSub(webAdvisorSub));
    }
}
