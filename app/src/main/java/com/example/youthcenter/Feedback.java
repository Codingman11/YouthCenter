package com.example.youthcenter;


public class Feedback {


    private String feedback;
    private String rating;



    public Feedback(String feedback, String rating) {
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getRating() {
        return rating;
    }


}
