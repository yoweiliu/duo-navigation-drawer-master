package com.example.lin.a415.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lin.a415.MainActivity;
import com.example.lin.a415.R;
import com.example.lin.a415.bean.Song;
import com.example.lin.a415.utils.MusicUtils;

import java.util.List;

/**
 * Created by lin on 2018/4/28.
 */

public class Myadapter extends BaseAdapter {
    private Context context;
    private List<Song> list;
    int p=0;
    public Myadapter(MainActivity mainActivity, List<Song> list) {
        this.context = mainActivity;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            //引入布局
            view = View.inflate(context, R.layout.v1, null);
            //实例化对象
            holder.song = (TextView) view.findViewById(R.id.item_mymusic_song);
            holder.singer = (TextView) view.findViewById(R.id.item_mymusic_singer);
            holder.duration = (TextView) view.findViewById(R.id.item_mymusic_duration);
            holder.position = (TextView) view.findViewById(R.id.item_mymusic_postion);
            holder.pic=(ImageView) view.findViewById(R.id.imageView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //给控件赋值


        holder.song.setText(list.get(i).song.toString());
      //  holder.singer.setText(list.get(i).singer.toString());
        //时间需要转换一下

        int duration = list.get(i).duration;
        String time = MusicUtils.formatTime(duration);
        holder.duration.setText(time);
        holder.position.setText(i+1+"");
        holder.pic.setImageBitmap(list.get(i).pic);


        return view;
    }
    class ViewHolder{
        TextView song;//歌曲名
        TextView singer;//歌手
        TextView duration;//时长
        TextView position;//序号
        ImageView pic;

    }

}
