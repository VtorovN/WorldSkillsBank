package com.example.worldskills.RVAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worldskills.Model.Operation;
import com.example.worldskills.R;
import com.example.worldskills.Utility.App;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.OperationViewHolder> {

    private List operationList = new ArrayList<>();

    public void setItems(Collection<Operation> items){
        operationList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operation_info_view, parent, false);
        return new OperationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationViewHolder holder, int position) {
        holder.bind((Operation) operationList.get(position));
    }

    @Override
    public int getItemCount() {
        return operationList.size();
    }

    class OperationViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewDate, textViewSum;

        public OperationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.operation_text_name);
            textViewDate = itemView.findViewById(R.id.operation_text_date);
            textViewSum = itemView.findViewById(R.id.operation_text_sum);
        }

        public void bind(Operation operation) {
            textViewName.setText(operation.getOperationName());
            textViewDate.setText(operation.getOperationDate());

            DecimalFormat decimalFormat = new DecimalFormat("##0.00");
            double sum = operation.getSum()/100.0d;
            String sumString = decimalFormat.format(sum) + " " +
                    App.getContext().getString(R.string.rubles);
            textViewSum.setText(sumString);
        }

    }
}
