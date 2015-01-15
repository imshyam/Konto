package me.imshyam.konto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

        View rootView = inflater.inflate(R.layout.history, container, false);
        return rootView;
    }
}
