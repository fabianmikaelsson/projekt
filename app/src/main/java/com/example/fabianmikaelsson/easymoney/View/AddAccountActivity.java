package com.example.fabianmikaelsson.easymoney.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fabianmikaelsson.easymoney.Model.Account;
import com.example.fabianmikaelsson.easymoney.Model.Bookkeeper;
import com.example.fabianmikaelsson.easymoney.Model.TextItemPair;
import com.example.fabianmikaelsson.easymoney.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to add accounts.
 */
public class AddAccountActivity extends AppCompatActivity {

    private Button addNewAccountButton;
    private Button removeSelectedAccountButton;

    private EditText accountNameEditText;
    private EditText accountNumberEditText;

    private Spinner removeAccountSpinner;

    private Bookkeeper mBookkeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        mBookkeeper = Bookkeeper.getInstance();

        setupView();
        getAccounts();
    }

    // Setup the view.
    public void setupView() {
        addNewAccountButton = (Button)findViewById(R.id.addNewAccountButton);
        removeSelectedAccountButton = (Button)findViewById(R.id.removeAccountButton);
        accountNameEditText = (EditText)findViewById(R.id.NameAccountEditText);
        accountNumberEditText = (EditText)findViewById(R.id.AccountNrEditText);
        removeAccountSpinner = (Spinner)findViewById(R.id.removeAccountSpinner);

        onClickForButton();
    }

    public void onClickForButton() {
        addNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberString = accountNumberEditText.getText().toString();
                int number = Integer.parseInt(numberString);

                mBookkeeper.addAccount(accountNameEditText.getText().toString(), number);

                startActivity(new Intent(AddAccountActivity.this, MenuActivity.class));
            }
        });

        removeSelectedAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedAccount();
            }
        });
    }

    // Return list of account to spinner with adapter.
    private void getAccounts() {
        List<Account> accounts = mBookkeeper.getAccounts();
        List<TextItemPair<Account>> dataSource = new ArrayList<TextItemPair<Account>>();

        for(Account a : accounts) {
            dataSource.add(new TextItemPair<Account>(a.getName(), a));
        }

        ArrayAdapter<TextItemPair<Account>> adapterAccount = new ArrayAdapter<TextItemPair<Account>>(this, R.layout.support_simple_spinner_dropdown_item, dataSource);
        removeAccountSpinner.setAdapter(adapterAccount);
    }

    // Removes selected acount in spinner.
    private void removeSelectedAccount() {
        int index = removeAccountSpinner.getSelectedItemPosition();
        mBookkeeper.removeAccount(index);
        getAccounts();
    }



}
