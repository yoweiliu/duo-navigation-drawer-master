package nl.psdcompany.duonavigationdrawer.example;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setPageMargin(20);
        pager.setAdapter(new PagerAdapter());
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setScaleY(1f - ((float) (0.3 * Math.abs(position))));
                page.setScaleX(1f - ((float) (0.3 * Math.abs(position))));
            }
        });
        pager.setCurrentItem(2000);
        return view;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  /*  public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float min_scale = 0.85f;

        @Override
        public void transformPage(View page, float position) {
            float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
            if (position < -1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position < 0) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position >= 0 && position < 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position >= 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }
        }
    }*/
    private class PagerAdapter extends android.support.v4.view.PagerAdapter {


        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Create some layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            // Create some text

            RelativeLayout layout = new RelativeLayout(container.getContext());
            layout.setBackgroundColor(ContextCompat.getColor(container.getContext(), R.color.colorPrimary));
            layout.setLayoutParams(layoutParams);
            switch (position) {
                case 0:
                    ImageView imageView0 = getImageView0(container.getContext());
                    imageView0.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    layout.addView(imageView0);
                    break;

                case 1:
                    ImageView imageView1 = getImageView1(container.getContext());
                    imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    layout.addView(imageView1);
                    break;
                case 2:
                    ImageView imageView2 = getImageView2(container.getContext());
                    imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    layout.addView(imageView2);
                    break;
                case 3:
                    ImageView imageView3 = getImageView3(container.getContext());
                    imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    layout.addView(imageView3);
                    break;

            }
            container.addView(layout);
            return layout;
        }

        private  ImageView getImageView0(Context context){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.itelogo);
            return imageView;
        }
        private  ImageView getImageView1(Context context){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.ite1);
            return imageView;
        }
        private  ImageView getImageView2(Context context){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.cat123);
            return imageView;
        }
        private  ImageView getImageView3(Context context){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.cat123);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
