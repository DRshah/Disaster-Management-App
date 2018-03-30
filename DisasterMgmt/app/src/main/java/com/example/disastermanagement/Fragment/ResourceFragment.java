package com.example.disastermanagement.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.disastermanagement.R;

public class ResourceFragment extends android.support.v4.app.Fragment {

    EditText name, loc, contact, amt, item, desc;
    Button subm;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resource, container, false);
        name = view.findViewById(R.id.Name);
        loc = view.findViewById(R.id.Location);
        contact = view.findViewById(R.id.PhoneNumber);
        amt = view.findViewById(R.id.Quantity);
        item = view.findViewById(R.id.ItemName);
        desc = view.findViewById(R.id.Description);
        subm = view.findViewById(R.id.submit);



        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int valid = 1;
                if(name.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper NAME", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(loc.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper LOCATION", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(contact.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper CONTACT NUMBER", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(amt.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper AMOUNT", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(item.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper ITEM NAME", Toast.LENGTH_LONG).show();
                    valid=0;
                }
                else if(desc.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(), "Enter proper DESCRIPTION", Toast.LENGTH_LONG).show();
                    valid=0;
                }

                String nm = remSpace(name.getText().toString().trim());

                String addr = remSpace(loc.getText().toString().trim());

                String ph = remSpace(contact.getText().toString().trim());

                String amount = remSpace(amt.getText().toString().trim());

                String itm = remSpace(item.getText().toString().trim());

                String descr = remSpace(desc.getText().toString().trim());



                String message = nm+","+addr+","+ph+","+amount+","+itm+","+descr;

                /*if(valid==1) {
                    new ResourceSubmit(getApplicationContext()).execute(message);

                }*/

            }
        });

        return view;
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
