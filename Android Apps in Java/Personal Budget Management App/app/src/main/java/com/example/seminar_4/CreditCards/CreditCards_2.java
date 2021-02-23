package com.example.seminar_4.CreditCards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.seminar_4.R;


import java.util.ArrayList;

public class CreditCards_2 extends AppCompatActivity {

    private String iban;
    private TextView amount;
    private TextView typeSum;
    private TextView category;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_cards_2);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String param2 = extras.getString("param2");
        String param3 = extras.getString("param3");
        String param4 = extras.getString("param4");
        ArrayList<String> param5 = extras.getStringArrayList("param5");
        String param9 = extras.getString("param9");




        iban = param2;
        amount = (TextView)findViewById(R.id.tv_amountCorrect);
        amount.setText(param3);

        typeSum = (TextView)findViewById(R.id.tv_typeSumCorrect);
        typeSum.setText(param4);

        category = (TextView)findViewById(R.id.tv_categoryCorrect);

        String value5 = "";
        for(int i=0;i<param5.size();i++)
            if(param5.get(i) != null)
                value5 += param5.get(i) + "\n";

        category.setText(value5);

        date = (TextView)findViewById(R.id.tv_dateCorrect);
        date.setText(param9);



        }


    public void returnToMainActivity(View view)
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void CallListActivity(View view)
    {


        Intent intent = new Intent(this, RegistrationList.class);
        Bundle extras = new Bundle();
        CreditEntry creditEntry = CreateEntry();

        final AccountDBHelper accountDBHelper = new AccountDBHelper(this);
        accountDBHelper.insertSampleTransaction(creditEntry.getIban(), creditEntry.getAmount(), creditEntry.getType(),
                creditEntry.getCategory(), creditEntry.getDate());


        extras.putString("paramIBAN", creditEntry.getIban());
        extras.putString("paramType", creditEntry.getType());
        extras.putString("paramAmount", creditEntry.getAmount());
        intent.putExtras(extras);
        startActivity(intent);
    }


    public CreditEntry CreateEntry(){
        CreditEntry creditEntry = new CreditEntry(iban, amount.getText().toString(),
                typeSum.getText().toString(), category.getText().toString(), date.getText().toString());
        return creditEntry;
    }


}