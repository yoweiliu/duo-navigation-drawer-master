package com.example.lin.a415;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.lin.a415.adapter.Myadapter;
import com.example.lin.a415.bean.Song;
import com.example.lin.a415.utils.MusicUtils;
import com.example.lin.a415.view.LoadingDialog;
import com.shuyu.gsyvideoplayer.utils.Debuger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;



public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<Song> list;
    private Myadapter adapter;
    Button mscan;
    String play;
    int i=0,count=0;
    private LoadingDialog mLoadingDialog;
    Handler handler = new Handler();
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mscan = (Button) findViewById(R.id.scan);
        mscan.setOnClickListener(scanOnClick);

        Debuger.enable();
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT > 23)
        {
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS, Manifest.permission.CAMERA};
            if (!EasyPermissions.hasPermissions(MainActivity.this, perms)) {
                EasyPermissions.requestPermissions(MainActivity.this, "需要權限",
                        100, perms);

            }
        }

    }
    private View.OnClickListener scanOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showLoading();
            handler.postDelayed(new Runnable(){

                @Override
                public void run() {

                    //過兩秒後要做的事情

                    initView();
                }}, 500);



        }
    };
    private void showAlert(String title,String context)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(context);
        alert.show();
    }
    private void initView() {

        mListView = (ListView) findViewById(R.id.main_listview);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        list = MusicUtils.getMusicData(this);


        adapter = new Myadapter(this,list);

        mListView.setAdapter(adapter);

        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情
                hideLoading();

            }}, 1000);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                play=list.get(i).path;
               // showAlert("path",play);
                //创建一个播放音频的方法，把点击到的地址传过去
                //list.get(i).path这个就是歌曲的地址
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,PlayActivity.class);

                Bundle bundle =new Bundle();
                bundle.putString("path",play);

                intent.putExtras(bundle);
                startActivity(intent);
                // ppath=list.get(i).path;
                //JumpUtils.goToVideoPlayer(MainActivity.this, view);
                //播放音乐的时候把是否在播放赋值为true

            }
        });

    }
    public String send()
    {
        return  play;
    }
    private void showLoading() {
        hideLoading();
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
    }

    private void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


}

