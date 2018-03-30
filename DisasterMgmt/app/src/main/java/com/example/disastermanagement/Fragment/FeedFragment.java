package com.example.disastermanagement.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disastermanagement.Files.AndroidVersion;
import com.example.disastermanagement.Files.DataAdapter;
import com.example.disastermanagement.Files.Feed;
import com.example.disastermanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;


public class FeedFragment extends android.support.v4.app.Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String description,area,image,dateTime,category;
    ArrayList<Feed> feedArrayList;


    private RecyclerView recyclerView;
    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1522407330?alt=media&token=c61c92bf-d5c0-4d63-b518-a4be62d489cb",
            "https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1522407330?alt=media&token=c61c92bf-d5c0-4d63-b518-a4be62d489cb",
            "https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1522407330?alt=media&token=c61c92bf-d5c0-4d63-b518-a4be62d489cb",
            "https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1522407330?alt=media&token=c61c92bf-d5c0-4d63-b518-a4be62d489cb",
            "https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1522407330?alt=media&token=c61c92bf-d5c0-4d63-b518-a4be62d489cb",
            "https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1522407330?alt=media&token=c61c92bf-d5c0-4d63-b518-a4be62d489cb"
    };
    private final String cat[]=new String[1000];
    private int ctr=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        feedArrayList=new ArrayList<Feed>();

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("Data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot usersnapshot:dataSnapshot.getChildren()){
                    System.out.println("outer");
                    //
                    for(DataSnapshot entry:usersnapshot.getChildren()){
                        System.out.println("2nd loop");

                            Feed feed = entry.getValue(Feed.class);
                            feedArrayList.add(feed);
                            /*category = feed.category;

                            System.out.println(category);
                            cat[ctr] = category;
                            ctr++;*/


                    }
                }
                initViews();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error"+databaseError.getMessage());
            }
        });
        return view;
    }

    private void initViews(){

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //prepareData();
        DataAdapter adapter = new DataAdapter(getContext(),feedArrayList);
        recyclerView.setAdapter(adapter);

    }
    private void prepareData(){

        for (Feed c:feedArrayList){
            if (c.getImage()==""){
                c.setImage("https://firebasestorage.googleapis.com/v0/b/sih2018-11ae3.appspot.com/o/Photos%2F1024px-No_image_available.png?alt=media&token=adf11caa-adf6-4b98-a86c-876bbe9ef8de");
            }
        }

        /*ArrayList android_version = new ArrayList<>();
        for(int i=0;i<cat.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(cat[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;*/
    }
}
