package com.example.youthcenter;

import java.util.ArrayList;

class FeedbackList extends ArrayList<Feedback> {

    private static FeedbackList feedbackList;
    private ArrayList<Feedback> feedbackArrayList;
    public static FeedbackList getInstance() {
        if(feedbackList == null) {
            feedbackList = new FeedbackList();
        }
        return feedbackList;
    }

    public FeedbackList() {
        this.feedbackArrayList = new ArrayList<>();
    }

    public ArrayList<Feedback> getFeedbackArrayList() {
        return feedbackArrayList;
    }

    public void setFeedbackArrayList(ArrayList<Feedback> feedbackArrayList) {
        this.feedbackArrayList = feedbackArrayList;
    }



    public void addFeedback(Feedback feedback) {
        this.feedbackArrayList.add(feedback);
    }
}
