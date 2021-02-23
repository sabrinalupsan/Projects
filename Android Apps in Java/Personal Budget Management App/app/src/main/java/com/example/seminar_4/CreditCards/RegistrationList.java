package com.example.seminar_4.CreditCards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminar_4.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class RegistrationList extends AppCompatActivity {
    private static final String TAG = RegistrationList.class.getSimpleName();
    private TextView list;
    private String iban, amount, type;
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_list);




        list = findViewById(R.id.tv_transactions);
        btn_delete = findViewById(R.id.btn_deleteAllTransactions);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        try {
            iban = extras.getString("paramIBAN");
            type = extras.getString("paramType");
            amount = extras.getString("paramAmount");
        }catch(Exception e){
        }

        final AccountDBHelper accountDBHelper = new AccountDBHelper(this);

        for(int i=0;i<TransactionTable.TABLE_TODO.length();i++) {
            Cursor cursor = accountDBHelper.getDataCursorTransaction(i);
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();

                String iban = cursor.getString(cursor.getColumnIndex("IBAN"));
                String amount = cursor.getString(cursor.getColumnIndex("ActualAmount"));
                String type = cursor.getString(cursor.getColumnIndex("SumType"));
                String category = cursor.getString(cursor.getColumnIndex("Category"));
                String date = cursor.getString(cursor.getColumnIndex("Date"));
                String limit = cursor.getString(cursor.getColumnIndex("AmountLimit"));
                String bank = cursor.getString(cursor.getColumnIndex("Bank"));
                cursor.close();
                CreditEntry entry = new CreditEntry(iban, amount, type, category, date);

                list.setText(list.getText().toString() + "\n" + entry.toString() + ", Current Amount " + limit + ", Bank: " + bank);
            }
        }

        int value;
        try {
            if(type == "Income")
                value = (Integer.parseInt(accountDBHelper.getLimit(iban)) + Integer.parseInt(amount));
            else
                value = (Integer.parseInt(accountDBHelper.getLimit(iban)) - Integer.parseInt(amount));
            accountDBHelper.updateAccount(iban, value);
        }catch(Exception e){}


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountDBHelper.deleteAllTransactions();
                list.setText("");

            }
        });
    }

    public void returnToMainActivityFromList(View view) {
        Intent intent = new Intent(this, BankDetails.class);
        startActivity(intent);
    }

    public void SaveFile(View view){
        writeToFile(list.getText().toString());
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Transactions.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }


}