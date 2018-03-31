package com.example.disastermanagement.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disastermanagement.Files.GPSTracker;
import com.example.disastermanagement.Files.Feed;
import com.example.disastermanagement.Files.LoginActivity;
import com.example.disastermanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Entry extends android.support.v4.app.Fragment {
    TextView textView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    Spinner spinner1;
    EditText descr;
    Button submit;

    private Button selectimage;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT=2;
    private ProgressDialog progressdialog;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri uri;
    private String image;
    private String ts;
    private String category,descrip,latitudes,longitudes;
    private String area;
    private SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_entry,container,false);
        //imageView= (ImageView) view.findViewById(R.id.gimg);
        int reqcode=0;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},reqcode);
        }

        progressdialog=new ProgressDialog(getActivity());


        spinner1 = view.findViewById(R.id.spinner1);
        descr = view.findViewById(R.id.Description1);
        submit = view.findViewById(R.id.submit1);

        List<String> list = new ArrayList<String>();
        list.add("Deceased Victim");
        list.add("Stranded Victim");
        list.add("Route Blocked");
        list.add("Animal Issue");
        list.add("Fire");
        list.add("Structure Damage");
        list.add("Water Logging");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getContext(),LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mStorage= FirebaseStorage.getInstance().getReference();
        selectimage=(Button)view.findViewById(R.id.uploadimage);
        if(!isNetworkAvailable()){
            selectimage.setEnabled(false);
        }
        imageView = (ImageView) view.findViewById(R.id.imageView);

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

// Create a reference with an initial file path and name
        //StorageReference pathReference = storageRef.child("images/stars.jpg");

// Create a reference to a file from a Google Cloud Storage URI
        // StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");

// Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
        //StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                category = String.valueOf(spinner1.getSelectedItem());
                descrip = descr.getText().toString().trim();

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

                Toast.makeText(getContext(),
                        "Spinner 1 : "+ String.valueOf(spinner1.getSelectedItem() +
                                "\nLat: " + latitude + " Long: " + longitude +
                                "\nDesc: "+ descrip),
                        Toast.LENGTH_SHORT).show();


                int valid = 1;
                if(descrip.equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper DESCRIPTION", Toast.LENGTH_LONG).show();
                    valid=0;
                }

                category = remSpace(category);
                descrip = remSpace(descrip);
                latitudes = remSpace(Double.toString(latitude));
                longitudes = remSpace(Double.toString(longitude));

                String message = category+","+descrip+","+latitudes+","+longitudes;

                if(isNetworkAvailable())
                {
                    if(valid==1) {
                        //new EntrySubmit(getContext()).execute(message);


                        //Toast.makeText(getContext(),"inside if statement",Toast.LENGTH_LONG).show();

                        area=get_city(latitude,longitude);

                        progressdialog.setMessage("Uploading data......");
                        progressdialog.show();
                        Long tsLong = System.currentTimeMillis()/1000;
                        ts = tsLong.toString();
                        StorageReference filepath=mStorage.child("Photos").child(ts);

                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // progressdialog.dismiss();
                                Toast.makeText(getContext(),"Upload Done",Toast.LENGTH_LONG).show();
                                storageRef.child("Photos/"+ts).getDownloadUrl().addOnSuccessListener(new          OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        image=uri.toString();
                                        Toast.makeText(getContext(),uri.toString(),Toast.LENGTH_LONG).show();
                                        SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yy HH:mm:ss");
                                        String fid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        System.out.println(fid+"FID");

                                        Feed data=new Feed(category,descrip,latitudes,longitudes,uri.toString(),get_timestamp(),fid,area);
                                        databaseReference.child("Data").child(firebaseUser.getUid()).push().setValue(data);
                                        descr.setText("");
                                        imageView.setImageBitmap(null);
                                        progressdialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors

                                    }
                                });

                            }


                        });




                    }
                }
                else{

                    preferences=getContext().getSharedPreferences("GoogleInfo",MODE_PRIVATE);
                    String id=preferences.getString("personID","");
                    String msg="Disaster"+category+":"+descrip+":"+latitude+":"+longitude+":"+id;
                    progressdialog.setMessage("Sending sms.....");

                    progressdialog.show();
                   if( sendSMS("8082191919",msg)){
                       progressdialog.dismiss();
                       Toast.makeText(getContext(),"SMS sent",Toast.LENGTH_SHORT).show();
                   }
                   else {
                       progressdialog.dismiss();
                       Toast.makeText(getContext(),"SMS NO",Toast.LENGTH_SHORT).show();
                   }


                }


            }

        });

        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {


            uri=data.getData();

            Picasso.with(getActivity()).load(uri).into(imageView);






        }
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
    private boolean sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        try {

            sms.sendTextMessage(phoneNumber, null, message, null, null);
            return true;

        }
        catch (Exception e){
             e.printStackTrace();
             return false;
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public String get_city(double lat, double lng) {
        String add="";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return add;
    }

    public String get_timestamp()
    { //Long tsLong = System.currentTimeMillis()/1000;

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(System.currentTimeMillis() * 1000L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        return date;
    }
}
