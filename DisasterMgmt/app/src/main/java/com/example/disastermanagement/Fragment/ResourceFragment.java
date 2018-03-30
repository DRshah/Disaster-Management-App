package com.example.disastermanagement.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.disastermanagement.Files.Feed;
import com.example.disastermanagement.Files.GPSTracker;
import com.example.disastermanagement.Files.Resource;
import com.example.disastermanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResourceFragment extends android.support.v4.app.Fragment {

    EditText name, loc, contact, amt, desc;
    Button subm;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    Spinner spinner1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resource, container, false);
        name = view.findViewById(R.id.Name);
        contact = view.findViewById(R.id.PhoneNumber);
        amt = view.findViewById(R.id.Quantity);
        desc = view.findViewById(R.id.Description);
        subm = view.findViewById(R.id.submit);
        int reqcode=0;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},reqcode);
        }
        spinner1=view.findViewById(R.id.in);
        List<String> list = new ArrayList<String>();
        list.add("Water");
        list.add("Food");
        list.add("Rescue team");
        list.add("Blankets");
        list.add("First Aid");
        list.add("Battery and torch lights");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int valid = 1;
                if(name.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper NAME", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(contact.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper CONTACT NUMBER", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(amt.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper AMOUNT", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(desc.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper DESCRIPTION", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                if (valid==1) {


                    String nm = remSpace(name.getText().toString().trim());

                    String lat, lon;

                    String ph = remSpace(contact.getText().toString().trim());

                    String amount = remSpace(amt.getText().toString().trim());

                    String itm = (String) spinner1.getSelectedItem();

                    String descr = remSpace(desc.getText().toString().trim());
                    GPSTracker gpsTracker = new GPSTracker(getContext());
                    double latitude = 0.0, longitude = 0.0;

                    // check if GPS enabled
                    if (gpsTracker.canGetLocation()) {

                        latitude = gpsTracker.getLatitude();
                        longitude = gpsTracker.getLongitude();

                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gpsTracker.showSettingsAlert();
                    }
                    lat = latitude + "";
                    lon = longitude + "";

                    Resource data=new Resource(amount,descr,itm,nm,lat,lon,ph);
                    Toast.makeText(getContext(),data.toString(),Toast.LENGTH_LONG).show();
                    databaseReference.child("Resources").child(firebaseUser.getUid()).push().setValue(data);
                }
                /*if(valid==1) {
                    new ResourceSubmit(getApplicationContext()).execute(message);

                }*/


            }
        });

        return view;
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
