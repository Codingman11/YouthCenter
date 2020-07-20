package com.example.youthcenter;

import java.util.ArrayList;

class FeedbackList {

    private static FeedbackList feedbackList;
    private ArrayList<Feedback> feedbackArrayList;
    public static FeedbackList getInstance() {
        if(feedbackList == null) {
            feedbackList = new FeedbackList();
        }
        return feedbackList;
    }

    public FeedbackList() {
        feedbackArrayList = new ArrayList<>();
    }

}
