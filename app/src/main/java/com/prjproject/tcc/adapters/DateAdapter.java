package com.prjproject.tcc.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.prjproject.tcc.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Emanuel on 2016-04-22.
 */
public class DateAdapter extends CursorAdapter {

    public DateAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_item_date, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtProfile = (TextView) view.findViewById(R.id.txtDateItem);
        // Extract properties from cursor
        long dateMillis= cursor.getLong(cursor.getColumnIndexOrThrow("day_date"));
        Date date = new Date(dateMillis);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        String formattedDate = df.format(date);
        // Populate fields with extracted properties
        txtProfile.setText(formattedDate);
    }
}
