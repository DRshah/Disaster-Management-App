package com.example.disastermanagement.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.disastermanagement.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Darsh_Shah on 24-03-2018.
 */

public class FragmentAndroid extends Fragment {
    LinearLayout milk,vegetable,fruit,exotic_vegetable,exotic_fruit;
    ImageView img1,img2,img3,img4,img5;
    Context con;
    TextView textView;
    private static final String KEY_MOVIE_TITLE = "key_title";

    public FragmentAndroid() {
        // Required empty public constructor
    }


    public static FragmentAndroid newInstance(String movieTitle) {
        FragmentAndroid fragmentComedy = new FragmentAndroid();
        Bundle args = new Bundle();
        args.putString(KEY_MOVIE_TITLE, movieTitle);
        fragmentComedy.setArguments(args);

        return fragmentComedy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_android, container, false);

        init(view);

//        Picasso.with(con).load(R.drawable.s_fruit).into(img1);
//        Picasso.with(con).load(R.drawable.s_veg).into(img2);
//        Picasso.with(con).load(R.drawable.s_milk).into(img3);
//        Picasso.with(con).load(R.drawable.s_veg).into(img4);
//        Picasso.with(con).load(R.drawable.s_fruit).into(img5);
//
//
//        fruit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        vegetable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        exotic_fruit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        exotic_vegetable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        milk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void init(View v)
    {
        con     = getActivity();
//        img1    = (ImageView) v.findViewById(R.id.image1);
//        img2    = (ImageView) v.findViewById(R.id.image2);
//        img3    = (ImageView) v.findViewById(R.id.image3);
//        img4    = (ImageView) v.findViewById(R.id.image4);
//        img5    = (ImageView) v.findViewById(R.id.image5);
//
//        fruit   = (LinearLayout) v.findViewById(R.id.fruits);
//        vegetable = (LinearLayout) v.findViewById(R.id.vegetables);
//        exotic_fruit= (LinearLayout) v.findViewById(R.id.exotic_fruits);
//        exotic_vegetable = (LinearLayout) v.findViewById(R.id.exotic_vegetables);
//        milk    = (LinearLayout) v.findViewById(R.id.milk);
        //textView=v.findViewById(R.id.frag_text);
    }
}
