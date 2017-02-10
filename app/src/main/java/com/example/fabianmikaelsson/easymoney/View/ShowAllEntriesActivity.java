package com.example.fabianmikaelsson.easymoney.View;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.fabianmikaelsson.easymoney.Model.DBAdapter;
import com.example.fabianmikaelsson.easymoney.R;

/**
 * Activity to show all entries from DBAdapter in item_layout.
 */
public class ShowAllEntriesActivity extends AppCompatActivity {

    DBAdapter db;

    Button buttonClearAllEntries;
    ListView showAllEntriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_entries);

        openDb();
        initObjects();

        populateListViewFromDB();
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

    // View database in item_layout.
    private void populateListViewFromDB()
    {
        Cursor cursor = db.getAllRows();

        startManagingCursor(cursor);

        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_ACCOUNT, DBAdapter.KEY_CATEGORY, DBAdapter.KEY_SUM, DBAdapter.KEY_DATE, DBAdapter.KEY_DESCRIPTION};


        int[] toViewIDs = new int[]
                {R.id.textViewAccount, R.id.textViewType, R.id.textViewTotalSum, R.id.textViewDate, R.id.textViewDescription};

        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.item_layout,
                        cursor,
                        fromFieldNames,
                        toViewIDs
                );

        showAllEntriesListView.setAdapter(cursorAdapter);
    }

    // Setup view.
    private void initObjects()
    {
        showAllEntriesListView = (ListView)findViewById(R.id.listViewShowAllEntries);
        buttonClearAllEntries = (Button)findViewById(R.id.buttonClearAllEntries);

        buttonClearAllEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert("Är du säker?");
            }
        });
    }

    // Show alert with message from parameter.
    private void showAlert(String errorMessage) {
        android.app.AlertDialog.Builder alertNr = new android.app.AlertDialog.Builder(this);
        alertNr.setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteAll();
                        populateListViewFromDB();
                    }
                })
                .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        alertNr.show();
    }
}
