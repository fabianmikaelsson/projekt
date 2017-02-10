package com.example.fabianmikaelsson.easymoney.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fabianmikaelsson.easymoney.Model.DBAdapter;
import com.example.fabianmikaelsson.easymoney.Model.Account;
import com.example.fabianmikaelsson.easymoney.Model.Bookkeeper;
import com.example.fabianmikaelsson.easymoney.Model.EntryCategory;
import com.example.fabianmikaelsson.easymoney.Model.TextItemPair;
import com.example.fabianmikaelsson.easymoney.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity to add new entries.
 */
public class NewEntryActivity extends AppCompatActivity implements Serializable {

    public static final String TAG = "NewEntryActivity";

    private DBAdapter db;
    private Bookkeeper bookkeeper;

    private RadioGroup radioGroup;
    private EditText dateEditText;
    private EditText descriptionEditText;
    private Spinner typeSpinner;
    private Spinner accountSpinner;
    private EditText totalSumEditText;
    private Button addEntryButton;

    private int radioButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        openDb();
        initObjects();

        getAccounts();
        getEntryCategory();

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry();

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                radioButtonId = checkedId-2131427422;
                getEntryCategory();
            }
        });
    }

    // Setup view.
    private void initObjects() {
        bookkeeper = Bookkeeper.getInstance();
        radioGroup = (RadioGroup)findViewById(R.id.InOrOutRadioGroup);
        dateEditText = (EditText)findViewById(R.id.DateEditText);
        descriptionEditText = (EditText)findViewById(R.id.DescriptionEditText);
        typeSpinner = (Spinner)findViewById(R.id.TypeOfEntrySpinner);
        accountSpinner = (Spinner)findViewById(R.id.AccountSpinner);
        totalSumEditText = (EditText)findViewById(R.id.SumEditText);
        addEntryButton = (Button)findViewById(R.id.addEntrybutton1);
    }

    // Add entry and pass values to DBAdapter.
    private void addEntry() {
        String date = dateEditText.getText().toString();
        String account = accountSpinner.getSelectedItem().toString();
        String category = typeSpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString();
        int sum = 0;
        try { sum = Integer.parseInt(totalSumEditText.getText().toString()); }
        catch(NumberFormatException nfe){
            sum=0;
        }

        db.insertRow(account, category, sum, 1, date, description);

        Toast.makeText(NewEntryActivity.this, "Händelsen lades till", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(NewEntryActivity.this, NewEntryActivity.class));
    }

    // Setup accounts in spinner.
    private void getAccounts() {
        List<Account> accounts = bookkeeper.getAccounts();
        List<TextItemPair<Account>> dataSource = new ArrayList<TextItemPair<Account>>();

        for(Account a : accounts) {
            dataSource.add(new TextItemPair<Account>(a.getName(), a));
        }

        ArrayAdapter<TextItemPair<Account>> adapterAccount = new ArrayAdapter<TextItemPair<Account>>(this, R.layout.support_simple_spinner_dropdown_item, dataSource);
        accountSpinner.setAdapter(adapterAccount);
    }

    // Setup categories in spinner.
    private void getEntryCategory() {
        EntryCategory entryCategory = new EntryCategory(radioButtonId == 0);
        List<String> category = entryCategory.categoriesForIncomeOrExpense();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, category);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDb();
    }

    // Open database.
    private void openDb() {
        db = new DBAdapter(this);
        db.open();
    }

    // Close database.
    private void closeDb() {
        db.close();
    }

}
