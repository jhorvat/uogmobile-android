package ca.uoguelph.socs.uog_mobile.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import ca.uoguelph.socs.uog_mobile.R;
import ca.uoguelph.socs.uog_mobile.injection.HasComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.DaggerWebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.injection.component.WebAdvisorComponent;
import ca.uoguelph.socs.uog_mobile.injection.module.WebAdvisorModule;

public class WebAdvisorActivity extends BaseActivity implements HasComponent {

    private WebAdvisorComponent webAdvisorComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initializeInjection();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
              view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show());
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public Object getComponent() {
        return this.webAdvisorComponent;
    }

    private void initializeInjection() {
        this.webAdvisorComponent = DaggerWebAdvisorComponent.builder()
              .applicationComponent(getApplicationComponent())
              .activityModule(getActivityModule())
              .webAdvisorModule(new WebAdvisorModule())
              .build();
    }
}
