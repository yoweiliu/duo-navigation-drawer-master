package com.example.simon.farwifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        int a=bundle.getInt("a");

        final WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setContentView(webview);
        webview.setWebViewClient(new WebViewClient());
        if(a==1) {
            webview.loadUrl("https://api.thingspeak.com/update?api_key=V4STJDWB4PTF36DK&field1=1");
            Intent intent = new Intent();
            intent.setClass(Main2Activity.this , MainActivity.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }
        else {
            webview.loadUrl("https://api.thingspeak.com/update?api_key=V4STJDWB4PTF36DK&field1=0");
            Intent intent = new Intent();
            intent.setClass(Main2Activity.this , MainActivity.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }
    }
}
