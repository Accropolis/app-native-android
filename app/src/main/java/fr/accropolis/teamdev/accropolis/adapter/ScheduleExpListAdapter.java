package fr.accropolis.teamdev.accropolis.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.accropolis.teamdev.accropolis.R;
import fr.accropolis.teamdev.accropolis.model.Live;
import fr.accropolis.teamdev.accropolis.tools.Format;
import fr.accropolis.teamdev.accropolis.tools.smartimage.SmartImage;
import fr.accropolis.teamdev.accropolis.tools.smartimage.SmartImageView;

/**
 * Created by nspu on 25/02/17.
 *
 * Bridge between the data and the view.
 */
public class ScheduleExpListAdapter extends BaseExpandableListAdapter{

    Context context;
    List<Live> schedule;
    private LayoutInflater layoutInflater;

    public ScheduleExpListAdapter(Context context, List<Live> schedule) {
        this.context = context;
        this.schedule = schedule;
    }

    @Override
    public int getGroupCount() {
        return this.schedule.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(schedule.get(groupPosition).getContent() ==null || schedule.get(groupPosition).getContent().isEmpty()){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.schedule.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // unused because only one child
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (this.layoutInflater == null) {
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.schedule_list_item, null);
        }
        //current
        Live live = this.schedule.get(groupPosition);

        //instantiate view component
        SmartImageView ivImage = (SmartImageView) convertView.findViewById(R.id.iv_image) ;
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        LinearLayout llLive = (LinearLayout)convertView.findViewById(R.id.ll_live);
        TextView tvCalendar = (TextView)convertView.findViewById(R.id.tv_calendar);
        TextView tvNextLive = (TextView)convertView.findViewById(R.id.tv_nextlive);
        TextView tvDateNextLive = (TextView)convertView.findViewById(R.id.tv_date_next_live);


        //display first and seconde item with specific config
        switch (groupPosition){
            case 0:
                tvCalendar.setVisibility(View.GONE);
                tvNextLive.setVisibility(View.VISIBLE);
                tvNextLive.setBackgroundColor(Color.WHITE);
                tvTitle.setTextColor(Color.WHITE);
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorNextLive));
                break;
            case 1:
                tvCalendar.setVisibility(View.VISIBLE);
                tvNextLive.setVisibility(View.GONE);
                convertView.setBackgroundColor(Color.WHITE);
                break;
            default:
                tvCalendar.setVisibility(View.GONE);
                tvNextLive.setVisibility(View.GONE);
                convertView.setBackgroundColor(Color.WHITE);
                break;
        }

        //display date
        String dayLongName = live.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String upperString = dayLongName.substring(0,1).toUpperCase() + dayLongName.substring(1);
        String dayHour = upperString + " " + Format.displayTime(live.getDate().get(Calendar.HOUR_OF_DAY), live.getDate().get(Calendar.MINUTE), "H");
        if(groupPosition==0){
            tvDateNextLive.setText(dayHour);
            tvDateNextLive.setVisibility(View.VISIBLE);
            tvDate.setVisibility(View.GONE);
        }else{
            tvDate.setText(dayHour);
            tvDate.setVisibility(View.VISIBLE);
            tvDateNextLive.setVisibility(View.GONE);
        }


        //altenate position image and text
        if(groupPosition%2==0){
            llLive.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_LTR);
        }else  {
            llLive.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
        }

        //display image
        if(live.getUriImage()!=null && !live.getUriImage().isEmpty()){
            ivImage.setImageUri(live.getUriImage());
        }else{
            ivImage.setVisibility(View.GONE);
        }

        tvTitle.setText(live.getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Live live = this.schedule.get(groupPosition);

        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.schedule_list_extend, null);
        }




        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
        tvContent.setText(live.getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

