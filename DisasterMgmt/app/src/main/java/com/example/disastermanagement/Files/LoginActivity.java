package com.example.disastermanagement.Files;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disastermanagement.Files.HomePage;
import com.example.disastermanagement.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{
    private Button buttonSignIn;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public final static int requestcall=1,requestcall1=0,requestcall2=2;
    private SignInButton googleButton;
    private final static int RC_SIGN_IN = 7;
    private GoogleApiClient mGoogleApiClient;
    SharedPreferences preferences;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();


        int reqcode=0;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},reqcode);
        }
        int reqcode1=0;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this , new String[]{Manifest.permission.CALL_PHONE},reqcode1);
        }
        int reqcode2=0;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.SEND_SMS},reqcode2);
        }


        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(this,HomePage.class));

        }
        preferences=getSharedPreferences("GoogleInfo",MODE_PRIVATE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleButton = (SignInButton) findViewById(R.id.sign_in_Button);

        //not sure of this.....
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        buttonSignIn=(Button)findViewById(R.id.buttonSignIn);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        textViewSignUp=(TextView)findViewById(R.id.textViewSignUp);
        progressDialog=new ProgressDialog(this);
        imageView=(ImageView) findViewById(R.id.symbol);
        Picasso.with(this).load(R.drawable.respite).into(imageView);
        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                preferences.edit().putString("LoginType","GOOGLE").apply();
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                Uri personPhoto = account.getPhotoUrl();
                String personName = account.getDisplayName();
                preferences.edit().putString("name",personName).apply();
                preferences.edit().putString("photo",personPhoto+"").apply();
                //Toast.makeText(getApplicationContext(), "hello",
                  //      Toast.LENGTH_SHORT).show();

                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                //Toast.makeText(getApplicationContext(),"firebase google login failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            Toast.makeText(getApplicationContext(), "Authentication successful--"+user,
//                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),HomePage.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(getApplicationContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void userlogin()
    {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        preferences.edit().putString("LoginType","email").apply();
        preferences.edit().putString("Username",email).apply();
        if(TextUtils.isEmpty(email)){
            //mail is empty
            Toast.makeText(this,"please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this,"please enter your password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"wrong credentials entered",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onClick(View view)
    {
        if(view==buttonSignIn)
        {
            userlogin();
        }
        if(view==textViewSignUp)
        {
            finish();
            startActivity(new Intent(this,Register.class));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==requestcall)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
            }

        }
//        else if(requestCode==requestcall1){
//            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
//            }
//
//            else
//            {
//                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
//            }
//        }
//        else if(requestCode==requestcall2){
//            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
//            }
//
//            else
//            {
//                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
//            }
//        }
        else {

        }

    }

}

