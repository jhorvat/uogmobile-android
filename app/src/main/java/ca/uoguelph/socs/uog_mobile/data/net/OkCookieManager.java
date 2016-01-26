package ca.uoguelph.socs.uog_mobile.data.net;

import android.webkit.CookieManager;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by julianhorvat on 2016-01-26.
 */
@Singleton public class OkCookieManager implements CookieJar {

    private CookieManager manager;

    @Inject public OkCookieManager(CookieManager manager) {
        this.manager = manager;
    }

    @Override public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            manager.setCookie(url.toString(), cookie.toString());
        }
    }

    @Override public List<Cookie> loadForRequest(HttpUrl url) {
        ArrayList<Cookie> cookies = new ArrayList<>();
        String cookieString = manager.getCookie(url.toString());

        if (cookieString != null) {
            for (String s : cookieString.split(";")) {
                cookies.add(Cookie.parse(url, s));
            }
        }

        return cookies;
    }
}
