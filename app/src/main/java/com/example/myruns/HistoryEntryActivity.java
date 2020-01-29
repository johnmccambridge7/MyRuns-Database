package com.example.myruns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class HistoryEntryActivity extends AppCompatActivity {

    EditText activityType;
    EditText date;
    EditText duration;
    EditText distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_entry);

        this.activityType = findViewById(R.id.historyActivityType);
        this.date = findViewById(R.id.historyDate);
        this.duration = findViewById(R.id.historyDuration);
        this.distance = findViewById(R.id.historyDistance);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
