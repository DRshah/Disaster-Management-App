package com.example.sihsmsreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onSartService(View view){
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
    }
    /*public void onStartService(View view){
        Intent smsServiceInstance=new Intent(this,SmsProcessService.class);
        startService(smsServiceInstance);
    }*/
    public void onStopService(View view){
        stopService(new Intent(this, SmsProcessService.class));
    }
}
