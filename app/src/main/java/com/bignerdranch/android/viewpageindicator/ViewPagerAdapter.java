package com.bignerdranch.android.viewpageindicator;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/8/25/025.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private static final int[] IMG_RES = {R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4};

    @Override
    public int getCount() {
        return IMG_RES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_page, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.setImageResource(IMG_RES[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
