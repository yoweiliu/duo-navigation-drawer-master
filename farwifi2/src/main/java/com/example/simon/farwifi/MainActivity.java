package com.example.simon.farwifi;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button button1,button2,out;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Bundle bundle = new Bundle();
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.ON);
        button2 = findViewById(R.id.OFF);
        out = findViewById(R.id.OUT);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // webview.loadUrl("https://api.thingspeak.com/update?api_key=V4STJDWB4PTF36DK&field1=１");
                bundle.putInt("a",1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , Main2Activity.class);
                intent.putExtras(bundle);//把數字put進去
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // webview.loadUrl("https://api.thingspeak.com/update?api_key=V4STJDWB4PTF36DK&field1=0");
                bundle.putInt("a",0);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , Main2Activity.class);
                intent.putExtras(bundle);//把數字put進去
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.this.finish();
            }
        });
    }

}