package com.example.sihsmsreceiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyService extends Service {
    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate()
    {
        super.onCreate();

        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private class SMSreceiver extends BroadcastReceiver
    {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle extras = intent.getExtras();

            String strMessage = "";

            if ( extras != null )
            {
                Object[] smsextras = (Object[]) extras.get( "pdus" );

                for ( int i = 0; i < smsextras.length; i++ )
                {
                    SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                    String strMsgBody = smsmsg.getMessageBody().toString();
                    String strMsgSrc = smsmsg.getOriginatingAddress();

                    strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;
                    String x[]=strMsgBody.split(":");
                    for (int k=0;k<x.length;k++) {
                        System.out.println(x[k]);
                    }
                    System.out.println("yo");
                    if (x.length>9&&x[0].equals("Resource")){

                        Resource r=new Resource(x[1],x[2],x[3],x[4],x[5],x[6],x[7],x[8],x[9]+":"+x[10]+":"+x[11]);
                        sendResToFirebase(r,x[12]);
                        System.out.println(r.toString());

                    }
                    else if (x.length==6&&x[0].equals("Disaster")) {
                        String datetime = get_timestamp();
                        String area = get_city(Double.parseDouble(x[3]), Double.parseDouble(x[4]));
                        Feed feed = new Feed(x[1], x[2], x[3], x[4], datetime, area, x[5]);
                        System.out.println("hello");
                        System.out.println(feed.toString());
                        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, strMessage);
                        sendToFirebase(feed);
                    }
                    //databaseReference.child("Data").child(x[4]).push().setValue(feed);
                }

            }

        }
        void sendToFirebase(Feed feed){
            databaseReference.child("Data").child(feed.getUid()).push().setValue(feed);
        }
        void sendResToFirebase(Resource resource,String id){
            databaseReference.child("Resources").child(id).push().setValue(resource);
        }
        public String get_timestamp()
        { //Long tsLong = System.currentTimeMillis()/1000;

            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(System.currentTimeMillis() * 1000L);
            String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
            return date;
        }
        public String get_city(double lat, double lng) {
            String add="";
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                Address obj = addresses.get(0);
//            add = obj.getAddressLine(0);
//            add = add + " " + obj.getCountryName();
//            add = add + " " + obj.getCountryCode();
//            add = add + " " + obj.getAdminArea();
//            add = add + " " + obj.getPostalCode();
//            add = add + " " + obj.getSubAdminArea();
                add = add + " " + obj.getLocality();
//            add = add + " " + obj.getSubThoroughfare();

                Log.v("IGA", "Address" + add);
                // Toast.makeText(this, "Address=>" + add,
                // Toast.LENGTH_SHORT).show();

                // TennisAppActivity.showDialog(add);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return add;
        }

    }
}
