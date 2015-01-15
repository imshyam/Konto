package me.imshyam.konto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by shyamsundar on 10/17/2014.
 */
public class NotificationTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        //set title
//        ((MyActivity) getActivity())
//                .setActionBarTitle("Notifications");

        View rootView = inflater.inflate(R.layout.notific, container, false);
        return rootView;
    }
}
