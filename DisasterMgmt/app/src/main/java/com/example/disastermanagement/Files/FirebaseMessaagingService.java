package com.example.disastermanagement.Files;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.disastermanagement.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Set;

/**
 * Created by NAHUSH RAICHURA on 3/24/2017.
 */

public class FirebaseMessaagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        //remoteMessage.getNotification().getBody();
//        Map<String, String> xx=remoteMessage.getData();
          Intent i = new Intent(this,LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Set s=xx.keySet();
//        for (Object x:s){
//            Map.Entry e=(Map.Entry)x;
//            System.out.println(e.getKey()+"-"+e.getValue());
        //remoteMessage.getNotification().getBody();
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
//        showNotification(remoteMessage.getData().get("message"));

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM TEST")
                .setContentText(remoteMessage.getData().get("messsage"))
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentIntent(pendingIntent);
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());

    }
//    private void showNotification(String message)
//    {
//        Intent i=new Intent(this,ActivityXPTO.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Intent myIntent = new Intent(getApplicationContext(), Confirmation.class);
//        // PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
//                .setAutoCancel(true)
//                .setContentTitle("FCM TEST")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_menu_camera)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0,builder.build());
//    }
}