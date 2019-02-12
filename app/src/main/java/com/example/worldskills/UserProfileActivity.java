package com.example.worldskills;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class UserProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initToolbar();
        initBottomToolbar();
        initRV();
    }

    private void initRV() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        //dummies:

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("debit", "1234567843218765", "visa",50000));
        cards.add(new Card("credit", "4815381033737514", "mir",10.5));
        cards.add(new Card("credit", "1010241599332515", "maestro",0));
        sectionAdapter.addSection(new CardSection(cards));

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("current", "1324675890", 1000.15));
        accounts.add(new Account("current", "1324675890", 850300));
        sectionAdapter.addSection(new AccountSection(accounts));

        List<Credit> credits = new ArrayList<>();
        credits.add(new Credit("cash", "10.02.2019", 150000));
        credits.add(new Credit("mortgage", "21.03.2019", 10230));
        sectionAdapter.addSection(new CreditSection(credits));

        recyclerView = findViewById(R.id.profile_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(sectionAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        decoration.setDrawable(getBaseContext().getResources().getDrawable(R.drawable.profile_recycler_decoration, null));
        recyclerView.addItemDecoration(decoration);
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

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.user_name); //temporary, set user name dynamically later!!!
    }

    private void initBottomToolbar() {
        ImageButton homeButton = findViewById(R.id.action_home);
        ImageButton paymentsButton = findViewById(R.id.action_payments);
        ImageButton historyButton = findViewById(R.id.action_history);
        ImageButton chatButton = findViewById(R.id.action_chat);
        homeButton.setBackground(getResources().getDrawable(R.drawable.icon_home_active,null));
        paymentsButton.setBackground(getResources().getDrawable(R.drawable.icon_payments_inactive,null));
        historyButton.setBackground(getResources().getDrawable(R.drawable.icon_history_inactive,null));
        chatButton.setBackground(getResources().getDrawable(R.drawable.icon_chat_inactive,null));

        TextView homeText = findViewById(R.id.text_home);
        TextView paymentsText = findViewById(R.id.text_payments);
        TextView historyText = findViewById(R.id.text_history);
        TextView chatText = findViewById(R.id.text_dialogues);
        homeText.setTextColor(getResources().getColor(R.color.colorAccent, null));
        paymentsText.setTextColor(Color.parseColor("#FFFFFF"));
        historyText.setTextColor(Color.parseColor("#FFFFFF"));
        chatText.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onHomeButtonClick(View view) { }

    public void onPaymentsButtonClick(View view) {
        //startActivity(new Intent(UserProfileActivity.this, PaymentsActivity.class));
    }

    public void onHistoryButtonClick(View view) {
        //startActivity(new Intent(UserProfileActivity.this, HistoryActivity.class));
    }

    public void onChatButtonClick(View view) {
        //startActivity(new Intent(UserProfileActivity.this, ChatActivity.class));
    }
}
