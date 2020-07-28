package com.example.youthcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {


    private Button sendFeedbackBtn;
    private TextView checkFeedback;
    private EditText etFeedback;
    private RatingBar ratingBar;
    private ArrayList<Feedback> fList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);



        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        sendFeedbackBtn = (Button) findViewById(R.id.sendFeedBack);
        checkFeedback = (TextView) findViewById(R.id.checkFeedback);
        etFeedback = (EditText) findViewById(R.id.etFeedback);

        sendFeedback();
    }

    public void sendFeedback() {
        fList = Events.getInstance().getEvent(position).getFeedbackList();

        sendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fList.add(new Feedback(etFeedback.getText().toString(), String.valueOf(ratingBar.getRating())));

                finish();
            }
        });
    }
}
