package com.example.sreet.learning;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /* There are two types of messages data messages and notification messages. Data messages are handled here in onMessageReceived whether the app is in the foreground or background. Data messages are the type traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app is in the foreground. When the app is in the background an automatically generated notification is displayed. */
        String notificationTitle = null, notificationBody = null;
        String dataTitle = null, dataMessage = null;

        // Check if message contains a data payload.
        //if (remoteMessage.getData().size() > 0) {
        //  Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
        //dataTitle = remoteMessage.getData().get("title");
        //dataMessage = remoteMessage.getData().get("message");
        //}

        // Check if message contains a notification payload.
        //if (remoteMessage.getNotification() != null) {
        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        notificationTitle = remoteMessage.getNotification().getTitle();
        notificationBody = remoteMessage.getNotification().getBody();
        //}

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(notificationTitle, notificationBody);
    }


    /**
     //     * Create and show a simple notification containing the received FCM message.
     //     */
    private void sendNotification(String notificationTitle, String notificationBody) {
        Intent intent;
        if(notificationTitle=="Notice from firstYear") {
            intent = new Intent(this, YearNoticesDescript.class);
        }
        else if(notificationTitle=="Notice from secondYear"){
            intent = new Intent(this, YearNoticesDescript.class);
        }
        else if(notificationTitle=="Notice from thirdYear"){
            intent = new Intent(this, YearNoticesDescript.class);
        }
        else if(notificationTitle=="Notice from fourthYear"){
            intent = new Intent(this, YearNoticesDescript.class);
        }
        else{
            intent = new Intent(this, Notices.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default1",
                    "NoticesNotify",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notify on notice updated");
            notificationManager.createNotificationChannel(channel);
        }
        //Uri defaultSoundUri= Uri.parse("android.resource://" + getPackageName() + "/raw/knock_brush");
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this,"default1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setContentInfo("Info")
                .setPriority(Notification.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)

                //.setSound(defaultSoundUri);
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[0]);


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}