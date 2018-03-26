package com.example.disastermanagement.navigation;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.disastermanagement.BuildConfig;
import com.example.disastermanagement.Files.HomePage;
import com.example.disastermanagement.Fragment.FragmentAndroid;
import com.example.disastermanagement.R;

/**
 * Created by Darsh_Shah on 24-03-2018.
 */

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private HomePage mActivity;

    public static FragmentNavigationManager obtain(HomePage activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(HomePage activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragmentAction(String title) {
        showFragment(FragmentAndroid.newInstance(title), false);
    }

    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, fragment);
        ft.addToBackStack(null);
        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
        fm.executePendingTransactions();
    }
}
