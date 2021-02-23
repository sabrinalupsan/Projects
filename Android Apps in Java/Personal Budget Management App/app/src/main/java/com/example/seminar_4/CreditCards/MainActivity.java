package com.example.seminar_4.CreditCards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminar_4.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "something";
    private TextView date_text;
    private TextView tv;
    private String sIBAN, sLimit;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_credit_cards);

        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            sIBAN = extras.getString("paramIBAN");
            if (extras.getString("paramLimit") != null) {
                sLimit = extras.getString("paramLimit");
            }
        }

    }


    public void callSecondActivity(View view)
    {
        try {
            TextView value2 = (TextView) findViewById(R.id.et_actualAmount);
            RadioGroup radiogroup = findViewById(R.id.rg_typeSum);

            ArrayList<String> array = new ArrayList<String>();
            CheckBox c1 = (CheckBox) findViewById(R.id.checkBox);
            CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
            CheckBox c3 = (CheckBox) findViewById(R.id.checkBox3);

            if (c1.isChecked() == true)
                array.add(c1.getText().toString());
            if (c2.isChecked() == true)
                array.add(c2.getText().toString());
            if (c3.isChecked() == true)
                array.add(c3.getText().toString());
            int x = Integer.parseInt(value2.getText().toString());
            int y = Integer.parseInt(sLimit);
            System.out.println(x);
            System.out.println(y);
            Log.d(TAG, "X ESTE: " + x);
            Log.d(TAG, "Y ESTE: " + y);
            date_text = (TextView) findViewById(R.id.tv_DatePlace);
            RadioButton r = (RadioButton) findViewById(radiogroup.getCheckedRadioButtonId());
            String str = r.getText().toString();
            Log.d(TAG, "STRINGUL ESTE: " + str);


            if (TextUtils.isEmpty(value2.getText()))
                value2.setError("Actual Amount is required!");
            else if (radiogroup.getCheckedRadioButtonId() == -1)
                Toast.makeText(this, "A type of sum is required", Toast.LENGTH_SHORT).show();
            else if (c1.isChecked() == false && c2.isChecked() == false && c3.isChecked() == false)
                Toast.makeText(this, "A category is required", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(date_text.getText()))
                date_text.setError("A date is required!");
            else if (Integer.parseInt(value2.getText().toString()) > Integer.parseInt(sLimit) &&
                    ((RadioButton) findViewById(radiogroup.getCheckedRadioButtonId())).getText().toString().compareTo("Expense")==0) {
                Toast.makeText(this, "The transaction cannot be completed", Toast.LENGTH_SHORT).show();
                value2.setError("The Actual Amount is higher than the Account Limit: " + sLimit);
            } else {
                String selectedButton = ((RadioButton) findViewById(radiogroup.getCheckedRadioButtonId())).getText().toString();
                Intent intent = new Intent(this, CreditCards_2.class);
                Bundle bundle = new Bundle();

                // startActivity(intent);
                bundle.putString("param2", sIBAN);


                bundle.putString("param3", value2.getText().toString());


                bundle.putString("param4", selectedButton);


                bundle.putStringArrayList("param5", array);


                bundle.putString("param9", date_text.getText().toString());

                intent.putExtras(bundle);
                //intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);

                startActivityForResult(intent, 100);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void CallCalendarActivity(View view)
    {
        Intent intent = new Intent(this, calendar_activity.class);
        Bundle bundle = new Bundle();

        intent.putExtras(bundle);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200)
        {
            if(data != null)
            {
                Bundle extras = data.getExtras();
                String param8 = extras.getString("param8");
                tv = (TextView)findViewById(R.id.tv_DatePlace);
                tv.setText(param8);
            }
        }
    }
}