package fr.accropolis.teamdev.accropolis.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.GetChars;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import fr.accropolis.teamdev.accropolis.R;
import fr.accropolis.teamdev.accropolis.controller.GetSchedule;
import fr.accropolis.teamdev.accropolis.view.ScheduleActivity;
import fr.accropolis.teamdev.accropolis.model.Live;

/**
 * Created by nspu on 25/02/17.
 */


public class AlarmReceiverSchedule extends WakefulBroadcastReceiver implements GetSchedule.GetScheduleListener{
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        new GetSchedule(context,this, 1).execute();
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param schedule FCM message body received.
     */
    private void sendNotification(List<Live>  schedule, Context cont) {

        Intent notificationIntent = new Intent(cont, ScheduleActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(cont, 0, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);

        String ringtone = preferences.getString("alarm_new_message_ringtone", "");
        if(ringtone != ""){
            soundUri = Uri.parse(ringtone);
        }

        boolean vibrate = preferences.getBoolean("alarm_new_message_vibrate", false);

        Bitmap icon = BitmapFactory.decodeResource(cont.getResources(), R.mipmap.ic_launcher);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for(Iterator<Live> live = schedule.iterator(); live.hasNext(); ) {
            Live item = live.next();
            inboxStyle.addLine(item.getDate().get(Calendar.HOUR_OF_DAY) + "H" + String.format("%02d", schedule.get(0).getDate().get(Calendar.MINUTE))+ " " + item.getTitle());
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(cont)
                .setLargeIcon(icon)
                .setContentTitle("Accropolis Programmation")
                .setSmallIcon(R.drawable.ic_accropolis_action_bar)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setContentText(schedule.get(0).getDate().get(Calendar.HOUR_OF_DAY) + "H" + String.format("%02d", schedule.get(0).getDate().get(Calendar.MINUTE))+ " " + schedule.get(0).getTitle() + (schedule.size() > 1?" ...":""));

        if(vibrate){
            notificationBuilder.setVibrate(new long[] { 1000,1000,1000});
        }

        NotificationManager notificationManager =
                (NotificationManager) cont.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());
    }

    @Override
    public void onSucces(List<Live> liveList) {
        List<Live> schedule = liveList;

        if(schedule.size() > 0){
            this.sendNotification(schedule, mContext);
        }
    }

    @Override
    public void onError(Exception e) {

    }
}