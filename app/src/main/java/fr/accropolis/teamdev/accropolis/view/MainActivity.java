package fr.accropolis.teamdev.accropolis.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;

import fr.accropolis.teamdev.accropolis.R;

/**
 * Created by nspu on 25/02/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llTipeeeMonth = null;
    LinearLayout llTipeeeDonation = null;
    LinearLayout llContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //display the logo instead of the name
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_accropolis_action_bar);

        //subscribe to topic
        FirebaseMessaging.getInstance().subscribeToTopic("accrolive");


        this.llTipeeeMonth = (LinearLayout) findViewById(R.id.ll_tipeee_month);
        this.llTipeeeMonth.setOnClickListener(this);

        this.llTipeeeDonation = (LinearLayout) findViewById(R.id.ll_tipeee_donation);
        this.llTipeeeDonation.setOnClickListener(this);

        this.llContact = (LinearLayout) findViewById(R.id.ll_mail);
        this.llContact.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_schedule:
                Intent scheduleIntent = new Intent(this, ScheduleActivity.class);
                startActivity(scheduleIntent);
                return true;
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, About.class);
                startActivity(aboutIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_tipeee_month:
                Intent tipeeMonthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tipeee.com/la-vie-publique"));
                startActivity(tipeeMonthIntent);
                break;
            case R.id.ll_tipeee_donation:
                Intent tipeeDonationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tipeeestream.com/accropolis/donation"));
                startActivity(tipeeDonationIntent);
                break;
            case R.id.ll_mail:
                Intent contactIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "contact@accropolis.fr", null));
                startActivity(Intent.createChooser(contactIntent, "Email"));
                break;
        }
    }
}
