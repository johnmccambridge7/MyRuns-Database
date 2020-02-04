package com.example.myruns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ManualEntryActivity extends AppCompatActivity {

    String[] options = {
            "Date",
            "Time",
            "Duration",
            "Distance",
            "Calories",
            "Heart Rate",
            "Comment"
    };

    static Map<String, String> config;
    static ManualEntryListAdapter listAdapter;
    String DIALOG_TYPE = "";
    ListView list;

    EntryDataSource database;

    int savedYear = 0;
    int savedMonth = 0;
    int savedDay = 0;

    int savedHour = 0;
    int savedMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        this.config = new HashMap<String, String>();

        /*
        "Date",
            "Time",
            "Duration",
            "Distance",
            "Calories",
            "Heart Rate",
            "Comment"
         */

        final Calendar c = Calendar.getInstance();

        savedYear = c.get(Calendar.YEAR);
        savedMonth = c.get(Calendar.MONTH);
        savedDay = c.get(Calendar.DAY_OF_MONTH);
        savedHour = c.get(Calendar.HOUR_OF_DAY);
        savedMinute = c.get(Calendar.MINUTE);

        this.database = new EntryDataSource(this);
        this.database.open();

        Bundle intentData = getIntent().getExtras();

        for(String option:this.options) {
            this.config.put(option, "");
        }

        this.config.put("Date", "");
        this.config.put("Time", "");
        this.config.put("Calories", "0");
        this.config.put("Distance", "0");
        this.config.put("Duration", "0");
        this.config.put("Heart Rate", "0");
        this.config.put("Comment", "");

        this.listAdapter = new ManualEntryListAdapter(ManualEntryActivity.this, options, config);
        this.list = findViewById(R.id.list);
        this.list.setAdapter(listAdapter);

        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                handleDialog(i);
            }
        });

        if(savedInstanceState != null) {
            DIALOG_TYPE = savedInstanceState.getString("DIALOG_TYPE");

            savedYear = savedInstanceState.getInt("year");
            savedMonth = savedInstanceState.getInt("month");
            savedDay = savedInstanceState.getInt("day");

            savedHour = savedInstanceState.getInt("hour");
            savedMinute = savedInstanceState.getInt("minute");

            for(String option:this.options) {
                Log.d("johnmacdonald", option + " - " + savedInstanceState.getString(option));
                this.config.put(option, savedInstanceState.getString(option));
            }

            Log.d("johnmacdonald", this.config.toString());

            if(DIALOG_TYPE != null) {
                switch (DIALOG_TYPE) {
                    case "date":
                        launchDateModal();
                        break;

                    case "time":
                        launchTimeModal();
                        break;
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        // store previous data
        for (String option:this.options) {
            bundle.putString(option, this.config.get(option));
        }

        bundle.putInt("year", savedYear);
        bundle.putInt("month", savedMonth);
        bundle.putInt("day", savedDay);

        bundle.putInt("hour", savedHour);
        bundle.putInt("minute", savedMinute);

        bundle.putString("DIALOG_TYPE", DIALOG_TYPE);
    }

    public void saveNewEntry(View view) {
        // use new thread to write to the database
        // add to the history fragment list adapter
        // notify change has occurred
        // handle metric to imperial conversion
        // {Heart Rate=100, Comment=Good Night, Time=16:24, Duration=100, Date=2020-2-14, Distance=355, Calories=300}

        new Thread(new Runnable() {
            @Override
            public void run() {
                // insert new record into database
                final ExerciseEntry entry = new ExerciseEntry();

                entry.setDateTime(config.get("Date") + " " + config.get("Time"));
                entry.setComment(config.get("Comment"));

                entry.setDuration(Integer.valueOf(config.get("Duration")));
                entry.setDistance(Float.valueOf(config.get("Distance")));
                entry.setCalorie(Integer.valueOf(config.get("Calories")));
                entry.setHeartRate(Integer.valueOf(config.get("Heart Rate")));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        History.entries.add(entry);
                        History.listAdapter.notifyDataSetChanged();
                    }
                });

                database.createEntry(entry);
                finish();
            }
        }).start();
    }

    public void launchDateModal() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // set instance variable for the date
                config.put("Date", year + "-" + (month + 1) + "-" + "" + day);

                savedYear = year;
                savedMonth = month;
                savedDay = day;

                listAdapter.notifyDataSetChanged();
            }
        }, savedYear, savedMonth, savedDay) {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day){
                savedYear = year;
                savedMonth = month;
                savedDay = day;
            }
        };

        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                DIALOG_TYPE = "";
            }
        });

        this.DIALOG_TYPE = "date";
        datePickerDialog.show();
    }

    public void launchTimeModal() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        config.put("Time", hourOfDay + ":" + minute);

                        savedHour = hourOfDay;
                        savedMinute = minute;

                        listAdapter.notifyDataSetChanged();
                    }
                }, savedHour, savedMinute, false) {

            @Override
            public void onTimeChanged (TimePicker view, int hourOfDay, int minute) {
                savedHour = hourOfDay;
                savedMinute = minute;
            }
        };

        timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                DIALOG_TYPE = "";
            }
        });

        this.DIALOG_TYPE = "time";
        timePickerDialog.show();
    }

    public void cancel(View view) {
        finish();
    }

    public void handleDialog(int i) {
        String choice = this.options[i];

        if(!choice.equals("Date") && !choice.equals("Time")) {

            DialogHandler dialog = new DialogHandler(choice, this.config);
            Bundle b = new Bundle();
            b.putInt(DialogHandler.DIALOG_KEY, DialogHandler.DIALOG_OPTION);
            b.putBoolean("textBased", false);

            if(choice.equals("Comment")) {
                b.putBoolean("textBased", true);
            }

            dialog.setArguments(b);
            dialog.show(getSupportFragmentManager(), "tag");
            return;
        }

        switch (choice) {
            case "Date":
                launchDateModal();
                break;
            case "Time":
                launchTimeModal();
                break;
        }
    }
}
