package me.imshyam.konto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by shyamsundar on 10/17/2014.
 */
public class Home extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        TableLayout tableLayout=(TableLayout)rootView.findViewById(R.id.table);
        TableRow tableRow=new TableRow(rootView.getContext());
        TextView name=new TextView(rootView.getContext());
        name.setText("Name");
        TextView owe=new TextView(rootView.getContext());
        owe.setText("Balance");
        tableRow.addView(name);
        tableRow.addView(owe);
        tableLayout.addView(tableRow);
        View v = new View(rootView.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,0.7f);
        v.setLayoutParams(params);
        tableLayout.addView(v);

        /* Demonstration of a collection-browsing activity.
        rootView.findViewById(R.id.collection_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        // Demonstration of navigating to external activities.
        rootView.findViewById(R.id.external_activity)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create an intent that asks the user to pick a photo, but using
                        // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
                        // the application from the device home screen does not return
                        // to the external activity.
                        Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                        externalActivityIntent.setType("image/*");
                        externalActivityIntent.addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(externalActivityIntent);
                    }
                });
*/
        return rootView;
    }
}

