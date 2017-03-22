package edu.csulb.android.assigment3_drawcustomview;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PaintBroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.stat_notify_error)
                    .setContentTitle("Notification!")
                    .setContentText("Test Hello World")
                    .setAutoCancel(true);
            PendingIntent pi = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
            mBuilder.setContentIntent(pi);


            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());


        }
        /*Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

        }
    }
}
