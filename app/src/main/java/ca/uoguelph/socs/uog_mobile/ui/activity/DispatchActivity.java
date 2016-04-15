package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import com.cesarferreira.rxpaper.RxPaper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-03-30.
 */
public class DispatchActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("In Dispatch, launching new activity");

        final RxPaper paper = RxPaper.with(this);

        paper.exists(CentralLookupActivity.CENTRAL_USER_KEY)
              //.flatMap(exists -> exists ? paper.read(CentralLookupActivity.CENTRAL_USER_KEY)
              //      : Observable.just((User) null))
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(user -> {
                  final Class toLaunch =
                        user ? CentralLookupActivity.class : WebAdvisorActivity.class;
                  final Intent in = new Intent(this, toLaunch);

                  if (toLaunch == WebAdvisorActivity.class) {
                      Timber.d("User has been verified");
                      in.putExtra("user", user);
                  } else {
                      Timber.d("New user, verifying identity");
                  }

                  startActivity(in);
              });
    }
}
