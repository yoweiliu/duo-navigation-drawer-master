package nl.psdcompany.duonavigationdrawer.example;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class BlankFragment_main1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);

        view.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.music");//自訂的 action name
                startActivity(activityIntent);
            }
        });
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.film");//自訂的 action name
                startActivity(activityIntent);
            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.youtube");//自訂的 action name
                startActivity(activityIntent);
            }
        });
        view.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.CTITV");//自訂的 action name
                startActivity(activityIntent);
            }
        });
        view.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.DongSen");//自訂的 action name
                startActivity(activityIntent);
            }
        });

        view.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.TTV");//自訂的 action name
                startActivity(activityIntent);
            }
        });


        return view;
    }

}
