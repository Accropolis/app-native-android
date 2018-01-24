/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.accropolis.teamdev.accropolis.service.firebase;

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
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import fr.accropolis.teamdev.accropolis.R;


public class AccroFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "AccroFirebaseMsgService";



    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]


        String message = "";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //check if notification is enable in the preference (see settingsActivity notification)
        if(!preferences.getBoolean("notifications_new_message", false)){
            return;
        }

        //check if it background or foreground notification
        if(remoteMessage.getNotification() != null){
            message = remoteMessage.getNotification().getBody();
        }else{
            Map<String, String> data = remoteMessage.getData();
            message = data.get("message");
        }

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        sendNotification(message);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/accropolis"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String ringtone = preferences.getString("notifications_new_message_ringtone", "");

        if(ringtone != ""){
            soundUri = Uri.parse(ringtone);
        }

        boolean vibrate = preferences.getBoolean("notifications_new_message_vibrate", false);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(icon)
                .setContentTitle("Accropolis Live")
                .setSmallIcon(R.drawable.ic_accropolis_action_bar)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);


        if(vibrate){
            notificationBuilder.setVibrate(new long[] { 1000,1000,1000});
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
