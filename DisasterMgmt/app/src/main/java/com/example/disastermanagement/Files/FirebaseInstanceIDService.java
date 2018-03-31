package com.example.disastermanagement.Files;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NAHUSH RAICHURA on 3/24/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh()
    {
        String token=FirebaseInstanceId.getInstance().getToken();
        System.out.println(token);
        registerToken(token);
    }
    private void registerToken(String token)
    {
        //send token to database

        Register_Token register_token = new Register_Token(token);

        FirebaseDatabase.getInstance().getReference("Token").child(token).setValue(register_token);
        System.out.println("register_token method");

    }
}