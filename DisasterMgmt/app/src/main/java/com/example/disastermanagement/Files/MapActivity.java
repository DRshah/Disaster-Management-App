package com.example.disastermanagement.Files;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disastermanagement.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TrackGPS gps=new TrackGPS(getContext());
    public Context context;
    Toolbar toolbar;
    Intent intent;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //context = MapActivity.this;
//
//
//        setContentView(R.layout.activity_map);
//
//        ActionBar actionBar=getSupportActionBar();
//        assert actionBar!=null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        intent=new Intent(getApplicationContext(),HomePage.class);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//
//
//    }


    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            getMapAsync(this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(17.4898, 78.44973) , 10.0f) );


//        double lat=gps.getLatitude();
//        double lon=gps.getLongitude();
//        System.out.println("location"+ lon+"  hello "+lat);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(17.370855, 78.508603);
        String add=getAddress(17.4898, 78.44973);
        System.out.println(add);

        BitmapDescriptor iconhosp = BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital_name);
        BitmapDescriptor iconfire = BitmapDescriptorFactory.fromResource(R.drawable.ic_fire_name);

//Doctor
        LatLng CGH = new LatLng(17.31878, 78.48163);
        LatLng AHG = new LatLng(17.3852, 78.40259);
        LatLng LH = new LatLng(17.4898, 78.44973);
        LatLng RRH = new LatLng(17.49043, 78.40688);
        LatLng AH = new LatLng(17.54138, 78.49171);
        LatLng LHH = new LatLng(17.40416, 78.46086);
//Firestation

        LatLng BFS = new LatLng(17.47251, 78.43166);
        LatLng FSN = new LatLng(17.45684, 78.43788);
        LatLng IICT = new LatLng(17.4201, 78.54599);
        LatLng MFS = new LatLng(17.3710, 78.5038);


        LatLng currentloc=new LatLng(17.607042, 78.486475);

        mMap.addMarker(new MarkerOptions().position(currentloc).title("CMR college of Engineering"));


        mMap.addMarker(new MarkerOptions().position(CGH).title("Charminar Govt Hospital")).setIcon(iconhosp);
        mMap.addMarker(new MarkerOptions().position(AHG).title("Area Hospital Golkonda")).setIcon(iconhosp);
        mMap.addMarker(new MarkerOptions().position(LH).title("Lalita Hospital")).setIcon(iconhosp);
        mMap.addMarker(new MarkerOptions().position(RRH).title("Ramdev Rao Hospital")).setIcon(iconhosp);
        mMap.addMarker(new MarkerOptions().position(AH).title("Ashrita Hospital")).setIcon(iconhosp);
        mMap.addMarker(new MarkerOptions().position(LHH).title("Lotus Hospital")).setIcon(iconhosp);

        mMap.addMarker(new MarkerOptions().position(BFS).title("BalNagar Fire station")).setIcon(iconfire);
        mMap.addMarker(new MarkerOptions().position(FSN).title("Firestation sanath Nagar")).setIcon(iconfire);
        mMap.addMarker(new MarkerOptions().position(IICT).title("IICT Fire station")).setIcon(iconfire);
        mMap.addMarker(new MarkerOptions().position(MFS).title("MalakPet Firestation")).setIcon(iconfire);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v= getLayoutInflater().inflate(R.layout.info_window,null);

                TextView locality=(TextView)v.findViewById(R.id.locality);
                TextView call=(Button)v.findViewById(R.id.call);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+918369818613", null));
                        startActivity(intent);
                    }
                });

                locality.setText(marker.getTitle());



                return v;
            }
        });


//        CameraPosition cameraPosition= new CameraPosition.Builder().target(sydney).zoom(8).build();

//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public String getAddress(double lat, double lng) {
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
//            add = add + " " + obj.getLocality();
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
//    private GoogleMap mMap;
//    private TrackGPS gps=new TrackGPS(getContext());
//    public Context context;
//    Toolbar toolbar;
//    Intent intent;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        //context = MapActivity.this;
////
////
////        setContentView(R.layout.activity_map);
////
////        ActionBar actionBar=getSupportActionBar();
////        assert actionBar!=null;
////        actionBar.setDisplayHomeAsUpEnabled(true);
////        actionBar.setHomeButtonEnabled(true);
////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                .findFragmentById(R.id.map);
////        mapFragment.getMapAsync(this);
////
////        intent=new Intent(getApplicationContext(),HomePage.class);
////
////        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
////
////
////    }
//
//
//    @Override
//    public void onResume() {
//            super.onResume();
//        setUpMapIfNeeded();
//    }
//
//    private void setUpMapIfNeeded() {
//        if (mMap == null) {
//            getMapAsync(this);
//        }
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//        double lat=gps.getLatitude();
//        double lon=gps.getLongitude();
//        System.out.println("location"+ lon+"  hello "+lat);
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(lat,lon);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        CameraPosition cameraPosition= new CameraPosition.Builder().target(sydney).zoom(8).build();
//
//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }


}
