package com.example.disastermanagement.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String category = String.valueOf(spinner1.getSelectedItem());
                String descrip = descr.getText().toString().trim();

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
                String latitudes = remSpace(Double.toString(latitude));
                String longitudes = remSpace(Double.toString(longitude));

                String message = category+","+descrip+","+latitudes+","+longitudes;

                if(valid==1) {
                    //new EntrySubmit(getContext()).execute(message);

                    progressdialog.setMessage("Uploading data......");
                    progressdialog.show();
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    StorageReference filepath=mStorage.child("Photos").child(ts);

                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           // progressdialog.dismiss();
                           // Toast.makeText(getContext(),"Upload Done",Toast.LENGTH_LONG).show();
                        }


                    });
                    mStorage.child("Photos"+ts).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            image=uri.toString();
                            Toast.makeText(getContext(),uri.toString(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });


                    Feed data=new Feed(category,descrip,latitudes,longitudes,image);
                    databaseReference.child("Data").child(firebaseUser.getUid()).push().setValue(data);
                    progressdialog.dismiss();

                }

            }

        });

        mStorage= FirebaseStorage.getInstance().getReference();
        selectimage=(Button)view.findViewById(R.id.uploadimage);
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
}
