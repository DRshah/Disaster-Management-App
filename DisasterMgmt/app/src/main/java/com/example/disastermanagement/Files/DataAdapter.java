package com.example.disastermanagement.Files;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.disastermanagement.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by Darsh_Shah on 30-03-2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    //private ArrayList<AndroidVersion> android_versions;
    private ArrayList<Feed> feedArrayList;
    private Context context;

    public DataAdapter(Context context,ArrayList<Feed> feedArrayList1) {
        this.context = context;
        this.feedArrayList = feedArrayList1;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_category.setText(feedArrayList.get(i).getCategory());
        viewHolder.tv_datetime.setText(feedArrayList.get(i).getDateime());
        viewHolder.tv_area.setText(feedArrayList.get(i).getArea());
        viewHolder.tv_description.setText(feedArrayList.get(i).getDescription());
        Picasso.with(context).load(feedArrayList.get(i).getImage()).placeholder(R.drawable.abc).error(R.drawable.abc).resize(160,60).into(viewHolder.img_view);//.resize(120, 60).into(viewHolder.img_view);
    }

    @Override
    public int getItemCount() {
        return feedArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_category,tv_description,tv_area,tv_datetime;
        ImageView img_view;
        public ViewHolder(View view) {
            super(view);
            tv_category=view.findViewById(R.id.tv_category);
            tv_area=view.findViewById(R.id.tv_area);

            tv_description=view.findViewById(R.id.tv_description);
            tv_datetime = (TextView)view.findViewById(R.id.tv_datetime);
            img_view = (ImageView)view.findViewById(R.id.img);
        }
    }
}
