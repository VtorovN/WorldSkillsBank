package com.example.worldskills;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List currencySet = new ArrayList<>();

    public void setItems(Collection<Currency> currencies) {
        currencySet.addAll(currencies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_course_view, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.bind((Currency) currencySet.get(position));
    }

    @Override
    public int getItemCount() {
        return currencySet.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCourseBuy, textViewCourseSell, textViewCurrencyCode, textViewCurrencyDecoded;
        private ImageView imageViewCourseBuyChange, imageViewCourseSellChange, imageCurrencyFlag;

        public CourseViewHolder(View itemView) {
            super(itemView);

            textViewCurrencyCode = itemView.findViewById(R.id.currencyView_text_currency_code);
            textViewCurrencyDecoded = itemView.findViewById(R.id.currencyView_text_currency_decoded);

            textViewCourseBuy = itemView.findViewById(R.id.currencyView_text_course_buy);
            textViewCourseSell = itemView.findViewById(R.id.currencyView_text_course_sell);

            imageViewCourseBuyChange = itemView.findViewById(R.id.currencyView_image_course_buy_change);
            imageViewCourseSellChange = itemView.findViewById(R.id.currencyView_image_course_sell_change);

            imageCurrencyFlag = itemView.findViewById(R.id.currencyView_image_currency_flag);
        }

        public void bind(Currency currency) {
            textViewCurrencyCode.setText(currency.getCode());
            textViewCurrencyDecoded.setText(currency.getDecoded());

            DecimalFormat decimalFormat = new DecimalFormat("##.00");

            textViewCourseBuy.setText(decimalFormat.format(currency.getCourse()));
            textViewCourseSell.setText(decimalFormat.format(currency.getCourse()));

            imageViewCourseBuyChange.setImageDrawable(
            imageViewCourseSellChange.setImageDrawable(

            imageCurrencyFlag.setImageResource(currency.getFlagId());
        }
    }
}
