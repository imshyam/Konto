package me.imshyam.konto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shyam on 1/8/2015.
 */
public class History extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        //set title
//        ((MyActivity) getActivity())
//                .setActionBarTitle("Make Request");
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.history, container, false);
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_norefresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences preferences = getActivity().getSharedPreferences("MyShPre", 0);
                preferences.edit().clear().commit();
                Intent i =new Intent(getActivity().getApplicationContext(),sign_in.class);
                startActivity(i);
                return true;
            case R.id.action_locally:
                Intent i1 =new Intent(getActivity().getApplicationContext(),Locally.class);
                startActivity(i1);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
