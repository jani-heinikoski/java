package com.jhprog.sqlscratch;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.jhprog.sqlscratch.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SQLiteDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new SQLiteDBHelper(this);

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        System.out.println("LOGGER: " + database.getPath());
        database.close();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initButtons();

    }

    private void initButtons() {

        binding.executeQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeSQL(binding.editQuery.getText().toString());
            }
        });

    }

    private void executeSQL(String query) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor;

        if ((cursor = database.rawQuery(query, null)) != null) {
            String retVal;
            if (cursor.moveToNext()) {
                if ((retVal = cursor.getString(1)) != null) {
                    binding.resultTextview.setText(String.format(Locale.getDefault(), "RESULT: %s", retVal));
                }
            }
            cursor.close();
        }
        database.close();
    }

}
