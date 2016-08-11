package com.tongdao.demo.gcm.gcmservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.tongdao.demo.MainActivity;
import com.tongdao.demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class MyGcmListenerService extends GcmListenerService {

    private static String TAG = MyGcmListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        JSONObject json = new JSONObject();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, JSONObject.wrap(data.get(key)));
            } catch(JSONException e) {
                //Handle exception here
            }
        }

        String type = data.getString("tongrd_type");
        String value = data.getString("tongrd_value");

        String message = data.getString("message");

        String extraData = json.toString();

        Log.e(TAG, "From: " + from);
        Log.e(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message, type, value, extraData);
        // [END_EXCLUDE]
    }

    private void sendNotification(String message, String type, String value, String extraData) {

        Intent intent = new Intent();

        PendingIntent pendingIntent;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("value", value);
        intent.putExtra("NotificationMessage", extraData);

        if( type.equalsIgnoreCase("url") ) {
            intent.setAction(MyGcmPushMessageReceiver.OPEN_URL);
            pendingIntent = PendingIntent.getBroadcast(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }
        else {
            intent.setClass(this, MainActivity.class);
            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}