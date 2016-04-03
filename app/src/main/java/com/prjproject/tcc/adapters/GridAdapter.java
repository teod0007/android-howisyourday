package com.prjproject.tcc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.model.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Emanuel on 2016-03-26.
 */
public class GridAdapter extends BaseAdapter {
    List<Object> items;
    Context context;

    private static LayoutInflater inflater=null;
    public GridAdapter(Context c, List<Object> items) {
        this.items = items;
        context= c;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public GridAdapter(Context c) {
        this.items = new ArrayList<Object>();
        context= c;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Object> getItems() {
        return items;
    }

    public void addItem(Object item) {
        items.add(item);
        this.notifyDataSetChanged();
    }

    public class Holder
    {
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.layout_item_grid, null);
        holder.img=(ImageView) rowView.findViewById(R.id.image_item_grid);

        holder.img.setImageBitmap(((Activity) items.get(position)).getActivityImage());

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return rowView;
    }

}
