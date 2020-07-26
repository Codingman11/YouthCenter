package com.example.youthcenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class    CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button addEvent, etDate, etTimeStart, etTimeEnd, decreaseBtn, increaseBtn;
    EditText etTitle, etAge, etPlace, etDesc;
    TextView textVisitorAmount, textViewIsRunning;
    Switch aSwitch;

    private int timePicker;
    private int visitorAmount = 0;
    private boolean isRunning;
    private boolean admin;


    Events eList = Events.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Initializing all attributes for create an event
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDate = (Button) findViewById(R.id.etDate);
        etTimeStart = (Button) findViewById(R.id.etTimeStart);
        etTimeEnd = (Button) findViewById(R.id.etTimeEnd);
        etAge = (EditText) findViewById(R.id.etAge);
        etPlace = (EditText) findViewById(R.id.etPlace);
        etDesc = (EditText) findViewById(R.id.etDesc);

        //Visitor amount
        decreaseBtn = (Button) findViewById(R.id.decreaseBtn1);
        increaseBtn = (Button) findViewById(R.id.increaseBtn1);
        textVisitorAmount = (TextView) findViewById(R.id.tvVisAmount);
        Intent intent = getIntent();
        admin = intent.getBooleanExtra("admin", true);
        //EventIsRunning
        textViewIsRunning = findViewById(R.id.swIsRunning);


        //Button for addEvent
        addEvent = findViewById(R.id.addEvent);

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visitorAmount > 0) {
                    visitorAmount--;
                    textVisitorAmount.setText(Integer.toString(visitorAmount));
                } else {
                    Toast.makeText(v.getContext(), "Kävijälaskuri ei voi olla pienempi kuin 0!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitorAmount++;
                textVisitorAmount.setText(Integer.toString(visitorAmount));
            }
        });

        //DatePickerListener for date, TimePickerListener for startTime and endTime
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "date picker");
            }
        });


        etTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = v.getId();
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "timeStart");
            }
        });

        etTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = v.getId();
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "timeEnd");
            }
        });


        //SwitchListener for EventIsRunning
        aSwitch = findViewById(R.id.swIsRunning);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    aSwitch.setTextOn("ON");
                    isRunning = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Tapahtuma ei käynnissä.", Toast.LENGTH_SHORT).show();
                    aSwitch.setTextOff("OFF");
                    isRunning = false;
                }
            }
        });

        //AddEvent button

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);


                eList.AddToArray(new Event(etTitle.getText().toString(), etDate.getText().toString(), etTimeStart.getText().toString(), etTimeEnd.getText().toString(), etAge.getText().toString(), etPlace.getText().toString(), etDesc.getText().toString(), Integer.parseInt(textVisitorAmount.getText().toString()), 1, isRunning));
                intent.putExtra("admin", admin);
                startActivity(intent);
            }
        });
    }
    public void decreaseAmount(View view) {
        visitorAmount = eList.geteList().get(view.getId()).getVisitorAmount();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        etDate.setText(dayOfMonth + "." + month + "." + year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        String time = hourOfDay + ":" + minute;
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        Date date = null;

        try {
            date = fmt.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String frd = fmt.format(date);
        switch (timePicker) {
            case R.id.etTimeStart:
                etTimeStart.setText(frd);
                break;
            case R.id.etTimeEnd:
                etTimeEnd.setText(frd);
                break;
        }
    }


}
