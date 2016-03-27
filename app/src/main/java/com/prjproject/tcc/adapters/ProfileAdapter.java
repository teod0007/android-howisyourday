package com.prjproject.tcc.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.prjproject.tcc.R;

/**
 * Created by Emanuel on 2016-03-26.
 */
public class ProfileAdapter extends CursorAdapter {

    public ProfileAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_item_profile, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtProfile = (TextView) view.findViewById(R.id.txtProfileItem);
        // Extract properties from cursor
        String profileName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        // Populate fields with extracted properties
        txtProfile.setText(profileName);
    }
}
