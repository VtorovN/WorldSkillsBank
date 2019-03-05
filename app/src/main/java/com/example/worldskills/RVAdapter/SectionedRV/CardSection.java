package com.example.worldskills.RVAdapter.SectionedRV;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worldskills.Model.Card;
import com.example.worldskills.R;
import com.example.worldskills.UI.CardActivity;

import java.text.DecimalFormat;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class CardSection extends StatelessSection {
    private List<Card> cardList;

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

    private TextView cardName, cardNumber, cardBalance;
    private ImageView cardType;
    private String rublesString, debitCardString, creditCardString;

    public CardViewHolder (View cardView) {
        super(cardView);
        cardName = cardView.findViewById(R.id.cardView_text_card_name);
        cardNumber = cardView.findViewById(R.id.cardView_text_card_number);
        cardBalance = cardView.findViewById(R.id.cardView_text_balance);
        cardType = cardView.findViewById(R.id.cardVIew_image_type);
        rublesString = itemView.getContext().getResources().getString(R.string.rubles);
        cardView.setOnClickListener(new View.OnClickListener() { //find way to get card directly?
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CardActivity.class);
                intent.putExtra("cardLastDigits",
                        cardNumber.getText().toString().substring(12));
                v.getContext().startActivity(intent);
            }
        });
    }

    public void bind(Card card) {

        cardName.setText(card.getName());

        String cardNumberRaw = card.getNumber();
        String cardNumberHidden = cardNumberRaw.substring(0, 4) // in case when card format is
                + "********"            // 0000 0000 0000 0000 text is
                + cardNumberRaw.substring(12); //0000 **** **** 0000
        cardNumber.setText(cardNumberHidden);

        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        double balance = card.getBalance()/100.0d;
        String balanceString = decimalFormat.format(balance) + " " + rublesString;
        cardBalance.setText(balanceString);

        switch (card.getType()) {
            case 0:
                cardType.setImageResource(R.drawable.mir_card);
                break;

            case 1:
            cardType.setImageResource(R.drawable.visa_card);
            break;

            case 2:
                cardType.setImageResource(R.drawable.mastercard_card);
                break;

            case 3:
                cardType.setImageResource(R.drawable.maestro_card);
                break;
        }
    }
}
