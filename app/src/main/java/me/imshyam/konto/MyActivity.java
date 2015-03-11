package me.imshyam.konto;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shyamsundar on 10/16/2014.
 */

public class MyActivity extends FragmentActivity {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;



    public void onCreate(Bundle savedInstanceState) {
        final ActionBar actionBar = getActionBar();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mAppSectionsPagerAdapter =
                new AppSectionsPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }
    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    public class AppSectionsPagerAdapter extends FragmentStatePagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if(i==0) {
                Fragment fragment = new Home();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(Home.ARG_OBJECT, i + 1);
                fragment.setArguments(args);
                return fragment;
            }
            else if(i==1) {
                Fragment fragment = new NotificationTab();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(Home.ARG_OBJECT, i + 1);
                fragment.setArguments(args);
                return fragment;
            }
            else if(i==2) {
                Fragment fragment = new AddNotification();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(Home.ARG_OBJECT, i + 1);
                fragment.setArguments(args);
                return fragment;
            }
            else if(i==3) {
                Fragment fragment = new History();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(Home.ARG_OBJECT, i + 1);
                fragment.setArguments(args);
                return fragment;
            }
            else if(i==4) {
                Fragment fragment = new Prefereces();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(Home.ARG_OBJECT, i + 1);
                fragment.setArguments(args);
                return fragment;
            }
            else return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "Home";
            }
            else if(position==1){
                return "Notifications";
            }
            else if(position==2){
                return "Make Request";
            }
            else if(position==3){
                return "History";
            }
            else
            return "Preferences";
        }
    }
    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("MyShPre", 0);
                preferences.edit().clear().commit();
                Intent i =new Intent(getApplicationContext(),sign_in.class);
                startActivity(i);
                return true;
            case R.id.action_locally:
                Intent i1 =new Intent(getApplicationContext(),Locally.class);
                startActivity(i1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}