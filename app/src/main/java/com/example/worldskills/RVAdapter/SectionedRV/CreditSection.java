package com.example.worldskills.RVAdapter.SectionedRV;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.worldskills.Model.Credit;
import com.example.worldskills.R;

import java.text.DecimalFormat;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class CreditSection extends StatelessSection {
    private List<Credit> creditList;

    public CreditSection(List<Credit> list) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.credit_info_view)
                .headerResourceId(R.layout.header_credits)
                .build());
        creditList = list;
    }

    @Override
    public int getContentItemsTotal() {
        return creditList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        CreditViewHolder itemHolder = (CreditViewHolder) holder;
        itemHolder.bind(creditList.get(position));
    }
}

class CreditViewHolder extends RecyclerView.ViewHolder {

    private TextView creditName, creditNextPayment, creditSum;
    private String creditPaymentString, rublesString;

    public CreditViewHolder (View creditView) {
        super(creditView);
        creditName = creditView.findViewById(R.id.creditView_text_name);
        creditNextPayment = creditView.findViewById(R.id.creditView_text_payment);
        creditSum = creditView.findViewById(R.id.creditView_text_money);
        creditPaymentString = itemView.getResources().getString(R.string.credit_payment);
        rublesString = itemView.getResources().getString(R.string.rubles);
    }

    public void bind (Credit credit) {
        creditName.setText(credit.getCreditName());

        String[] date = credit.getPaymentDate().substring(0, 10).split("-"); //YYYY-MM-DD
        String nextPaymentString = creditPaymentString + " "
                + date[2] + "." + date[1] + "." + date[0];
        creditNextPayment.setText(nextPaymentString);

        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        double sum = credit.getSum()/100.0d;
        String sumString = decimalFormat.format(sum) + " " + rublesString;
        creditSum.setText(sumString);
    }
}