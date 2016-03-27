package com.prjproject.tcc.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.model.Activity;

import java.util.List;

/**
 * Created by Emanuel on 2016-03-13.
 */
public class ImageAdapter extends RecyclerView.Adapter{
    private List<Object> items;

    public ImageAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_view_holder, parent, false);
        viewHolder = new ViewHolderImage(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderImage vh = (ViewHolderImage) holder;
        Activity act = (Activity) items.get(position);
        vh.getImageView().setImageBitmap(act.getActivityImage());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();//this.items.size();
    }

    public void addItem(Object item) {
        items.add(item);
    }

    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    private class ViewHolderImage extends RecyclerView.ViewHolder {
        private ImageView image;

        public ViewHolderImage(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_item);
        }

        public ImageView getImageView() {
            return image;
        }

        public void setImageView(ImageView image) {
            this.image = image;
        }
    }
}
