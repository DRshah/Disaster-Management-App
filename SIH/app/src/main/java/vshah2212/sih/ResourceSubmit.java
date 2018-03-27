package vshah2212.sih;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

class ResourceSubmit extends AsyncTask<String, Void, String> {
    Context ctx;
    String usn;

    public ResourceSubmit(Context context)
    {
        ctx = context;
    }

    protected String doInBackground(String... message) {
        HttpClient httpclient;
        HttpGet request;
        HttpResponse response = null;
        String result = "";
        try {
            String send = message[0];
            String[] rec = send.split(",");

            String name = rec[0];

            String addr = rec[1];

            String phn = rec[2];

            String amt = rec[3];

            String itm = rec[4];

            String desc = rec[5];

            httpclient = new DefaultHttpClient();
            request = new HttpGet("http://dcshahfamily.esy.es/Register1.php?name="+name+"&loc="+addr+"&phn="+phn+"&amt="+amt+"&item="+itm+"&desc="+desc);
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
