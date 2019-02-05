package com.example.worldskills;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class AccountSection extends StatelessSection {
    List<Account> accountList;

    public AccountSection(List<Account> list) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.account_info_view)
                .headerResourceId(R.layout.header_accounts)
                .build());
        accountList = list;
    }

    @Override
    public int getContentItemsTotal() {
        return accountList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountViewHolder itemHolder = (AccountViewHolder) holder;
        itemHolder.bind(accountList.get(position));
    }
}

class AccountViewHolder extends RecyclerView.ViewHolder {

    TextView accountType, accountNumber, accountBalance;
    String currentAccountString, rublesString;

    public AccountViewHolder (View accountView) {
        super(accountView);
        accountType = accountView.findViewById(R.id.accountView_text_type);
        accountNumber = accountView.findViewById(R.id.accountView_text_account_number);
        accountBalance = accountView.findViewById(R.id.acccountView_text_balance);
        currentAccountString = itemView.getContext().getResources().getString(R.string.current_account);
        rublesString = itemView.getContext().getResources().getString(R.string.rubles);
    }

    public void bind(Account account) {
        switch (account.getType()) {                              //any other types?
            case "current":
                accountType.setText(currentAccountString);
                break;

            //case ""
        }

        String accountNumberRaw = account.getNumber();
        String accountNumberHidden = "****" + accountNumberRaw.substring(4); //**** 000000
        accountNumber.setText(accountNumberHidden);

        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        String balanceString = decimalFormat.format(account.getBalance()) + " " + rublesString;
        accountBalance.setText(balanceString);
    }
}
