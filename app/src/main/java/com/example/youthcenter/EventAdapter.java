package com.example.youthcenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    Button openBtn, deleteBtn;
    private int itemID;

    public Events eList = Events.getInstance();


    public EventAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_list, null);


        EventViewHolder holder = new EventViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eList.geteList().get(position);
        holder.textViewTitle.setText("Otsikko: " + event.getTitle());
        holder.textViewDate.setText("Päivämäärä: " + event.getDate() + "\t klo " + event.gettStart() + " - " + event.gettEnd());

        holder.textViewDesc.setText("Lisätiedot: " + event.getDesc());
        holder.textViewPlace.setText("Paikka: " + event.getPlace());
        holder.textViewVisAmount.setText("Kävijälaskuri: \n" + event.getVisitorAmount());



        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background));

    }

    @Override
    public int getItemCount() {
        return eList.geteList().size();
    }


    class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewPlace, textViewDate, textViewDateTime, textViewVisAmount;


        @SuppressLint("ResourceType")
        public EventViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewPlace = itemView.findViewById(R.id.textViewPlace);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewVisAmount = itemView.findViewById(R.id.visitorAmount);
            openBtn = itemView.findViewById(R.id.btnOpenEvent);
            deleteBtn = itemView.findViewById(R.id.btnDeleteEvent);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eList.geteList().remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });


            openBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonShowPopUpWindowClick(itemView);
                }
            });
        }

        public void onButtonShowPopUpWindowClick(View view) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.popup_event, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;

            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


            popupWindow.showAtLocation(view, Gravity.CENTER, 0 , 0);

            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
        }
    }

    public void increaseVisitor(View v) {
        TextView visitor = v.findViewById(R.id.visitorAmountPop);

        int visitorAmount = eList.geteList().get(getItemCount()).getVisitorAmount();

    }


}
