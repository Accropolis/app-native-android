package fr.accropolis.teamdev.accropolis.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import fr.accropolis.teamdev.accropolis.setting.NotificationScheduleAlarm;

/**
 * Created by nspu on 25/02/17.
 */

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            NotificationScheduleAlarm.Change(prefs, context);
        }
    }
}
