package com.example.myruns;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Map;

public class HistoryListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] options;

    public HistoryListAdapter(Activity context, String[] options) {
        super(context, R.layout.manual_entry_choice, options);
        this.context = context;
        this.options = options;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.history_entry_choice, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.historyTitle);
        TextView subTitle = rowView.findViewById(R.id.historySubtitle);

        txtTitle.setText(options[position]);

        return rowView;
    }
}
