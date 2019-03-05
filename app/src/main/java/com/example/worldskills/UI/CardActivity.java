package com.example.worldskills.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worldskills.DialogFragment.ChangeCardNameDialogFragment;
import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.Model.Card;
import com.example.worldskills.R;
import com.example.worldskills.Model.User;

import java.text.DecimalFormat;

public class CardActivity extends AppCompatActivity {
    private Card currentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        String cardLastDigits = getIntent().getStringExtra("cardLastDigits");
        Card[] userCards = User.getCurrentUser().getCards();
        currentCard = null;
        for (Card card : userCards) {
            if (card.getNumber().substring(12).equals(cardLastDigits)) {
                currentCard = card;
                break;
            }
        }

        initCardView();
        initBottomToolbar();
        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_profile_features) {
            startActivity(new Intent(this, ProfileFeaturesActivity.class));
        }
        return true;
    }

    private void initCardView() {
        TextView cardName = findViewById(R.id.card_name);
        TextView cardNumber = findViewById(R.id.card_number);
        TextView cardBalance = findViewById(R.id.card_balance);
        TextView cardBlocked = findViewById(R.id.card_blocked);
        LinearLayout cardActions = findViewById(R.id.card_actions_layout);
        ImageView cardType = findViewById(R.id.card_type);

        if (currentCard.isBlocked()) {
            cardActions.setVisibility(View.INVISIBLE);
        }
        else {
            cardBlocked.setVisibility(View.INVISIBLE);
        }

        cardName.setText(currentCard.getName());

        String cardNumberRaw = currentCard.getNumber();
        String cardNumberHidden = cardNumberRaw.substring(0, 4) // in case when card format is
                + "********"            // 0000 0000 0000 0000 text is
                + cardNumberRaw.substring(12); //0000 **** **** 0000
        cardNumber.setText(cardNumberHidden);

        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        double balance = currentCard.getBalance()/100.0d;
        String balanceString = decimalFormat.format(balance) + " " +
                getBaseContext().getString(R.string.rubles);
        cardBalance.setText(balanceString);

        switch (currentCard.getType()) {
            case 1:
                cardType.setImageResource(R.drawable.visa_card);
                break;

            case 2:
                cardType.setImageResource(R.drawable.mastercard_card);
                break;

            case 3:
                cardType.setImageResource(R.drawable.maestro_card);
                break;

            case 4:
                cardType.setImageResource(R.drawable.mir_card);
                break;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.profile_top_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(User.getCurrentUser().getFirstName() + " "
                + User.getCurrentUser().getMiddleName());
    }

    private void initBottomToolbar() {
        ImageButton homeButton = findViewById(R.id.action_home);
        homeButton.setBackground(getResources().getDrawable(R.drawable.icon_home_active,null));

        TextView homeText = findViewById(R.id.text_home);
        homeText.setTextColor(getResources().getColor(R.color.colorAccent, null));
    }

    public void onPutMoneyClick(View view) {
        //put money on card
    }

    public void onTransferClick(View view) {
        //transfer money to another card
    }

    public void onCardHistoryClick(View view) {
        //startActivity(new Intent(CardActivity.this, CardHistoryActivity.class));
    }

    public void onCardBlockClick(View view) {
        //block card
    }

    public void onCardRenameClick(View view) {
        ChangeCardNameDialogFragment dialogFragment = new ChangeCardNameDialogFragment();
        dialogFragment.setCard(currentCard);
        dialogFragment.setListener(new DataListener() {
            @Override
            public void onGetData(boolean isValid, String info) {
                initCardView();
                UserProfileActivity.refreshData();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "cardRename");
    }

    public void onHomeButtonClick(View view) {
        super.finish();
    }

    public void onPaymentsButtonClick(View view) {
        //startActivity(new Intent(CardActivity.this, PaymentsActivity.class));
    }

    public void onHistoryButtonClick(View view) {
        //startActivity(new Intent(CardActivity.this, HistoryActivity.class));
    }

    public void onChatButtonClick(View view) {
        //startActivity(new Intent(CardActivity.this, ChatActivity.class));
    }
}
