package com.example.youthcenter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Event> eList;
    ArrayList<Feedback> fList;
    Feedback feedback;
    private static LayoutInflater inflater = null;

    public ListAdapter(Activity context, ArrayList<Event> events, ArrayList<Feedback> feedbacks) {
        this.context = context;
        this.eList = events;
        this.fList = feedbacks;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return eList.size();
    }

    @Override
    public Object getItem(int position) {
        return eList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.row, null): itemView;

        TextView textViewTitle = (TextView) itemView.findViewById(R.id.tvTitle1);
        TextView textViewDate = (TextView) itemView.findViewById(R.id.tvDate);
        TextView textViewFeedback = (TextView) itemView.findViewById(R.id.feedbacks);


        Event event = eList.get(position);
        textViewTitle.setText(event.getTitle());
        textViewDate.setText("Päivämäärä: " + event.getDate() + "\n Kellonaika: " + event.gettStart() + ":" + event.gettEnd());
        if (!fList.isEmpty()) {
            feedback = fList.get(position);
            textViewFeedback.setText("Arvosana: " + feedback.getRating() + "\nPalaute: " + feedback.getFeedback() );
        }
        return itemView;
    }
}
