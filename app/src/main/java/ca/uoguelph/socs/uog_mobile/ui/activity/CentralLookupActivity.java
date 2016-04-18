package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.data.central_lookup.CentralLookupService;
import ca.uoguelph.socs.uog_mobile.injection.component.CentralLookupComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.DaggerCentralLookupComponent;
import ca.uoguelph.socs.uog_mobile.injection.module.CentralLookupModule;
import ca.uoguelph.socs.uog_mobile.util.RxUtils;
import javax.inject.Inject;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by julianhorvat on 2016-03-30.
 */
public class CentralLookupActivity extends BaseActivity {

    private Subscription lookupSub;

    @Inject CentralLookupService lookupService;

    @Bind(R.id.central_input) EditText centralInput;
    @Bind(R.id.submit_button) Button submitButton;
    @Bind(R.id.loading_indicator) ProgressBar loading;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_lookup);
        ButterKnife.bind(this);
        this.initializeInjection();

        this.initializeEditTextListeners();
    }

    @Override protected void onResume() {
        super.onResume();
        submitButton.setOnClickListener(v -> {
            final String input = centralInput.getText().toString();

            if (!input.equals("example@mail.uoguelph.ca")) {
                lookupSub = lookupService.lookup(input.substring(0, input.length() - 17))
                                         .doOnSubscribe(() -> loading.setVisibility(View.VISIBLE))
                                         .subscribe(user -> {
                                             Timber.d("Found user %s", user.toString());

                                             Intent in = new Intent(this, WebAdvisorActivity.class);
                                             in.putExtra("user", user);
                                             startActivity(in);
                                         }, error -> {
                                             Timber.e(error.getMessage());
                                             Toast.makeText(this,
                                                            "Lookup failed, did you mistype?",
                                                            Toast.LENGTH_LONG).show();
                                             loading.setVisibility(View.GONE);
                                         });
            }
        });
    }

    @Override protected void onPause() {
        super.onPause();
        RxUtils.resetSub(lookupSub);
    }

    private void initializeEditTextListeners() {
        centralInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Stub
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Stub
            }

            @Override public void afterTextChanged(Editable s) {
                if (!s.toString().endsWith("@mail.uoguelph.ca")) {
                    centralInput.setText("example@mail.uoguelph.ca");
                    centralInput.setSelection(0, 7);
                }
            }
        });

        centralInput.setOnFocusChangeListener((v, hasFocus) -> {
            final String input = centralInput.getText().toString();

            if (hasFocus && input.endsWith("@mail.uoguelph.ca")) {
                centralInput.setSelection(0, input.indexOf("@mail.uoguelph.ca"));
            }
        });
    }

    private void initializeInjection() {
        CentralLookupComponent component = DaggerCentralLookupComponent.builder()
                                                                       .applicationComponent(
                                                                             getApplicationComponent())
                                                                       .activityModule(
                                                                             getActivityModule())
                                                                       .centralLookupModule(new CentralLookupModule())
                                                                       .build();
        component.inject(this);
    }
}
