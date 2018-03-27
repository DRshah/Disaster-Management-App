package vshah2212.sih;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class EntryFormActivity extends AppCompatActivity {

    Spinner spinner1;
    EditText descr;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_form);

        int reqcode=0;
        if (ContextCompat.checkSelfPermission(EntryFormActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EntryFormActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},reqcode);
        }

        spinner1 = findViewById(R.id.spinner1);
        descr = findViewById(R.id.Description1);
        submit = findViewById(R.id.submit1);

        List<String> list = new ArrayList<String>();
        list.add("Deceased Victim");
        list.add("Stranded Victim");
        list.add("Route Blocked");
        list.add("Animal Issue");
        list.add("Fire");
        list.add("Structure Damage");
        list.add("Water Logging");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String category = String.valueOf(spinner1.getSelectedItem());
                String descrip = descr.getText().toString().trim();

                GPSTracker gps = new GPSTracker(EntryFormActivity.this);
                double latitude = 0.0,longitude = 0.0;

                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

                Toast.makeText(EntryFormActivity.this,
                        "Spinner 1 : "+ String.valueOf(spinner1.getSelectedItem() +
                                "\nLat: " + latitude + " Long: " + longitude +
                                   "\nDesc: "+ descrip),
                        Toast.LENGTH_SHORT).show();


                int valid = 1;
                if(descrip.equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(), "Enter proper DESCRIPTION", Toast.LENGTH_LONG).show();
                    valid=0;
                }

                category = remSpace(category);
                descrip = remSpace(descrip);
                String latitudes = remSpace(Double.toString(latitude));
                String longitudes = remSpace(Double.toString(longitude));

                String message = category+","+descrip+","+latitudes+","+longitudes;

                if(valid==1) {
                    new EntrySubmit(getApplicationContext()).execute(message);

                }

            }

        });

    }

    public String remSpace(String old)
    {
        String new1="";
        while(old.contains(" "))
        {
            int ind=old.indexOf(" ");
            old = old.substring(0,ind)+"."+old.substring(ind+1);
        }

        new1 = old;
        return new1;
    }
}
