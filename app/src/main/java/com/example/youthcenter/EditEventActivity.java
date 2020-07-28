package com.example.youthcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ArrayList<Event> eList = Events.getInstance().geteList();
    EditText etTitle, etAge, etPlace, etDesc;
    Button updateEvent, etDate, etTimeStart, etTimeEnd, decreaseBtn, increaseBtn, feedbackBtn;
    TextView textVisitorAmount, textViewIsRunning;
    Switch aSwitch;



    private int timePicker;
    private int visitorAmount = 0;
    private boolean isRunning;
    private int position = 0;
    private Boolean admin;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);


        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        admin = intent.getBooleanExtra("admin", true);
        etTitle = (EditText) findViewById(R.id.etEditTitle);
        etDate = (Button) findViewById(R.id.etEditDate);
        etTimeStart = (Button) findViewById(R.id.etEditTimeStart);
        etTimeEnd = (Button) findViewById(R.id.etEditTimeEnd);
        etAge = (EditText) findViewById(R.id.etEditAge);
        etPlace = (EditText) findViewById(R.id.etEditPlace);
        etDesc = (EditText) findViewById(R.id.etEditDesc);

        decreaseBtn = (Button) findViewById(R.id.decreaseBtn1);
        increaseBtn = (Button) findViewById(R.id.increaseBtn1);
        textVisitorAmount = (TextView) findViewById(R.id.tvEditVisAmount);
        visitorAmount = eList.get(position).getVisitorAmount();
        aSwitch = (Switch) findViewById(R.id.swEditIsRunning);
        feedbackBtn = (Button) findViewById(R.id.toFeedBtn);
        //Button for addEvent
        updateEvent = findViewById(R.id.updateEvent);


        initEvent(v);
        toFeedbackActivity();
        //AddEvent button
        dateTimePicker();
        changeVisitors();
        setSwitch();

        setUpdateEvent();

    }

    //SwitchListener for EventIsRunning
    public void setSwitch() {
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
    }

    public void changeVisitors() {
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
    }

    public void dateTimePicker() {
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
    }

    public void toFeedbackActivity() {
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEventActivity.this, FeedbackActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    public void initEvent(View v) {
        etTitle.setText(eList.get(position).getTitle());
        etDate.setText(eList.get(position).getDate());
        etTimeStart.setText(eList.get(position).gettStart());
        etTimeEnd.setText(eList.get(position).gettEnd());
        etAge.setText(eList.get(position).getAge());
        etPlace.setText(eList.get(position).getPlace());
        etDesc.setText(eList.get(position).getDesc());
        textVisitorAmount.setText(String.valueOf(eList.get(position).getVisitorAmount()));
        aSwitch.setChecked(eList.get(position).isRunning());
    }

    public void setUpdateEvent() {
        updateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = eList.get(position);
                event.setTitle(etTitle.getText().toString());
                event.setDate(etDate.getText().toString());
                event.settStart(etTimeStart.getText().toString());
                event.settEnd(etTimeEnd.getText().toString());
                event.setAge(etAge.getText().toString());
                event.setPlace(etPlace.getText().toString());
                event.setDesc(etDesc.getText().toString());
                event.setVisitorAmount(Integer.parseInt(textVisitorAmount.getText().toString()));
                event.setRunning(isRunning);
                eList.set(position, event);
                Intent intent = new Intent(EditEventActivity.this, MainActivity.class);
                intent.putExtra("admin", admin);

                startActivity(intent);
            }
        });
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
            case R.id.etEditTimeStart:
                etTimeStart.setText(frd);
                break;
            case R.id.etEditTimeEnd:
                etTimeEnd.setText(frd);
                break;
        }
    }


}