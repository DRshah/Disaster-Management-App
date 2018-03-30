package com.example.disastermanagement.Files;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disastermanagement.R;

public class Entry extends android.support.v4.app.Fragment {
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_entry,container,false);
        //imageView= (ImageView) view.findViewById(R.id.gimg);
        textView= (TextView) view.findViewById(R.id.entry_frag);
        return view;
    }
}
