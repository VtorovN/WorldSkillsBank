package com.example.worldskills;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

        recyclerView = (RecyclerView) findViewById(R.id.profile_recycler_view);
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
    public boolean onOptionsItemSelected(MenuItem item) { //Exit button pressed, delete login token!!!
        if(item.getItemId() == R.id.action_exit) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.user_name); //temporary, set user name dynamically later!!!
    }
}
