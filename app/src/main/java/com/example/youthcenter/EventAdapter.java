package com.example.youthcenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private Button openBtn, deleteBtn, dataChangedBtn, toFBBtn;
    private Switch isEveRun;
    public Events eList = Events.getInstance();
    private static int TYPE_ADMIN = 1;
    private static int TYPE_GUEST = 2;
    private int resource;

    public EventAdapter(Context context, int resource) {
        this.mContext = context;
        this.resource = resource;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == TYPE_GUEST) {
            view = LayoutInflater.from(mContext).inflate(R.layout.event_list_guest, parent, false);
            return new EventViewGuest(view);
        }


        return new EventViewAdmin(LayoutInflater.from(mContext).inflate(R.layout.event_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_GUEST) {

            ArrayList<Event> event = eList.geteList();
            ((EventViewGuest) holder).setEventDetail(event.get(position));
        } else  {


            ArrayList<Event> event = eList.geteList();
            ((EventViewAdmin) holder).setEventDetail(event.get(position));

        }
    }


    @Override
    public int getItemCount() {
        return eList.geteList().size();
    }

    @Override
    public int getItemViewType(int position) {

        if (resource == 1) {
            return TYPE_ADMIN;
        } else if (resource == 2) {
            return TYPE_GUEST;
        } else {
            return super.getItemViewType(position);
        }
    }


    class EventViewGuest extends RecyclerView.ViewHolder {
        ImageView imageView;
        private TextView textViewTitle, textViewDesc, textViewPlace, textViewDate, textViewAge, textViewVisAmount, textViewIsRunning;
        private TextView textViewVisAmPop;


        public EventViewGuest(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewPlace = itemView.findViewById(R.id.textViewPlace);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewVisAmount = itemView.findViewById(R.id.visitorAmount);
            textViewIsRunning = itemView.findViewById(R.id.tvIsRunning);
        }

        private void setEventDetail(Event event) {
            textViewTitle.setText(event.getTitle());
            textViewDate.setText("Päivämäärä: " + event.getDate() + "\t klo " + event.gettStart() + " - " + event.gettEnd());
            textViewAge.setText("Ikähaarukka: " + event.getAge());
            textViewDesc.setText("Lisätiedot: " + event.getDesc());
            textViewPlace.setText("Paikka: " + event.getPlace());
            textViewVisAmount.setText("Kävijälaskuri: \n" + event.getVisitorAmount());
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));
            if (event.isRunning() == true) {
                textViewIsRunning.setText("KÄYNNISSÄ");
                textViewIsRunning.setTextColor(mContext.getColor(R.color.isRunning));
            } else {
                textViewIsRunning.setText(mContext.getResources().getText(R.string.isNotRunning));
                textViewIsRunning.setTextColor(mContext.getColor(R.color.isNotRunning));
            }
        }
    }

    class EventViewAdmin extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewTitle, textViewDesc, textViewPlace, textViewDate, textViewAge, textViewVisAmount, textViewIsRunning;
        private TextView textViewVisAmPop;
        Button increaseBtnPop, decreaseBtnPop;
        int visitorAm = 0;
        boolean isRunning;

        @SuppressLint("ResourceType")
        public EventViewAdmin(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewPlace = itemView.findViewById(R.id.textViewPlace);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewVisAmount = itemView.findViewById(R.id.visitorAmount);
            textViewIsRunning = itemView.findViewById(R.id.tvIsRunning);
            dataChangedBtn = itemView.findViewById(R.id.dataChangedBtn);


            deleteEvent(itemView);
            openEvent(itemView);
        }

        private void setEventDetail(Event event) {
            textViewTitle.setText(event.getTitle());
            textViewDate.setText("Päivämäärä: " + event.getDate() + "\t klo " + event.gettStart() + " - " + event.gettEnd());
            textViewAge.setText("Ikähaarukka: " + event.getAge());
            textViewDesc.setText("Lisätiedot: " + event.getDesc());
            textViewPlace.setText("Paikka: " + event.getPlace());
            textViewVisAmount.setText("Kävijälaskuri: \n" + event.getVisitorAmount());
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));
            if (event.isRunning() == true) {
                textViewIsRunning.setText(mContext.getResources().getText(R.string.isRunning));
                textViewIsRunning.setTextColor(mContext.getColor(R.color.isRunning));
            } else {
                textViewIsRunning.setText(mContext.getResources().getText(R.string.isNotRunning));
                textViewIsRunning.setTextColor(mContext.getColor(R.color.isNotRunning));
            }
        }

        public void deleteEvent(View v) {
            deleteBtn = v.findViewById(R.id.btnDeleteEvent);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eList.geteList().remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void openEvent(View v) {
            openBtn = v.findViewById(R.id.btnOpenEvent);
            openBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onButtonShowPopUpWindowClick(itemView);
                    Intent intent = new Intent(mContext, EditEventActivity.class);
                    intent.putExtra("position", getAdapterPosition());
                    intent.putExtra("admin", true);
                    mContext.startActivity(intent);
                    notifyDataSetChanged();
                }
            });

        }

    }
}



