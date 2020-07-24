package com.example.youthcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private FeedbackList feedbackList;
    private Button sendFeedbackBtn;
    private TextView checkFeedback;
    private EditText etFeedback;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackList = FeedbackList.getInstance();


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        sendFeedbackBtn = (Button) findViewById(R.id.sendFeedBack);
        checkFeedback = (TextView) findViewById(R.id.checkFeedback);
        etFeedback = (EditText) findViewById(R.id.etFeedback);

        sendFeedback();
    }

    public void sendFeedback() {
        sendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackList.addFeedback(new Feedback(etFeedback.getText().toString(), String.valueOf(ratingBar.getRating())));
                finish();
            }
        });
    }
}
