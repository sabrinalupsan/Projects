package com.example.seminar_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.seminar_4.Cash.Cash;
import com.example.seminar_4.Cash.DownloadAsync;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.example.seminar_4.CashActivity.TRANSACTIONS;


public class MainActivityCash extends AppCompatActivity {

    private Button OKbutton;
    private Button Cancelbutton;
    private static final int REQUEST_CODE = 1;
    private static final int resultCode = 0;
    private RadioGroup type;
    private SeekBar amount;
    private RatingBar rating;
    private EditText date;
    private Switch fromSavings;
    private ToggleButton planned;
    private ArrayList<Cash> transactions = new ArrayList<>();
    private Intent intent;
    private Bundle bundle = new Bundle();
    private Cash cash;
    private TextView tvSeekBar;
    private ImageView img;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cash);

        InitializeAllComponents();

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        int maxAmount = preferences.getInt("seekBar", 100);
        amount.setMax(maxAmount);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Alpha-ul este: "+String.valueOf(img.getAlpha()));

                if(img.getAlpha()!=0.0)
                    img.animate().alpha(0f).setDuration(10);
                else
                    img.animate().alpha(100f).setDuration(10);
            }

        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setText(" ");
            }
        });

        OKbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), CashActivity.class);
                String theType = null;
                try {
                    RadioButton btn = (RadioButton)findViewById(type.getCheckedRadioButtonId());
                    theType = btn.getText().toString();
                } catch (NullPointerException ex) {
                    //messagebox?
                }
                Log.d(TAG, "theType is "+theType);

                if (theType == null)
                    Toast.makeText(getApplicationContext(), "Please choose a type of transaction!", Toast.LENGTH_LONG).show();
                else {
                    Cash cash = getInfoFromControls();
                    bundle.putParcelable("value", cash);
                    bundle.putParcelableArrayList("totalTransactions", transactions);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        Cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(0, intent);
                finish();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setText("");
            }
        });
        amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvSeekBar.setText("Selected amount: "+String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvSeekBar = findViewById(R.id.textView4);
                tvSeekBar.setText("Selected amount: "+String.valueOf(seekBar.getProgress()));
            }
        });

    }

    @SuppressLint({"ResourceType", "WrongViewCast"})
    protected void InitializeAllComponents()
    {
        OKbutton = findViewById(R.id.OKbtn);
        Cancelbutton = findViewById(R.id.btnCancel);
        type = findViewById(R.id.radioGroup);
        amount = findViewById(R.id.seekBar2);
        rating = findViewById(R.id.ratingBar2);
        fromSavings = findViewById(R.id.switch1);
        date = findViewById(R.id.editTextDate2);
        planned = findViewById(R.id.checkBox);
        tvSeekBar = findViewById(R.id.textView4);
        img = findViewById(R.id.imageView2);
        img.animate().alpha(100f).setDuration(10000);
    }

    protected void clearAllComponents()
    {
        date.setText("");
        planned.setChecked(false);
        fromSavings.setChecked(false);
        rating.setRating(0);
        amount.setProgress(0);
        type.clearCheck();
        tvSeekBar.setText("Selected amount: ");
    }

    @SuppressLint("WrongConstant")
    protected Cash getInfoFromControls() {
        String theType = null;
        try {
            RadioButton btn = (RadioButton) findViewById(type.getCheckedRadioButtonId());
            theType = btn.getText().toString();
        } catch (NullPointerException ex) {
            //messagebox?
        }
        if (theType == null)
            Toast.makeText(this, "Please choose a type of transaction!", 5000);
            float theAmount = amount.getProgress();

            if (theAmount == 0.0 && (theType.compareTo("Income") == 0 || theType.compareTo("Expense") == 0)) //here check if the type if income/expense
            {
                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(this);
                if (theType.compareTo("Income") == 0) {
                    String x = preferences.getString("incomeAmount", "");
                    amount.setProgress(Integer.parseInt(x));
                } else {
                    String x = preferences.getString("expenseAmount", "");
                    amount.setProgress(Integer.parseInt(x));
                }
                theAmount = amount.getProgress();
            }

            Date theDate = null;
            try {
                theDate = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(date.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                if (theDate == null) {
                    Date newDate = new Date();
                    theDate = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(newDate.toString());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            float theRating = rating.getRating();

            boolean isPlanned;
            isPlanned = planned.isChecked();
            String plannedValue;
            if (isPlanned == true)
                plannedValue = "Yes";
            else
                plannedValue = "No";

            boolean isFromSavings;
            isFromSavings = fromSavings.isChecked();
            String fromSavings = null;
            if (isFromSavings == true)
                fromSavings = "Yes";
            else
                fromSavings = "No";
            return new Cash(theType, theAmount, theDate, theRating, plannedValue, fromSavings);
    }

    @Override
    protected void onActivityResult(int REQUEST_CODE, int resultCode, @Nullable Intent data) {
        super.onActivityResult(REQUEST_CODE, resultCode, data);
        if(resultCode == 2)
        {
            assert data != null;
            Bundle bundle = data.getExtras();
            ArrayList<Cash> transactions2 = bundle.getParcelableArrayList(TRANSACTIONS);
            for(int i=0; i<transactions2.size(); i++)
                transactions.add(transactions2.get(i));
            clearAllComponents();
            makeFILE();

        }
        else
            if(resultCode == 1)
                clearAllComponents();
    }

    public File makeFILE()
    {
        File file = new File(getApplicationContext().getFilesDir(), "CASH_FILE2.json");
        JSONObject obj = new JSONObject();
        try {
            FileWriter fileWriter =new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            JSONObject jsonObject = new JSONObject();
            JSONArray joCollectionOfIncomes = new JSONArray();
            JSONArray joCollectionOfExpenses = new JSONArray();
            JSONArray joCollectionOfOthers = new JSONArray();

            JSONArray jaIncome = new JSONArray();
            JSONArray jaExpense = new JSONArray();
            JSONArray jaOther = new JSONArray();

            for (int i = 0; i < transactions.size(); i++) {
                Cash c = transactions.get(i);
                JSONObject joBook = new JSONObject();
                JSONObject joCharacteristics = new JSONObject();
                joBook.put("type", c.getType());
                joBook.put("amount", c.getCashAmount());
                joCharacteristics.put("date", c.getDate());
                joCharacteristics.put("rating", c.getRating());
                joCharacteristics.put("from savings", c.isFromSavings());
                joBook.put("details", joCharacteristics);
                if(c.getType().compareTo("Income")==0)
                    jaIncome.put(joBook);
                else
                    if(c.getType().compareTo("Expense")==0)
                        jaExpense.put(joBook);
                    else
                        jaOther.put(joBook);
                String userString = joBook.toString();
                Log.d(TAG, "Content: "+userString);
            }
            //Log.d(TAG, "Content: "+jaTransactions);
            for(int i=0; i<jaIncome.length(); i++)
            {
                joCollectionOfIncomes.put(jaIncome.getJSONObject(i));
            }
            for(int i=0; i<jaExpense.length(); i++)
            {
                joCollectionOfExpenses.put(jaExpense.getJSONObject(i));
            }
            for(int i=0; i<jaOther.length(); i++)
            {
                joCollectionOfOthers.put(jaOther.getJSONObject(i));
            }
            jsonObject.put("My Income", joCollectionOfIncomes);
            jsonObject.put("My Expenses", joCollectionOfExpenses);
            jsonObject.put("Others", joCollectionOfOthers);
            obj = jsonObject;
            String userString = obj.toString();
            bufferedWriter.write(userString);

            DownloadAsync downloadAsync = new DownloadAsync();
            downloadAsync.execute("https://ptsv2.com/t/j5cq1-1606468043/post", userString);

            //Log.d(TAG, "Content: "+jsonObject);
            //joCollectionOfTransactions.put(jaTransactions)
            bufferedWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}