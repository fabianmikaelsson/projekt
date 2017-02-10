package com.example.fabianmikaelsson.easymoney.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fabianmikaelsson.easymoney.R;

public class MenuActivity extends AppCompatActivity {

    private Button newEntry;
    private Button showEntries;
    private Button addAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setUpButtons();

    }

    private void setUpButtons() {
        newEntry = (Button) findViewById(R.id.newEntryButton);
        showEntries = (Button) findViewById(R.id.showAllEntriesButton);
        addAccount = (Button) findViewById(R.id.addAccountButton);

        onClickListenersForButtons();
    }

    private void onClickListenersForButtons() {
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, NewEntryActivity.class));
            }
        });

        showEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ShowAllEntriesActivity.class));
            }
        });

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AddAccountActivity.class));
            }
        });


    }


}
