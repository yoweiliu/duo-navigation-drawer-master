package nl.psdcompany.duonavigationdrawer.example;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Media extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */

     Button button1, button2, button3, button4, button5, button6;



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main1);




//        button1 = (Button) findViewById(R.id.button1);
//
//        button1.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//
//            public void onClick(View v) {
//        Intent activityIntent1 = new Intent();
//        activityIntent1.setAction("com.music");//自訂的 action name
//        startActivity(activityIntent1);
//
//            }
//        });
//
//        button2 = (Button)findViewById(R.id.button2);
//
//        button2.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//
//            public void onClick(View v) {
//                Intent activityIntent2 = new Intent();
//                activityIntent2.setAction("com.film");//自訂的 action name
//                startActivity(activityIntent2);
//            }
//        });
//
//        button3 = (Button)findViewById(R.id.button3);
//
//        button3.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//
//            public void onClick(View v) {
//                Intent activityIntent3 = new Intent();
//                activityIntent3.setAction("com.youtube");//自訂的 action name
//                startActivity(activityIntent3);
//            }
//        });
//
//        button4 = (Button)findViewById(R.id.button4);
//
//        button4.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//
//            public void onClick(View v) {
//                Intent activityIntent4 = new Intent();
//                activityIntent4.setAction("com.CTITV");//自訂的 action name
//                startActivity(activityIntent4);
//            }
//        });
//
//        button5 = (Button)findViewById(R.id.button5);
//
//        button5.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//
//            public void onClick(View v) {
//                Intent activityIntent5 = new Intent();
//                activityIntent5.setAction("com.DongSen");//自訂的 action name
//                startActivity(activityIntent5);
//            }
//        });
//
//        button6 = (Button)findViewById(R.id.button6);
//
//        button6.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//
//            public void onClick(View v) {
//                Intent activityIntent6 = new Intent();
//                activityIntent6.setAction("com.TTV");//自訂的 action name
//                startActivity(activityIntent6);
//            }
//        });
    }


}

