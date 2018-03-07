package fr.accropolis.teamdev.accropolis.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

import fr.accropolis.teamdev.accropolis.service.AlarmReceiverSchedule;

/**
 * Created by nspu on 25/02/17.
 */

public class NotificationScheduleAlarm {
    static public void Change(SharedPreferences prefs, Context context){
        boolean activeAlarm = prefs.getBoolean("alarm_new_message", false);

        //Init intent
        Intent intent = new Intent(context, AlarmReceiverSchedule.class );
        PendingIntent pending_intent = PendingIntent.getBroadcast(context, 786649, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //init alarm manager
        AlarmManager alarme_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //reset alarm
        alarme_manager.cancel(pending_intent);

        //setup alarm if activated
        if(activeAlarm){
            String time = prefs.getString("alarm_time", "09:00");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, getHour(time));
            calendar.set(Calendar.MINUTE, getMinute(time));
            calendar.set(Calendar.SECOND, 00);
            alarme_manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pending_intent);
        }
    }

    static int getHour(String time) {
        String[] pieces=time.split(":");

        return(Integer.parseInt(pieces[0]));
    }

    static int getMinute(String time) {
        String[] pieces=time.split(":");

        return(Integer.parseInt(pieces[1]));
    }
}
