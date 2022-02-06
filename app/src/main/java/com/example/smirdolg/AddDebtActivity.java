package com.example.smirdolg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDebtActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_debt);

        dbHelper = new DBHelper(this);

        com.google.android.material.floatingactionbutton.FloatingActionButton confirmDebt = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.confirmDebt);
        confirmDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = (EditText) findViewById(R.id.debtName);
                String debtName = editName.getText().toString().trim();
                if (debtName.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Назовите долг", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    EditText editSum = (EditText) findViewById(R.id.debtSum);
                    String debtSumText = editSum.getText().toString().trim();
                    int debtSum = 0;
                    if (!debtSumText.equals("")) {
                        debtSum = Integer.parseInt(debtSumText);
                    }
                    Date dateNow = new Date();
                    SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
                    Log.d("MYDEBUG", formatForDateNow.format(dateNow));
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_NAME, debtName);
                    contentValues.put(DBHelper.KEY_SUM, debtSum);
                    contentValues.put(DBHelper.KEY_DATE, formatForDateNow.format(dateNow));
                    database.insert(DBHelper.TABLE_DEBTS, null, contentValues);
                    dbHelper.close();
                    finish();
                }
            }
        });
    }
}