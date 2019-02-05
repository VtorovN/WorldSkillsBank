package com.example.worldskills;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class CreditSection extends StatelessSection {
    List<Credit> creditList;

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

    TextView creditType, creditNextPayment, creditMoney;
    String cashCreditString, mortgageCreditString, creditPaymentString, rublesString;

    public CreditViewHolder (View creditView) {
        super(creditView);
        creditType = creditView.findViewById(R.id.creditView_text_type);
        creditNextPayment = creditView.findViewById(R.id.creditView_text_payment);
        creditMoney = creditView.findViewById(R.id.creditView_text_money);
        cashCreditString = itemView.getResources().getString(R.string.cash_credit);
        mortgageCreditString = itemView.getResources().getString(R.string.mortgage_credit);
        creditPaymentString = itemView.getResources().getString(R.string.credit_payment);
        rublesString = itemView.getResources().getString(R.string.rubles);
    }

    public void bind (Credit credit) {
        switch (credit.getType()) {
            case "cash":
                creditType.setText(cashCreditString);
                break;

            case "mortgage":
                creditType.setText(mortgageCreditString);
                break;
        }

        String nextPaymentString = creditPaymentString + " "
                + credit.getType(); //if date is already in DD.MM.YYYY

        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        String moneyString = decimalFormat.format(credit.getType()) + " " + rublesString;
        creditMoney.setText(moneyString);
    }
}