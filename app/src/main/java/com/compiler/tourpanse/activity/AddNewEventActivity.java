package com.compiler.tourpanse.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.dbhelper.EventDataSource;
import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNewEventActivity extends AppCompatActivity {



    private EditText eventLocationET;
    private TextView travelStartingDateTV;
    private EditText travelDurationET;
    private EditText estimatedBudgetET;
    private Button saveEventButton;

    private String eventLocation;
    private String travelStartingDate;
    private String travelDuration;
    private String estimatedBudget;


    private EventDataSource eventDataSource;
    private SaveUserCredentialsToSharedPreference sfData;
    private Event event;


    private Intent intent;

    private int userId;
    private int eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        eventDataSource = new EventDataSource(AddNewEventActivity.this);
        userId = getIntent().getIntExtra("userId", 0);

        eventLocationET = (EditText) findViewById(R.id.eventLocationET);
        travelStartingDateTV = (TextView) findViewById(R.id.travelStartingDateTV);
        travelDurationET = (EditText) findViewById(R.id.travelDurationET);
        estimatedBudgetET = (EditText) findViewById(R.id.estimatedBudgetET);
        saveEventButton = (Button) findViewById(R.id.saveEventButton);


        travelStartingDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                travelStartingDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

    }

    public void saveEvent(View view) {
        eventLocation = eventLocationET.getText().toString().trim();
        travelStartingDate = travelStartingDateTV.getText().toString().trim();
        travelDuration = travelDurationET.getText().toString().trim();
        estimatedBudget = estimatedBudgetET.getText().toString().trim();


        if (eventLocation.equals("")) {
            eventLocationET.setError("Tour location is required.");
        } else if (travelStartingDate.equals("") || travelStartingDate.equals("Enter Date")) {
            travelStartingDateTV.setError("Travel starting date is required.");
        } else if (isEventDateSmall(travelStartingDate)) {
            travelStartingDateTV.setError("Enter valid date.");
        }

        /*else if(travelDuration.equals("")) {
            travelDurationET.setError("Enter estimated duration");
        } */
        else if (estimatedBudget.equals("")) {
            estimatedBudgetET.setError("Please enter estimated budget.");
        } else {
            event = new Event(userId, eventLocation, travelStartingDate, travelDuration, estimatedBudget);
            boolean status = eventDataSource.saveEvent(event);
            if (status) {
                Toast.makeText(this, "Tour created.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNewEventActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isEventDateSmall(String taskDate) {
        boolean isSmallDate = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
            Calendar todayCalendar = Calendar.getInstance();
            Calendar userCalendar = Calendar.getInstance();
            int day = todayCalendar.get(Calendar.DATE);
            int month = todayCalendar.get(Calendar.MONTH) + 1;
            int year = todayCalendar.get(Calendar.YEAR);
            Date systemProvidedDate = dateFormat.parse(day + "-" + month + "-" + year);
            Date userInputDate = dateFormat.parse(taskDate);
            todayCalendar.setTime(systemProvidedDate);
            userCalendar.setTime(userInputDate);
            if (userCalendar.getTimeInMillis() <= todayCalendar.getTimeInMillis()) {
                isSmallDate = true;
            }
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Enter a valid date.", Toast.LENGTH_SHORT).show();
        }
        return isSmallDate;
    }


}
