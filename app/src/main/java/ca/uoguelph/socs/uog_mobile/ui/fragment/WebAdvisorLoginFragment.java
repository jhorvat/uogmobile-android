package ca.uoguelph.socs.uog_mobile.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.web_advisor.WebAdvisorService;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import javax.inject.Inject;
import rx.Subscription;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class WebAdvisorLoginFragment extends BaseFragment {

    public static final String LOGIN_URL =
          "https://webadvisor.uoguelph.ca/WebAdvisor/WebAdvisor?CONSTITUENCY=WBDF&type=P&pid=UT-LGRQ&PROCESS=-UTAUTH01";
    public static final String LOGGED_IN_URL_PREFIX =
          "https://webadvisor.uoguelph.ca/WebAdvisor/WebAdvisor?TYPE=M&PID=CORE-WBMAIN&TOKENIDX=";

    @Inject String mockString;
    @Inject WebAdvisorService webAdvisorService;

    @Bind(R.id.webview) WebView webView;

    private CookieManager cookieManager;
    private Subscription webAdvisorSub;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(WebAdvisorComponent.class).inject(this);
        Timber.d("Hi, we are %s", mockString);
        Timber.d("WebAdvisor %s", webAdvisorService);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookieManager = CookieManager.getInstance();

        Timber.d("Frag started has cookies: %s", cookieManager.hasCookies());

        if (Build.VERSION.SDK_INT >= 21) {
            //cookieManager.setAcceptThirdPartyCookies(webView, true);
            cookieManager.removeAllCookies(value -> {
                Timber.d("Frag started has cookies: %s %s", value, cookieManager.hasCookies());
                loadLogin();
            });
        } else {
            cookieManager.removeAllCookie();
            Timber.d("Frag started has cookies: %s", cookieManager.hasCookies());
            loadLogin();
        }
    }

    @Override public void onPause() {
        super.onPause();

        webAdvisorSub.unsubscribe();
    }

    @SuppressLint("SetJavaScriptEnabled") private void loadLogin() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.startsWith(LOGGED_IN_URL_PREFIX)) {
                    String cookie = CookieManager.getInstance().getCookie(url);
                    Timber.d("Page loaded, %s \n Cookies: %s", url, cookie);

                    webAdvisorSub = webAdvisorService.login(cookie)
                                                     .flatMap(user -> webAdvisorService.getSchedule())
                                                     .subscribe(schedule -> Timber.d(schedule.toString()),
                                                                throwable -> Timber.d(throwable,
                                                                                      "Something failed"));
                }
            }
        });
        webView.loadUrl(LOGIN_URL);
    }
}
