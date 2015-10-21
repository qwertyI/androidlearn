package com.example.qwerty.learn;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class CalarmService extends Service {

    private BootReceiver br;
    private NotificationManager mManager;

    public CalarmService() {
    }

    @Override
    public void onCreate(){
        br = new BootReceiver();
        IntentFilter CalarmIF = new IntentFilter();
        CalarmIF.addAction("android.qwerty.learn.timecalarm");
        registerReceiver(br, CalarmIF);
        super.onCreate();
    }

    public class BootReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Intent CalarmIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, CalarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("TimeCalarm")
                    .setContentText("Stand Up kids;")
                    .setTicker("comein=")
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.comein);
            mManager.notify(1, mBuilder.build());
        }
    }

    @Override
    public void onDestroy(){
        unregisterReceiver(br);
        br = null;
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
