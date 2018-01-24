package fr.accropolis.teamdev.accropolis.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import fr.accropolis.teamdev.accropolis.FakeSchedule;
import fr.accropolis.teamdev.accropolis.R;
import fr.accropolis.teamdev.accropolis.adapter.ScheduleExpListAdapter;
import fr.accropolis.teamdev.accropolis.model.Live;

/**
 * Created by Nicolas Padiou on 25/02/17.
 */

public class ScheduleActivity extends AppCompatActivity {

    ExpandableListView lvSchedule = null;
    List<Live> schedule = null;
    ScheduleExpListAdapter scheduleListAdapter = null;
    TextView tvNone = null;
    TextView tvNoOtherLove = null;

    LinearLayout llLive = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //display arrow
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //display the logo instead of the name
        getSupportActionBar().setLogo(R.drawable.accropolis_ban_mini);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        //fill schedule
        //TODO replace FakeSchedule.loadSchedule() by true data
        this.schedule= FakeSchedule.loadSchedule();
        //this.schedule=new ArrayList<>();
        //this.schedule.add( FakeSchedule.loadSchedule().get(0));


        //init
        this.lvSchedule = (ExpandableListView)findViewById(R.id.lv_schedule);
        this.tvNone = (TextView)findViewById(R.id.tv_none);
        this.tvNoOtherLove = (TextView) findViewById(R.id.tv_no_other_live);
        this.llLive = (LinearLayout) findViewById(R.id.ll_live);

        //Expandable list view
        this.scheduleListAdapter = new ScheduleExpListAdapter(this, schedule);
        this.lvSchedule.setAdapter(scheduleListAdapter);

        this.ProgammationDataChanged();
    }


    public void ProgammationDataChanged(){
        //if no program display message
        this.scheduleListAdapter.notifyDataSetChanged();
        if(this.schedule.size()>0){
            this.lvSchedule.setVisibility(View.VISIBLE);

            //if no program after the next live, display message
            if(this.schedule.size()>1){
                this.tvNoOtherLove.setVisibility(View.GONE);
            }else{
                this.tvNoOtherLove.setVisibility(View.VISIBLE);
            }

            this.tvNone.setVisibility(View.GONE);
        }else{
            this.tvNone.setVisibility(View.VISIBLE);
            this.tvNoOtherLove.setVisibility(View.GONE);
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
}
