package com.example.youthcenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private Button openBtn, deleteBtn, dataChangedBtn, toFBBtn;
    private Switch isEveRun;

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
        holder.textViewAge.setText("Ikähaarukka: " + event.getAge());
        holder.textViewDesc.setText("Lisätiedot: " + event.getDesc());
        holder.textViewPlace.setText("Paikka: " + event.getPlace());
        holder.textViewVisAmount.setText("Kävijälaskuri: \n" + event.getVisitorAmount());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background));
        if (eList.geteList().get(position).isRunning() == true) {
            holder.textViewIsRunning.setText(context.getResources().getText(R.string.isRunning));
            holder.textViewIsRunning.setTextColor(context.getColor(R.color.isRunning));
        } else {
            holder.textViewIsRunning.setText(context.getResources().getText(R.string.isNotRunning));
            holder.textViewIsRunning.setTextColor(context.getColor(R.color.isNotRunning));
        }


    }

    @Override
    public int getItemCount() {
        return eList.geteList().size();
    }


    class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewPlace, textViewDate, textViewAge, textViewVisAmount, textViewIsRunning;
        TextView textViewVisAmPop;
        Button increaseBtnPop, decreaseBtnPop;
        int visitorAm = 0;
        boolean isRunning;

        @SuppressLint("ResourceType")
        public EventViewHolder(@NonNull final View itemView) {
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
                    onButtonShowPopUpWindowClick(itemView);
                }
            });

        }

        public void decreaseVisAm(View v) {
            decreaseBtnPop = v.findViewById(R.id.decreaseBtnPop);

            decreaseBtnPop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visitorAm--;
                    textViewVisAmPop.setText(String.valueOf(visitorAm));
                }
            });
        }

        public void increaseVisAm(View v) {
            increaseBtnPop = v.findViewById(R.id.increaseBtnPop);
            increaseBtnPop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visitorAm++;
                    textViewVisAmPop.setText(String.valueOf(visitorAm));
                }
            });
        }

        public void onUpdateVisAm(View v, final PopupWindow popupWin) {

            dataChangedBtn = v.findViewById(R.id.dataChangedBtn);
            dataChangedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visitorAm = Integer.parseInt(textViewVisAmPop.getText().toString());
                    eList.geteList().get(getAdapterPosition()).setRunning(isRunning);
                    eList.geteList().get(getAdapterPosition()).setVisitorAmount(visitorAm);
                    notifyItemChanged(getAdapterPosition());
                    popupWin.dismiss();
                }
            });

        }

        public void toFeedBackActivity(View v) {
            toFBBtn = v.findViewById(R.id.toFeedBtn);

            toFBBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(new Intent(context, FeedbackActivity.class));
                }
            });
        }

        public void checkSwitch(View v) {
            isEveRun = v.findViewById(R.id.swIsRunningPop);
            if (eList.geteList().get(getAdapterPosition()).isRunning() == true) {
                isEveRun.setChecked(true);
            } else {
                isEveRun.setChecked(false);
            }

            isEveRun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        isEveRun.setTextOn("ON");
                        Toast.makeText(context.getApplicationContext(), "Tapahtuma käynnissä.", Toast.LENGTH_SHORT).show();
                        isRunning = true;
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Tapahtuma ei käynnissä.", Toast.LENGTH_SHORT).show();
                        isEveRun.setTextOff("OFF");
                        isRunning = false;
                    }
                }
            });



        }

        public void onButtonShowPopUpWindowClick(View view) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View popupView = inflater.inflate(R.layout.popup_event, null);
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;

            PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            textViewVisAmPop = popupView.findViewById(R.id.visitorAmountPop);
            dataChangedBtn = popupView.findViewById(R.id.dataChangedBtn);

            textViewVisAmPop.setText(String.valueOf(eList.getEvent(getAdapterPosition()).getVisitorAmount()));
            visitorAm = eList.geteList().get(getAdapterPosition()).getVisitorAmount();

            decreaseVisAm(popupView);
            increaseVisAm(popupView);
            checkSwitch(popupView);
            onUpdateVisAm(popupView, popupWindow);
            toFeedBackActivity(popupView);

            popupWindow.update();
        }

        public  void readXML() {
            WriteAndRead writeAndRead = WriteAndRead.getInstance();
            writeAndRead.parseXML(context);
            notifyItemInserted(getAdapterPosition());
        }
    }
}



