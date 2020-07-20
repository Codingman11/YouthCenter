package com.example.youthcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedbackActivity extends AppCompatActivity {

    private FeedbackList feedbackList;
    private Button sendFeedback;
    private TextView checkFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackList = FeedbackList.getInstance();


        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        sendFeedback = (Button) findViewById(R.id.sendFeedBack);
        checkFeedback = (TextView) findViewById(R.id.checkFeedback);

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFeedback.setText("Your rating is " + ratingBar.getRating());
            }
        });




    }
}
