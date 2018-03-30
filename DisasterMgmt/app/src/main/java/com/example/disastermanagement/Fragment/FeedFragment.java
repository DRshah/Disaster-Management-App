package com.example.disastermanagement.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disastermanagement.Files.Feed;
import com.example.disastermanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;


public class FeedFragment extends android.support.v4.app.Fragment {


    private ListView feedListView;
    private DatabaseReference mDatabase;
    private ArrayList<Feed> feedsList;
    private FeedAdapter feedAdapter;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_feed, container, false);
        feedListView=view.findViewById(R.id.feed_lv);
        textView=view.findViewById(R.id.tv_fragmentfeed);

        feedsList=new ArrayList<Feed>();
        feedAdapter=new FeedAdapter();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("asdasd", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(valueEventListener);

        return view;
    }
    class FeedHolder{
        TextView emergency_category,location;
    }

    class FeedAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeedHolder holder=null;
            if(convertView==null){
                LayoutInflater li=getLayoutInflater();
                convertView=li.inflate(R.layout.feed_list_layout,null);
                holder=new FeedHolder();
                holder.emergency_category=convertView.findViewById(R.id.tv_disaster_type);
                holder.location=convertView.findViewById(R.id.tv_disaster_location);
                convertView.setTag(holder);
            }
            else{
                holder=(FeedHolder)convertView.getTag();
            }
            Feed f= (Feed) getItem(position);
            holder.emergency_category.setText(f.getCategory());
            holder.location.setText(f.getLat()+","+f.getLon());
            return convertView;
        }
    }





}
