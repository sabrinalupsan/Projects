package com.example.seminar_4.CreditCards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.seminar_4.MainMenuActivity;
import com.example.seminar_4.R;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class BankDetails extends AppCompatActivity {
    private static final String TAG = BankDetails.class.getSimpleName();
    private Spinner spn;
    private Button btn_cancel;
    private Button btn_ok, btn_delete;
    private ListView listView;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        listView = findViewById(R.id.lv_banks);
        btn_delete = findViewById(R.id.btn_deleteAccount);

        final AccountDBHelper accountDBHelper = new AccountDBHelper(this);


        cursor = accountDBHelper.getDataCursorAccount();
        final SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"_id", "Bank"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(cursorAdapter);



        spn = findViewById(R.id.spn_bankName);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_bank,
                android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);


        btn_cancel = findViewById(R.id.btn_cancelBank);
        btn_ok = findViewById(R.id.btn_okBank);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });



        final Context context = this;
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AccountDBHelper accountDBHelper = new AccountDBHelper(context);
                EditText etIBAN = findViewById(R.id.et_IBAN);
                EditText etLimit = findViewById(R.id.et_limit);
                spn = findViewById(R.id.spn_bankName);

                if( TextUtils.isEmpty(etIBAN.getText())) {
                    etIBAN.setError("IBAN is required!");
                }
                else if(TextUtils.isEmpty(etLimit.getText())){
                    etLimit.setError("Limit is required!");
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("paramIBAN", etIBAN.getText().toString());
                    bundle.putString("paramLimit", etLimit.getText().toString());
                    intent.putExtras(bundle);

                    accountDBHelper.insertSampleAccount(etIBAN.getText().toString(), spn.getSelectedItem().toString(), etLimit.getText().toString());
                    startActivity(intent);
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();

                TextView txtValue = view.findViewById(android.R.id.text1);
                String iban = txtValue.getText().toString();
                final AccountDBHelper accountDBHelper = new AccountDBHelper(context);
                String limit = accountDBHelper.getLimit(iban);


                bundle.putString("paramIBAN", iban);
                bundle.putString("paramLimit", limit);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                TextView txtValue = view.findViewById(android.R.id.text1);
                String iban = txtValue.getText().toString();
                accountDBHelper.deleteItemAccount(iban);
                cursorAdapter.swapCursor(accountDBHelper.getDataCursorAccount());
                cursorAdapter.notifyDataSetChanged();
                return true;
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountDBHelper.deleteAllAccount();
                cursorAdapter.swapCursor(accountDBHelper.getDataCursorAccount());
                cursorAdapter.notifyDataSetChanged();
            }
        });
    }


    public String GetTextFromList()
    {
        String value = "";
        final AccountDBHelper accountDBHelper = new AccountDBHelper(this);
        Cursor cursor = accountDBHelper.getDataCursorAccount();

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String iban = cursor.getString(cursor.getColumnIndex("_id"));
                    String amount = cursor.getString(cursor.getColumnIndex("AmountLimit"));
                    String bank = cursor.getString(cursor.getColumnIndex("Bank"));

                    value = value + "IBAN: " + iban + ", BANK: " + bank + ", LIMIT: " + amount + "\n";
                } while (cursor.moveToNext());
            }
        }
        return value;
    }


    public void SaveFileCSV(View view){
        writeToFile(GetTextFromList());
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Account.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }
}