package com.example.worldskills.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.worldskills.DialogFragment.SuccessInfoDialogFragment;
import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.Listener.HistoryListener;
import com.example.worldskills.Model.Operation;
import com.example.worldskills.Model.User;
import com.example.worldskills.R;
import com.example.worldskills.RVAdapter.OperationsAdapter;
import com.example.worldskills.Repository.RepositoryProfile;
import com.example.worldskills.Utility.SuccessBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class OperationsHistoryActivity extends AppCompatActivity {
    private Operation[] operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar progressBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyleSmall);
        setContentView(progressBar);
        RepositoryProfile.getCardOperationsHistory(CardActivity.getCurrentCard().getNumber(),
                new HistoryListener() {
                    @Override
                    public void onGetHistory(Operation[] operations, boolean isValid, String info) {
                        if (isValid) {
                            setContentView(R.layout.activity_operations_history);
                            OperationsHistoryActivity.this.operations = operations;
                            initBottomToolbar();
                            initToolbar();
                            initRV();
                        } else {
                            SuccessInfoDialogFragment successDialogFragment
                                    = new SuccessInfoDialogFragment();
                            successDialogFragment.setArguments(SuccessBundle
                                    .assemble(false, info));
                            successDialogFragment.show(getSupportFragmentManager(),
                                    "infoDialog");
                            OperationsHistoryActivity.super.finish();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_profile_features):
                startActivity(new Intent(this, ProfileFeaturesActivity.class));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initRV() {
        RecyclerView operationsRecyclerView = findViewById(R.id.operations_history_recycler_view);
        operationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OperationsAdapter operationsAdapter = new OperationsAdapter();
        operationsRecyclerView.setAdapter(operationsAdapter);
        List<Operation> operationList = Arrays.asList(operations);
        operationsAdapter.setItems(operationList);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        operationsRecyclerView.addItemDecoration(decoration);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.profile_top_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(User.getCurrentUser().getFirstName() + " "
                + User.getCurrentUser().getMiddleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon()
                .setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
    }

    private void initBottomToolbar() {
        ImageButton homeButton = findViewById(R.id.action_home);
        homeButton.setBackground(getResources().getDrawable(R.drawable.icon_home_active,null));

        TextView homeText = findViewById(R.id.text_home);
        homeText.setTextColor(getResources().getColor(R.color.colorAccent, null));
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
