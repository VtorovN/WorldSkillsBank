package com.example.worldskills.RVAdapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worldskills.Model.ATM;
import com.example.worldskills.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ATMViewHolder> {

    private List atmSet = new ArrayList<>();

    public void setItems(Collection<ATM> atms) {
        atmSet.addAll(atms);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ATMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atm_info_view, parent, false);
        return new ATMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ATMViewHolder holder, int position) {
        holder.bind((ATM) atmSet.get(position));
    }

    @Override
    public int getItemCount() {
        return atmSet.size();
    }

    class ATMViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewATMAddress, textViewATMState, textViewATMType, textViewATMHours;
        private String hours, open, close;
        //private int green, red;

        public ATMViewHolder(View itemView) {
            super(itemView);
            textViewATMAddress = itemView.findViewById(R.id.atmView_text_address);
            textViewATMState = itemView.findViewById(R.id.atmView_text_state);
            textViewATMType = itemView.findViewById(R.id.atmView_text_type);
            textViewATMHours = itemView.findViewById(R.id.atmView_text_hours);

            hours = itemView.getResources().getString(R.string.hours);
            open = itemView.getResources().getString(R.string.open);
            close = itemView.getResources().getString(R.string.close);
        }

        public void bind(ATM atm) {
            textViewATMAddress.setText(atm.getAddress());
            textViewATMType.setText(atm.getType());
            String atmTimeStart = atm.getTimeStart();
            String atmTimeEnd = atm.getTimeEnd();
            String workingTime = hours + " " + atmTimeStart + "-" + atmTimeEnd;
            textViewATMHours.setText(workingTime);

            //open/close?
            int hourStart = Integer.valueOf(atmTimeStart.substring(0,2));
            int minuteStart = Integer.valueOf(atmTimeStart.substring(3));
            int hourEnd = Integer.valueOf(atmTimeEnd.substring(0,2));
            if (hourEnd == 0) hourEnd = 24;
            int minuteEnd = Integer.valueOf(atmTimeStart.substring(3));

            Calendar currentDate = Calendar.getInstance();
            int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentDate.get(Calendar.MINUTE);

            if (currentHour > hourStart) { //Too difficult. Find alternative?
                if (currentHour < hourEnd - 1) {
                    textViewATMState.setText(open);
                    textViewATMState.setTextColor(Color.parseColor("#00FF00"));
                } else if (currentHour == hourEnd - 1 && currentMinute < minuteEnd) {
                    textViewATMState.setText(open);
                    textViewATMState.setTextColor(Color.parseColor("#00FF00"));
                }
            }
            if (!(currentHour > hourStart) && currentHour == hourStart && currentMinute >= minuteStart) {
                textViewATMState.setText(open);
                textViewATMState.setTextColor(Color.parseColor("#00FF00"));
            }
            else if (!(currentHour > hourStart)) {
                textViewATMState.setText(close);
                textViewATMState.setTextColor(Color.parseColor("#FF0000"));
            }
        }
    }
}
