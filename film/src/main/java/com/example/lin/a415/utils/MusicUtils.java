package com.example.lin.a415.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.example.lin.a415.bean.Song;
import com.example.lin.a415.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lin on 2018/3/19.
 */

public class MusicUtils {
    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    private LoadingDialog mLoadingDialog;
    public static List<Song> getMusicData(Context context)
    {
        List<Song> list = new ArrayList<Song>();
        // 媒体库查询语句（写一个工具类MusicUtils）

        int x=96*96;
        Cursor cursor = context.getContentResolver().query( MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,null,null);
        if (cursor != null) {
            while (cursor.moveToNext())
            {

                Song song = new Song();
                song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                Bitmap bitmap;
                // 获取视频的缩略图
                bitmap = ThumbnailUtils.createVideoThumbnail(song.path,x);
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, 80, 80,
                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                song.pic=bitmap;
       /*         int length = song.duration;
                length = length/1000;
                int sec = length%60;
                length = length-sec;
                int min = length/60;
                String dispStr = "";
                if (sec < 10)
                song.time = dispStr +"Duration:  "+ min +":0"+ sec + "\n";
                  else
                song.time = dispStr +"Duration:  "+ min +":"+ sec + "\n";

              /*  if (song.size > 1000 * 800)
                {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (song.song.contains("-"))
                    {
                        String[] str = song.song.split("-");
                        song.singer = str[0];
                        song.song = str[1];
                    }

                    list.add(song);
                }

*/
                list.add(song);
            }
            // 释放资源
            cursor.close();
        }

        return list;
    }

    /**
     * 定义一个方法用来格式化获取到的时间
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }


}
