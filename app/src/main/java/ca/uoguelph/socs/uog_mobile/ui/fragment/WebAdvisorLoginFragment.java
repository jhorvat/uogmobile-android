package ca.uoguelph.socs.uog_mobile.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.presenter.WebAdvisorLoginPresenter;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class WebAdvisorLoginFragment extends BaseFragment {

    public static final String LOGIN_URL =
          "https://webadvisor.uoguelph.ca/WebAdvisor/WebAdvisor?CONSTITUENCY=WBDF&type=P&pid=UT-LGRQ&PROCESS=-UTAUTH01";
    public static final String LOGGED_IN_URL_PREFIX =
          "https://webadvisor.uoguelph.ca/WebAdvisor/WebAdvisor?TYPE=M&PID=CORE-WBMAIN&TOKENIDX=";

    @Inject Context context;
    @Inject WebAdvisorLoginPresenter presenter;
    @Inject CookieManager cookieManager;

    @Bind(R.id.webview) WebView webView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(WebAdvisorComponent.class).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_advisor_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
            cookieManager.removeAllCookies(value -> loadLogin());
        } else {
            cookieManager.removeAllCookie();
            loadLogin();
        }
    }

    @Override public void onResume() {
        super.onResume();

        presenter.onResume();
    }

    @Override public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @SuppressLint("SetJavaScriptEnabled") private void loadLogin() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            private boolean loggedIn = false;

            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.startsWith(LOGGED_IN_URL_PREFIX) && !loggedIn) {
                    loggedIn = true;
                    String cookie = cookieManager.getCookie(url);
                    Timber.d("Page loaded, %s \n Cookies: %s", url, cookie);
                    presenter.login(cookie);
                }
            }
        });
        webView.loadUrl(LOGIN_URL);
    }
}
