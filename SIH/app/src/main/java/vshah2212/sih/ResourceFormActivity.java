package vshah2212.sih;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResourceFormActivity extends AppCompatActivity {

    EditText name, loc, contact, amt, item, desc;
    Button subm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_form);

        name = findViewById(R.id.Name);
        loc = findViewById(R.id.Location);
        contact = findViewById(R.id.PhoneNumber);
        amt = findViewById(R.id.Quantity);
        item = findViewById(R.id.ItemName);
        desc = findViewById(R.id.Description);
        subm = findViewById(R.id.submit);



        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        int valid = 1;
        if(name.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Enter proper NAME", Toast.LENGTH_LONG).show();
            valid=0;
        }
        else if(loc.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Enter proper LOCATION", Toast.LENGTH_LONG).show();
            valid=0;
        }
        else if(contact.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Enter proper CONTACT NUMBER", Toast.LENGTH_LONG).show();
            valid=0;
        }
        else if(amt.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Enter proper AMOUNT", Toast.LENGTH_LONG).show();
            valid=0;
        }
        else if(item.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Enter proper ITEM NAME", Toast.LENGTH_LONG).show();
            valid=0;
        }
        else if(desc.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Enter proper DESCRIPTION", Toast.LENGTH_LONG).show();
            valid=0;
        }

        String nm = remSpace(name.getText().toString().trim());

        String addr = remSpace(loc.getText().toString().trim());

        String ph = remSpace(contact.getText().toString().trim());

        String amount = remSpace(amt.getText().toString().trim());

        String itm = remSpace(item.getText().toString().trim());

        String descr = remSpace(desc.getText().toString().trim());



        String message = nm+","+addr+","+ph+","+amount+","+itm+","+descr;

        if(valid==1) {
            new ResourceSubmit(getApplicationContext()).execute(message);

        }

            }
        });


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
