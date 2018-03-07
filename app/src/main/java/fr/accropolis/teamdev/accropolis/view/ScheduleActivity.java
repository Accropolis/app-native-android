package fr.accropolis.teamdev.accropolis.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.accropolis.teamdev.accropolis.R;
import fr.accropolis.teamdev.accropolis.adapter.ScheduleExpListAdapter;
import fr.accropolis.teamdev.accropolis.controller.GetSchedule;
import fr.accropolis.teamdev.accropolis.model.Live;

/**
 * Created by nspu on 25/02/17.
 */

public class ScheduleActivity extends AppCompatActivity implements GetSchedule.GetScheduleListener {
    enum DislayUi{
        MANY,
        ONE,
        NOTHING,
        NOLIVE,
        ERROR
    }

    ExpandableListView lvSchedule = null;
    List<Live> schedule = null;
    ScheduleExpListAdapter scheduleListAdapter = null;
    TextView tvNone = null;
    TextView tvNoOtherLive = null;
    LinearLayout llLive = null;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //display arrow
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //display the logo instead of the name
        getSupportActionBar().setLogo(R.drawable.accropolisban);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");



        //init
        this.lvSchedule = (ExpandableListView) findViewById(R.id.lv_schedule);
        this.tvNone = (TextView) findViewById(R.id.tv_none);
        this.tvNoOtherLive = (TextView) findViewById(R.id.tv_no_other_live);
        this.llLive = (LinearLayout) findViewById(R.id.ll_live);

        initSchedule();
    }


    private void initSchedule(){
        displayUi(DislayUi.NOTHING);
        progressDialog = ProgressDialog.show(this, "",
                "Récupération du calendrier", true);
        new GetSchedule(this,this, 14).execute();

    }

    private void displayUi(DislayUi dislayUi){
        switch (dislayUi) {
            case MANY:
                this.lvSchedule.setVisibility(View.VISIBLE);
                this.tvNone.setVisibility(View.GONE);
                this.tvNoOtherLive.setVisibility(View.GONE);
                break;
            case ONE:
                this.lvSchedule.setVisibility(View.VISIBLE);
                this.tvNone.setVisibility(View.GONE);
                this.tvNoOtherLive.setVisibility(View.VISIBLE);
                break;
            case NOTHING:
                this.lvSchedule.setVisibility(View.GONE);
                this.tvNone.setVisibility(View.GONE);
                this.tvNoOtherLive.setVisibility(View.GONE);
                break;
            case NOLIVE:
                this.lvSchedule.setVisibility(View.GONE);
                this.tvNone.setVisibility(View.VISIBLE);
                this.tvNoOtherLive.setVisibility(View.GONE);
                break;
            case ERROR:
                this.lvSchedule.setVisibility(View.GONE);
                this.tvNone.setVisibility(View.VISIBLE);
                this.tvNoOtherLive.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSucces(List<Live> liveList) {
        schedule = liveList;

        if (this.schedule.size() == 0) {
            displayUi(DislayUi.NOLIVE);
        }else{
            this.scheduleListAdapter = new ScheduleExpListAdapter(this, schedule);
            this.lvSchedule.setAdapter(scheduleListAdapter);
            if(schedule.size() > 1){
                displayUi(DislayUi.MANY);
            }else{
                displayUi(DislayUi.ONE);
            }
        }

        progressDialog.cancel();
    }

    @Override
    public void onError(Exception e) {
        displayUi(DislayUi.ERROR);
        progressDialog.cancel();
    }
}
