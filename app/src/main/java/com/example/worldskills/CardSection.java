package com.example.worldskills;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class CardSection extends StatelessSection {
    List<Card> cardList;

    public CardSection(List<Card> list) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.card_info_view)
                .headerResourceId(R.layout.header_cards)
                .build());
        cardList = list;
    }

    @Override
    public int getContentItemsTotal() {
        return cardList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new CardViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardViewHolder itemHolder = (CardViewHolder) holder;
        itemHolder.bind(cardList.get(position));
    }
}

class CardViewHolder extends RecyclerView.ViewHolder {

    TextView cardType, cardNumber, cardBalance;
    ImageView cardCompany;
    String rublesString, debitCardString, creditCardString;

    public CardViewHolder (View cardView) {
        super(cardView);
        cardType = cardView.findViewById(R.id.cardView_text_card_type);
        cardNumber = cardView.findViewById(R.id.cardView_text_card_number);
        cardBalance = cardView.findViewById(R.id.cardView_text_balance);
        cardCompany = cardView.findViewById(R.id.cardVIew_image_company);
        rublesString = itemView.getContext().getResources().getString(R.string.rubles);
        debitCardString = itemView.getContext().getResources().getString(R.string.debit_card);
        creditCardString = itemView.getContext().getResources().getString(R.string.credit_card);
    }

    public void bind(Card card) {

        switch(card.getType()) {
            case "debit":
                cardType.setText(debitCardString);
                break;

            case "credit":
                cardType.setText(creditCardString);
                break;
        }

        String cardNumberRaw = card.getNumber();
        String cardNumberHidden = cardNumberRaw.substring(0, 4) // in case when card format is
                + "********"            // 0000 0000 0000 0000 text is
                + cardNumberRaw.substring(12); //0000 **** **** 0000
        cardNumber.setText(cardNumberHidden);

        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        String balanceString = decimalFormat.format(card.getBalance()) + " " + rublesString;
        cardBalance.setText(balanceString);

        switch (card.getCompany()) {
            case "maestro":
                cardCompany.setImageResource(R.drawable.maestro_card);
                break;

            case "visa":
                cardCompany.setImageResource(R.drawable.visa_card);
                break;

            case "mir":
                cardCompany.setImageResource(R.drawable.mir_card);
                break;
        }
    }
}
