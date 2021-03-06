package com.example.sreet.learning;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.sreet.learning.MainActivity;


public class Notify extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent.getStringExtra("type").equals("timeTable")) {
            Log.i("CHeck", "Receiver");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, TimeTableSetter.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("timetable",
                        "TimeTable",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Notification for classes");
                notificationManager.createNotificationChannel(channel);
            }


            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context, "timetable")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(intent.getStringExtra("type"))
                    .setContentText(intent.getStringExtra("Body"))
                    .setContentInfo("Info")
                    .setPriority(Notification.PRIORITY_MAX)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)

                    //.setSound(defaultSoundUri);
                    .setContentIntent(pendingIntent);

            notificationManager.notify(0, notificationBuilder.build());
        }
        else if(intent.getStringExtra("type").equals("calendar")){
            Log.i("CHe", "Recei");

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(context, CalendarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel2 = new NotificationChannel("calendar",
                        "calendar",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel2.setDescription("Calendar events");
                notificationManager.createNotificationChannel(channel2);
            }


            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context, "calendar")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(intent.getStringExtra("type"))
                    .setContentText(intent.getStringExtra("Body"))
                    .setContentInfo("Info")
                    .setPriority(Notification.PRIORITY_MAX)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)

                    //.setSound(defaultSoundUri);
                    .setContentIntent(pendingIntent);

            notificationManager.notify(1, notificationBuilder.build());
        }
    }
}