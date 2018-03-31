package com.example.disastermanagement.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.disastermanagement.Files.GPSTracker;
import com.example.disastermanagement.Files.Register_Volunteer;
import com.example.disastermanagement.R;
import com.google.android.gms.maps.LocationSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import java.util.HashMap;
import java.util.Map;
public class Volunteer extends android.support.v4.app.Fragment{
    private Button but,but2,but3,but4;
    private FirebaseAuth mauth;
    private FirebaseStorage mstorage;
    private String email,num;
    private ProgressDialog progressDialog;
    private String latitudes, longitudes;
    private LocationListener listener;
    private LocationManager locationManager;
    private DatabaseReference databaseReference;
    private String uid;
    private String data_email,data_phone,data_lat,data_long;
    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.register_user, container, false);
        but=(Button)view.findViewById(R.id.but);
        but2=(Button)view.findViewById(R.id.but2);
        but3=(Button)view.findViewById(R.id.but3);
        but4=view.findViewById(R.id.but4);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),VolunteerTrackingActivity.class));
            }
        });
        databaseReference=FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference("Volunteer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersnapshot: dataSnapshot.getChildren())
                {
                    Register_Volunteer obj=usersnapshot.getValue(Register_Volunteer.class);
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim().equals(obj.email.trim()))
                    {
                        uid=usersnapshot.getKey();
                        data_email=obj.email.trim();
                        data_phone=obj.num.trim();
                        data_lat=obj.lat.trim();
                        data_long=obj.lng.trim();
                        but3.setEnabled(true);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationManager != null){
                    //noinspection MissingPermission
                    locationManager.removeUpdates(listener);
                    Toast.makeText(getContext(),"Listener has been stopped",Toast.LENGTH_LONG).show();
                }
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Listener has been Started..",Toast.LENGTH_LONG).show();
                listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        try {
                            // Toast.makeText(getContext(),Double.toString(location.getLatitude())+"+"+Double.toString(location.getLongitude()) , Toast.LENGTH_SHORT).show();
                            Register_Volunteer obj2=new Register_Volunteer(data_phone,data_email,Double.toString(location.getLatitude()),Double.toString(location.getLongitude()));
                            Map<String, Object> postValues=obj2.toMap();
                            Map<String,Object> childUpdates=new HashMap<>();
                            childUpdates.put("/Volunteer/"+uid,postValues);
                            databaseReference.updateChildren(childUpdates);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }
                    @Override
                    public void onProviderEnabled(String s) {
                    }
                    @Override
                    public void onProviderDisabled(String s) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                };
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps",3000,0,listener);
            }
        });
        email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //mstorage=FirebaseStorage.getInstance().getReference("Volunteers");
        progressDialog=new ProgressDialog(getContext());
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });
        return view;
    }
    public void onTrack(View view){
        Intent intent=new Intent(getContext(),VolunteerTrackingActivity.class);
        startActivity(intent);
    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                progressDialog.setMessage("Registering...");
                progressDialog.show();
                num=edt.getText().toString().trim();
                GPSTracker gps = new GPSTracker(getContext());
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
                latitudes = remSpace(Double.toString(latitude));
                longitudes = remSpace(Double.toString(longitude));
                Register_Volunteer obj=new Register_Volunteer(num,email,latitudes,longitudes);
                FirebaseDatabase.getInstance().getReference("Volunteer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(obj);
                progressDialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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