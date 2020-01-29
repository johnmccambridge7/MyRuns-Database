package com.example.myruns;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    HistoryListAdapter listAdapter;
    ListView list;

    String[] options = {
            "Hello World.",
            "Test Label.",
            "This is another item."
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        this.listAdapter = new HistoryListAdapter(getActivity(), options);
        this.list = view.findViewById(R.id.historyEntries);
        this.list.setAdapter(listAdapter);

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