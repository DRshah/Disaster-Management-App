package com.example.sihsmsreceiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Network;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.solver.Cache;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.x;
import static android.R.id.message;

public class SmsProcessService extends Service {
    SmsReceiver smsReceiver;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        smsReceiver = new SmsReceiver();
        registerReceiver(smsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        System.out.println("On start command method");
        Toast.makeText(this, "onStart command", Toast.LENGTH_LONG).show();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        return START_STICKY;
    }

    public class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle myBundle = intent.getExtras();
            SmsMessage[] messages = null;
            String strMessage = "";
            String res = "";
            System.out.println("On receive method");
            Toast.makeText(context, "onreceive", Toast.LENGTH_LONG).show();
            SmsManager sms = SmsManager.getDefault();
            if (myBundle != null) {
                Object[] pdus = (Object[]) myBundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                    strMessage += " : ";
                    strMessage += messages[i].getMessageBody();
                    strMessage += "\n";
                }

                final String mess = messages[0].getDisplayMessageBody();
                final String sender = messages[0].getDisplayOriginatingAddress();
                final String x[] = mess.split(":");

                Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                System.out.println(sender);
                System.out.println(mess);

                String category=x[0],descrip=x[1],latitude=x[2],longitude=x[3],time=x[4],firebaseid=x[5],area=x[6];
                System.out.println("category:"+category+"description:"+descrip+"latitude:"+latitude+"longitude:"+longitude+"time:"+time+"id:"+firebaseid+"area:"+area);
                Feed data=new Feed(category,descrip,latitude,longitude,time,area,firebaseid);
                Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_LONG);
                //databaseReference.child("Data").child(firebaseid).push().setValue(data);



            }


        }
    }
}