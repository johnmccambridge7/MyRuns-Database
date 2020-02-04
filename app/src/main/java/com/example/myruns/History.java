package com.example.myruns;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    public static HistoryListAdapter listAdapter;
    ListView list;

    private EntryDataSource database;
    public static ArrayList<ExerciseEntry> entries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.entries = new ArrayList<ExerciseEntry>();
        this.database = new EntryDataSource(getActivity());
        this.database.open();

        this.listAdapter = new HistoryListAdapter(getActivity(), this.entries);

        // database.deleteAllEntries();

        // thread to retrieve data from histories table
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ExerciseEntry> records = database.getAllEntries();
                // update list
                for(ExerciseEntry record : records) {
                    entries.add(record);
                }

                listAdapter.notifyDataSetChanged();
            }
        }).start();

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        this.list = view.findViewById(R.id.historyEntries);

        if(listAdapter != null) {
            this.list.setAdapter(listAdapter);
        }

        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), HistoryEntryActivity.class);
                // i.putExtra("activityType", this.selectedActivity);
                startActivity(intent);
            }
        });

        return view;
    }
}