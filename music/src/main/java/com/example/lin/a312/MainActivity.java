package com.example.lin.a312;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.example.lin.a312.adapter.Myadapter;
import com.example.lin.a312.bean.Song;
import com.example.lin.a312.utils.MusicUtils;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity
    implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener,EasyPermissions.PermissionCallbacks
{
    private MediaPlayer mMediaPlayer = null;
    private Boolean mbIsInitialised = true;
    TextView text1,name;
   Button mplay,mpause,mstop,mnext,mscan;
    private SeekBar mseekBar;
    private Timer timer;
    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突。
    private int currentPosition=0;//当前音乐播放的进度
    SimpleDateFormat format;
    int mm=1;
    private ListView mListView;
    private List<Song> list;
    private Myadapter adapter;
    public String ppath;
    private boolean IsPlay = false;
    private int playPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMediaPlayer = new MediaPlayer();
        format = new SimpleDateFormat("mm:ss");
        mplay = (Button) findViewById(R.id.play);
        mpause = (Button) findViewById(R.id.pause);
        mstop = (Button) findViewById(R.id.stop);
        mnext = (Button) findViewById(R.id.next);
        text1 = (TextView) findViewById(R.id.text1);
        mscan = (Button) findViewById(R.id.scan);
        name = (TextView)findViewById(R.id.name);
        mListView=(ListView)findViewById(R.id.main_listview);

        mscan.setOnClickListener(scanOnClick);
        mplay.setOnClickListener(playOnClick);
        mpause.setOnClickListener(pauseOnClick);
        mstop.setOnClickListener(stopOnClick);
        mnext.setOnClickListener(nextOnClick);
        mseekBar = findViewById(R.id.seekBar);

        mseekBar.setOnSeekBarChangeListener(new MySeekBar());
        if (Build.VERSION.SDK_INT > 23)
        {
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS, Manifest.permission.CAMERA};
            if (!EasyPermissions.hasPermissions(MainActivity.this, perms)) {
                EasyPermissions.requestPermissions(MainActivity.this, "需要權限",
                        100, perms);

            }
        }

    }


    private void initView() {
       mListView = (ListView) findViewById(R.id.main_listview);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        list = MusicUtils.getMusicData(this);
        adapter = new Myadapter(this,list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //创建一个播放音频的方法，把点击到的地址传过去
                //list.get(i).path这个就是歌曲的地址
                play(list.get(i).path);
                name.setText(list.get(i).song);
               // ppath=list.get(i).path;
                playPosition = i;
                //播放音乐的时候把是否在播放赋值为true

            }
        });

    }
    private void play(String path) {
        //播放之前要先把音频文件重置
        try {

            mMediaPlayer.reset();
            text1.setText("00:00");
            mbIsInitialised=false;
            currentPosition = 0;
          //  mseekBar.setProgress(0);

            //mseekBar.setMax(mMediaPlayer.getDuration());
            //调用方法传进去要播放的音频路径
            mMediaPlayer.setDataSource(path);
            //异步准备音频资源
            //mMediaPlayer.prepare();
            mMediaPlayer.prepareAsync();
            //调用mediaPlayer的监听方法，音频准备完毕会响应此方法
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mm=1;
                    currentPosition = mMediaPlayer.getCurrentPosition();

                    mMediaPlayer.start();
                    //开始音频
                    IsPlay = true;
                    retime();


                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onResume()
    {
        super.onResume();

     /*   mMediaPlayer = new MediaPlayer();

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);

        try {
            mMediaPlayer.setDataSource(this, uri);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "指定的音樂檔錯誤！", Toast.LENGTH_LONG)
                    .show();
        }
        */
        text1.setText("00:00");

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }
    protected  void onStop()
    {
        super.onStop();


        mMediaPlayer.release();
        mMediaPlayer = null;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.release();
        mediaPlayer = null;

        Toast.makeText(MainActivity.this, "發生錯誤，停止播放", Toast.LENGTH_LONG)
                .show();

        return true;
    }
    public void retime()
    {
        mMediaPlayer.seekTo(0);
        mMediaPlayer.seekTo(currentPosition);
        mseekBar.setMax(mMediaPlayer.getDuration());
        //监听播放时回调函数
        timer = new Timer();
        timer.schedule(new TimerTask() {

            Runnable updateUI = new Runnable() {
                @Override
                public void run() {
                    if(mMediaPlayer.isPlaying()) {
                        text1.setText(format.format(mMediaPlayer.getCurrentPosition()) + "");
                    }
                }
            };
            @Override
            public void run() {
                if(!isSeekBarChanging)
                {
                    if(mMediaPlayer.isPlaying())
                    {
                        mseekBar.setProgress(mMediaPlayer.getCurrentPosition());
                        runOnUiThread(updateUI);
                    }
                }
            }
        },0,50);
        Toast.makeText(MainActivity.this, "開始播放", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
       /* mMediaPlayer.seekTo(0);
        mMediaPlayer.start();
        mMediaPlayer.seekTo(currentPosition);
        mseekBar.setMax(mMediaPlayer.getDuration());
        //监听播放时回调函数
        timer = new Timer();
        timer.schedule(new TimerTask() {

            Runnable updateUI = new Runnable() {
                @Override
                public void run() {
                   text1.setText(format.format(mMediaPlayer.getCurrentPosition())+"");

                }
            };
            @Override
            public void run() {
                if(!isSeekBarChanging){
                    mseekBar.setProgress(mMediaPlayer.getCurrentPosition());
                    runOnUiThread(updateUI);
                }
            }
        },0,50);
        Toast.makeText(MainActivity.this, "開始播放", Toast.LENGTH_LONG).show();
        */
    }
    private View.OnClickListener scanOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            initView();
        }
    };
    private View.OnClickListener nextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (playPosition >= list.size() - 1) {
                playPosition = 0;
            } else {
                //点击下一曲让歌曲的序号加一
                playPosition++;
            }
            //播放
           // mMediaPlayer.release();
            play(list.get(playPosition).path);
        }
    };
    private View.OnClickListener pauseOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            if(mm==1)
            mMediaPlayer.pause();
            IsPlay = false;


        }
    };
    private View.OnClickListener stopOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {

            mMediaPlayer.stop();
            text1.setText("00:00");
            mm=0;
            mseekBar.setProgress(0);
            IsPlay = false;
            mbIsInitialised=true;
            currentPosition = 0;
        }
    };


        private View.OnClickListener playOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    currentPosition = mMediaPlayer.getCurrentPosition();
                    if (mbIsInitialised)
                    {
                        try {
                            mMediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mbIsInitialised = false;
                        mMediaPlayer.start();
                    }
                    else
                    {

                        mMediaPlayer.start();
                    }
                    IsPlay=true;
                mm=1;

            }
        };


    protected void onDestroy() {
        super.onDestroy();
        isSeekBarChanging = true;
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar mseekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar mseekBar) {
            isSeekBarChanging = true;
        }
        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar mseekBar) {
            isSeekBarChanging = false;
            mMediaPlayer.seekTo(mseekBar.getProgress());
        }
    }
}
