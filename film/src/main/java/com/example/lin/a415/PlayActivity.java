package com.example.lin.a415;

/**
 * Created by lin on 2018/4/15.
 */

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.example.lin.a415.listener.OnTransitionListener;
import com.example.lin.a415.video.SampleVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 单独的视频播放页面
 * Created by shuyu on 2016/11/11.
 */
public class PlayActivity extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;
    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";

    @BindView(R.id.video_player)
    SampleVideo videoPlayer;
    MagicFileChooser magicFileChooser;
   Uri uri;
    String path;
   String url;
    OrientationUtils orientationUtils;

    private boolean isTransition;
    private Transition transition;
    private void showAlert(String title,String context)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(context);
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        isTransition = getIntent().getBooleanExtra(TRANSITION, false);
/*
        // 建立 "選擇檔案 Action" 的 Intent
        Intent intent = new Intent( Intent.ACTION_GET_CONTENT );

        // 過濾檔案格式
        intent.setType( "video/*" );

        // 建立 "檔案選擇器" 的 Intent  (第二個參數: 選擇器的標題)
        Intent destIntent = Intent.createChooser( intent, "選擇檔案" );

        // 切換到檔案選擇器 (它的處理結果, 會觸發 onActivityResult 事件)
        startActivityForResult( destIntent, 0 );
*/

        Bundle bundle =getIntent().getExtras();
         path = bundle.getString("path");
        showAlert("path",path);
            init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
/*
        // 有選擇檔案
        if ( resultCode == RESULT_OK )
        {
            // 取得檔案的 Uri
             uri = data.getData();
             url = magicFileChooser.getAbsolutePathFromUri(this,uri,false);
             showAlert("path",url);
            init();
            // url = data.getDataString();
            if( uri != null )
            {
                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();

              /* for (int i = 0; i < cursor.getColumnCount(); i++) {


                    setTitle( i+"-"+cursor.getString(1));
                }
                */
                // 利用 Uri 顯示 ImageView 圖片
                // setTitle( uri.toString() );
/*
            }
            else
            {
                setTitle("無效的檔案路徑 !!");
            }
        }
        else
        {
            setTitle("取消選擇檔案 !!");
        }


*/
    }


    private void init() {
      String p = "file://"+path;

        //String url = "https://videos.files.wordpress.com/F173jZYT/e7acac08e8af9d-e78886e8b1aae79a84e8b5b7e8b791e7babf1_hd.mp4";
        //需要路径的
        videoPlayer.setUp(p,false,"");
       // videoPlayer.setUp(p, false, null, "");
       // new File(FileUtils.getPath())

        //借用了jjdxm_ijkplayer的URL
    /*   String source1 = "/storage/emulated/0/Movies/840.mp4";
        String name = "普通";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, source1);

        String source2 = "/storage/emulated/0/Movies/840.mp4";
        String name2 = "清晰";
        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, source2);

        List<SwitchVideoModel> list = new ArrayList<>();
        list.add(switchVideoModel);
        list.add(switchVideoModel2);

        videoPlayer.setUp(list, true, "测试视频");
*/
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        videoPlayer.setThumbImageView(imageView);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //videoPlayer.setShowPauseCover(false);

        //videoPlayer.setSpeed(2f);

        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });

        //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
        //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);

        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);

        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //过渡动画
        initTransition();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }, 500);
        }
    }


    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(videoPlayer, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            videoPlayer.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener(){
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    videoPlayer.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }

}
