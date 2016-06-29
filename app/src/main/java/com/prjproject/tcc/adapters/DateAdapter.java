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
import java.text.SimpleDateFormat;
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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        String formattedDate = df.format(date);
        String[] dateParts = formattedDate.split("/");
        String formattedDate2 = dateParts[0] + " de " + getMonth(dateParts[1]) + " , " + dateParts[2];
        // Populate fields with extracted properties
        txtProfile.setText(formattedDate2);
    }

    private String getMonth(String datePart) {
        int month = Integer.parseInt(datePart);
        switch(month){
            case 1: return "Janeiro";
            case 2: return "Fevereiro";
            case 3: return "Março";
            case 4: return "Abril";
            case 5: return "Maio";
            case 6: return "Junho";
            case 7: return "Julho";
            case 8: return "Agosto";
            case 9: return "Setembro";
            case 10: return "Outubro";
            case 11: return "Novembro";
            case 12: return "Dezembro";

        }
        return null;

    }
}
