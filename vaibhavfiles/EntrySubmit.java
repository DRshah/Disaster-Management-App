package vshah2212.sih;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

class EntrySubmit extends AsyncTask<String, Void, String> {
    Context ctx;
    String usn;

    public EntrySubmit(Context context)
    {
        ctx = context;
    }

    protected String doInBackground(String... message) {
        HttpClient httpclient;
        HttpGet request;
        HttpResponse response = null;
        String result = "";
        try {


            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());


            String send = message[0];
            String[] rec = send.split(",");

            String cat = rec[0];

            String descr = rec[1];

            String lat = rec[2];

            String longi = rec[3];

            httpclient = new DefaultHttpClient();
            request = new HttpGet("http://dcshahfamily.esy.es/Register2.php?cat="+cat+"&descr="+descr+"&lat="+lat+"&longi="+longi);
            response = httpclient.execute(request);
        } catch (Exception e) {
            result = "error1";
        }

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            Log.e("Error2","err "+e);
            result = "error2";
        }
        return result;
    }

    protected void onPostExecute(String result) {

        Log.e("Result", "Result" + result);
        if (result.trim().equalsIgnoreCase("1"))
        {
            Toast.makeText(ctx,"Successfully Submitted",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(ctx,"Unable to submit please try again",Toast.LENGTH_LONG).show();
        }


    }
}
