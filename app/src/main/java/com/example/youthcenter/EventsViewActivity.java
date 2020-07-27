package com.example.youthcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventsViewActivity extends AppCompatActivity {
    ListView listView;
    Intent intent;
    ArrayList<Event> eList = Events.getInstance().geteList();
    ArrayList<Feedback> fList = FeedbackList.getInstance().getFeedbackArrayList();
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);



        listView = findViewById(R.id.listView);

        adapter = new ListAdapter(this, eList, fList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditEventActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }






}