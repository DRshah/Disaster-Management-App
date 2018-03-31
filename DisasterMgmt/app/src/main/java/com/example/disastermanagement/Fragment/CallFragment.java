package com.example.disastermanagement.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.disastermanagement.Files.Contacts;
import com.example.disastermanagement.R;

import java.util.ArrayList;
import java.util.List;


public class CallFragment extends Fragment {
    List<Contacts> conList;
    ListView listView;
    List<Integer> drawID=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_call,container,false);


        listView = (ListView)view.findViewById(R.id.listViewCon);
        conList = new ArrayList<>();
        System.out.println("Call Fragment");
        conList.add(new Contacts("Ambulance", "8369818613"));
        conList.add(new Contacts("Police", "8369818613"));
        conList.add(new Contacts("Hospital", "8369818613"));

        drawID.add(R.drawable.ic_airport_shuttle_black_24dp);
        drawID.add(R.drawable.ic_local_car_wash_black_24dp);
        drawID.add(R.drawable.ic_local_hospital_black_24dp);

        ContactAdapter adapter = new ContactAdapter(conList,drawID);
        listView.setAdapter(adapter);

        return view;

    }

    class ContactAdapter extends ArrayAdapter<Contacts> {
        List<Contacts> contactslist;
        List<Integer> drawID;
        public ContactAdapter(List<Contacts> contactlist,List<Integer> drawID) {
            super(getActivity(), R.layout.layout_emergency_single, contactlist);
            this.contactslist = contactlist;
            this.drawID= drawID;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_emergency_single, null, true);
            TextView serviceNAME = listViewItem.findViewById(R.id.serviceNAME);
            Button callNumber = listViewItem.findViewById(R.id.callNumber);
            ImageView imageView= listViewItem.findViewById(R.id.icon);

            Drawable drawable = getResources().getDrawable(drawID.get(position));

            imageView.setImageDrawable(drawable);

            final Contacts econ = contactslist.get(position);
            serviceNAME.setText(econ.getServicename());
            final String number = econ.getContactinformation();
            callNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                    startActivity(intent);
                }
            });
            return listViewItem;
        }
    }
}
