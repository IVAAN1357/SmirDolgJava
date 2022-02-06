package com.example.smirdolg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    LinearLayout debts;
    TextView allDebt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        com.google.android.material.floatingactionbutton.FloatingActionButton addDebt = findViewById(R.id.addDebt);
        addDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddDebtActivity.class);
                view.getContext().startActivity(intent);
            }

        });


    }


    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View.OnClickListener oMyButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView cardView = (CardView) view;
                ConstraintLayout constraintlayout = (ConstraintLayout) cardView.getChildAt(0);
                TextView id = (TextView) constraintlayout.getChildAt(3);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
//                database.execSQL("DELETE FROM " + dbHelper.TABLE_DEBTS + " WHERE _id = " + id.getText());
                Log.d("MYDEBUG", id.getText().toString());
            }
        };
        allDebt = findViewById(R.id.allDebt);
        int sumOfDebt = 0;
        debts = findViewById(R.id.debts);
        debts.removeAllViews();
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_DEBTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int sumIndex = cursor.getColumnIndex(DBHelper.KEY_SUM);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            do {
                TextView textView = new TextView(this);
                sumOfDebt += cursor.getInt(sumIndex);

                CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.debt, null);
                ConstraintLayout constraintlayout = (ConstraintLayout) cardView.getChildAt(0);

                Resources r = this.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8,
                        r.getDisplayMetrics()
                );
                CardView.LayoutParams layoutParams = new CardView.LayoutParams
                        (CardView.LayoutParams.MATCH_PARENT , CardView.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(px, px, px, px);
                cardView.setLayoutParams(layoutParams);

                TextView name = (TextView) constraintlayout.getChildAt(0);
                String nameText = cursor.getInt(sumIndex) + " ₽";
                name.setText(nameText);
                TextView sum = (TextView) constraintlayout.getChildAt(1);
                sum.setText(cursor.getString(nameIndex));
                TextView date = (TextView) constraintlayout.getChildAt(2);
                date.setText(cursor.getString(dateIndex));
                TextView id = (TextView) constraintlayout.getChildAt(3);
                id.setText(String.valueOf(cursor.getInt(idIndex)));
                debts.addView(cardView);
                cardView.setOnClickListener(oMyButton);
                //textView.setText(String.valueOf(cursor.getInt(idIndex)) + " " + cursor.getString(nameIndex) + " " + String.valueOf(cursor.getInt(sumIndex)));
                //debts.addView(textView);
            } while (cursor.moveToNext());
            cursor.close();
        }
        String allDebtText = "Общий долг" + ": " + sumOfDebt;
        allDebt.setText(allDebtText);
        dbHelper.close();
    }
}