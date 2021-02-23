package com.example.seminar_4.Wishlist;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.seminar_4.model.Image;
import com.example.seminar_4.model.Wish;
import com.example.seminar_4.Cash.DownloadContent;
import com.example.seminar_4.R;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyAdapter extends BaseAdapter {

    Map<Long, Wish> wishList;
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Image> images;
   public boolean checked;

    public MyAdapter(Context context, Map<Long, Wish> wishList, ArrayList<Image> images) {
        this.wishList = wishList;
        this.context = context;
        this.images=images;
        this.layoutInflater = LayoutInflater.from(context);

        createCheckedHolder();
    }

    @Override
    public int getCount() {
        return wishList.size();
    }

    @Override
    public Object getItem(int position) {
        Object[] objects = wishList.keySet().toArray();
        return wishList.get(objects[position]);
    }

    @Override
    public long getItemId(int position) {
        Object[] objects = wishList.keySet().toArray();
        return wishList.get(objects[position]).getId();
    }

    private static class WishViewHolder
    {
        public TextView name;
        public TextView atributes;
        public CheckBox filtered;
        public ProgressBar progressBar;
        public ImageView img;
        private static final String TAG = MyAdapter.class.getSimpleName();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final WishViewHolder holder;
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.my_layout, parent, false);
            holder = new WishViewHolder();
            holder.progressBar=convertView.findViewById(R.id.progressBar);
            holder.atributes = convertView.findViewById(R.id.tvAtributes);
            holder.name = convertView.findViewById(R.id.tvName);

            holder.img = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else
            holder = (WishViewHolder) convertView.getTag();

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofInt(holder.progressBar, "progress", 0,10);
        progressAnimator.setDuration(2000);
        progressAnimator.start();

        Wish wish = wishList.get(getItemId(position));
        holder.name.setText(wish.getName());
        holder.atributes.setText(wish.getCategory());
        for (Image image : images) {
            if(wish.getCategory()==image.getCategory()){
                holder.img.setImageBitmap(image.getImage());

            }
        }
        holder.progressBar.setProgress(10);
        return convertView;
    }

    public boolean[] checkedHolder;

    private void createCheckedHolder() {
        checkedHolder = new boolean[getCount()];
    }

    }


