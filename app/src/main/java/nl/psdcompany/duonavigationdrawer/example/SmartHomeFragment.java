package nl.psdcompany.duonavigationdrawer.example;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SmartHomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.smarthomefrag, container, false);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.farwifi2");//自訂的 action name
                startActivity(activityIntent);
            }
        });
        view.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.bluetooth");//自訂的 action name
                startActivity(activityIntent);
            }
        });



        return view;
    }

}
