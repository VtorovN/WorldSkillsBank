package com.example.worldskills.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.worldskills.Model.Account;
import com.example.worldskills.RVAdapter.SectionedRV.AccountSection;
import com.example.worldskills.Model.Card;
import com.example.worldskills.RVAdapter.SectionedRV.CardSection;
import com.example.worldskills.Model.Credit;
import com.example.worldskills.RVAdapter.SectionedRV.CreditSection;
import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.R;
import com.example.worldskills.Repository.RepositoryProfile;
import com.example.worldskills.Utility.SuccessBundle;
import com.example.worldskills.DialogFragment.SuccessInfoDialogFragment;
import com.example.worldskills.Model.User;

import java.util.Arrays;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class UserProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private User user;
    private static UserProfileActivity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar progressBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyleSmall);
        setContentView(progressBar);
        RepositoryProfile.getUserInfo(new DataListener() {
            @Override
            public void onGetData(boolean isValid, String info) {
                if (isValid) {
                    currentActivity = UserProfileActivity.this;
                    user = User.getCurrentUser();

                    setContentView(R.layout.activity_user_profile);

                    initToolbar();
                    initBottomToolbar();
                    initRV();
                } else {
                    SuccessInfoDialogFragment dialogFragment = new SuccessInfoDialogFragment();
                    dialogFragment.setArguments(SuccessBundle.assemble(false, info));
                }
            }
        });
    }

    private void initRV() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        List<Card> cards = Arrays.asList(user.getCards());
        sectionAdapter.addSection(new CardSection(cards));

        List<Account> accounts = Arrays.asList(user.getAccounts());
        sectionAdapter.addSection(new AccountSection(accounts));

        List<Credit> credits = Arrays.asList(user.getCredits());
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
        Toolbar toolbar = findViewById(R.id.profile_top_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(user.getFirstName() + " " + user.getMiddleName());
    }

    private void initBottomToolbar() {
        ImageButton homeButton = findViewById(R.id.action_home);
        homeButton.setBackground(getResources().getDrawable(R.drawable.icon_home_active,null));

        TextView homeText = findViewById(R.id.text_home);
        homeText.setTextColor(getResources().getColor(R.color.colorAccent, null));
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

    public static void refreshData() {
        currentActivity.initRV();
        currentActivity.initToolbar();
    }
}
