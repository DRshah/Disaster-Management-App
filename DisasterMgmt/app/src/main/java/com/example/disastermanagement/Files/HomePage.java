package com.example.disastermanagement.Files;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disastermanagement.Fragment.Entry;
import com.example.disastermanagement.Fragment.FeedFragment;
import com.example.disastermanagement.Fragment.ResourceFragment;
import com.example.disastermanagement.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.disastermanagement.adapter.CustomExpandableListAdapter;
import com.example.disastermanagement.datasource.ExpandableListDataSource;
import com.example.disastermanagement.navigation.FragmentNavigationManager;
import com.example.disastermanagement.navigation.NavigationManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomePage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    //private String[] items;



    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;
    private NavigationManager mNavigationManager;
    private Map<String, List<String>> mExpandableListData;
    private TextView nav_text_view;

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthstateListener;
    private ImageView user_img;
    SharedPreferences preferences;
    String personID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth=FirebaseAuth.getInstance();
        preferences=getSharedPreferences("GoogleInfo",MODE_PRIVATE);
        if(isNetworkAvailable()){


            personID= mAuth.getCurrentUser().getUid();
            preferences.edit().putString("personID",personID).apply();
            //preferences.edit().putString("photo",personPhoto+"").apply();
        }


        //FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        mNavigationManager = FragmentNavigationManager.obtain(this);

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        user_img=listHeaderView.findViewById(R.id.avatar);
        nav_text_view=(TextView)listHeaderView.findViewById(R.id.name);
        preferences=getSharedPreferences("GoogleInfo",MODE_PRIVATE);
        String name=preferences.getString("name","");
        String photo=preferences.getString("photo","");
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
        nav_text_view.setText(name);
        Uri pp=Uri.parse(photo);
        Picasso.with(this).load(pp).into(user_img);
        //nav_user.setText(user.getEmail());


        mExpandableListView.addHeaderView(listHeaderView);
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        addDrawerItems();
        setupDrawer();
        if (savedInstanceState == null) {
            selectFirstItemAsDefault();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAuthstateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Toast.makeText(getApplicationContext(),"You have been successfully logged out.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomePage.this,LoginActivity.class));
                }
            }
        };

        //getSupportActionBar().setTitle("Disaster Application");
    }

    private void selectFirstItemAsDefault() {
        if (mNavigationManager != null) {
            String firstActionMovie = getResources().getStringArray(R.array.Vegetables)[0];
            mNavigationManager.showFragmentAction(firstActionMovie);
            getSupportActionBar().setTitle("Disaster Mgmt");
        }
    }

    private void initItems() {
    }

    private void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());//set title for toolbar when open
            }
        });
        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //getSupportActionBar().setTitle("hello world");
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();

                //getSupportActionBar().setTitle(selectedItem);

                //change activity when clicked on item
                if(selectedItem.equals("Home"))
                {
                    MapActivity fragment = new MapActivity();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.commit();
//                    Intent i = new Intent(HomePage.this, MapActivity.class);
//                    //i.putExtra("page",""+selectedItem);
//                    startActivity(i);
                }else
                if(selectedItem.equals("Disaster Reporting"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FeedFragment()).commit();
                    Toast.makeText(HomePage.this, "You have selected "+ selectedItem , Toast.LENGTH_SHORT).show();
                }
                else
                if(selectedItem.equals("Disaster Reporting"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new Entry()).commit();
                    Toast.makeText(HomePage.this, "You have selected "+ selectedItem , Toast.LENGTH_SHORT).show();
                }
                else
>>>>>>> 39336eca25a7ce191e4e3e3979a09337f40f4865
                if(selectedItem.equals("Nearest"))
                {
                    Toast.makeText(HomePage.this, "You have selected "+ selectedItem , Toast.LENGTH_SHORT).show();
                }
                else
                if(selectedItem.equals("Emergency Numbers"))
                {
                    Toast.makeText(HomePage.this, "You have selected "+ selectedItem , Toast.LENGTH_SHORT).show();
                }

                else
                if(selectedItem.equals("Logout")){//logout
                    mAuth.signOut();
                    finish();
//                    Intent i = new Intent(HomePage.this, ListActivity.class);
//                    i.putExtra("category",""+getCategory(selectedItem));
//                    i.putExtra("catgroupid",""+getCatCroupID(selectedItem));
//                    i.putExtra("page",""+selectedItem);
//                    startActivity(i);
                }
                else
                if(selectedItem.equals("Resource"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new ResourceFragment()).commit();
                    Toast.makeText(HomePage.this, "You have selected "+ selectedItem , Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(HomePage.this, ""+selectedItem, Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Disaster Management");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*if (id == R.id.aboutus) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/AboutUs.aspx");
            i.putExtra("page","About Us");
            startActivity(i);
            return true;
        }
        if (id == R.id.faq) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/FAQ.aspx");
            i.putExtra("page","FAQ");
            startActivity(i);
            return true;
        }
        if (id == R.id.qualitystandard) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/QualityStandard.aspx");
            i.putExtra("page","Quality Standard");
            startActivity(i);
            return true;
        }
        if (id == R.id.paymentpolicy) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/PaymentPolicy.aspx");
            i.putExtra("page","Payment Policy");
            startActivity(i);
            return true;
        }
        if (id == R.id.privacypolicy) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/PrivacyPolicy.aspx");
            i.putExtra("page","Privacy Policy");
            startActivity(i);
            return true;
        }
        if (id == R.id.termsncondition) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/TermsAndConditions.aspx");
            i.putExtra("page","Terms and Conditions");
            startActivity(i);
            return true;
        }
        if (id == R.id.contact) {
            Intent i = new Intent(HomePage.this,WebActivity.class);
            i.putExtra("url","http://sabzeewala.com/ContactUs.aspx");
            i.putExtra("page","Contact Us");
            startActivity(i);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
