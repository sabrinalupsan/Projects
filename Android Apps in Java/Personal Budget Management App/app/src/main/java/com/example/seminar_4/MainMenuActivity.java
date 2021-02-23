package com.example.seminar_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminar_4.Cash.Cash;
import com.example.seminar_4.Cash.CashDBHelper;
import com.example.seminar_4.CreditCards.BankDetails;
import com.example.seminar_4.Wishlist.MainActivityWishlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;
//import static com.example.seminar_4.R.id.imageView;

public class MainMenuActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private Spinner spnMenu;
    private Button btnOK;
    private Button btnDelete;
    private Button btnSettings;
    private GridView gridView;
    private ArrayList<Cash> transactions = new ArrayList<Cash>();
    private CheckBox box;
    private ListView listView;
    private TextView tvStart;
    private TextView tvDesc;
    private WebView webView;
    //private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initializeComponents();

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        tvStart.setText("Welcome to CashApp, "+preferences.getString("signature", ""));

        webView = findViewById(R.id.webview);
        //webView.setBackgroundColor(this.getColor());
        webView.loadUrl("file:///android_asset/piggy.gif");

        //makeTransactions();
        //String s = makeJSONFile();

        //DownloadAsync downloadAsync = new DownloadAsync();
        //downloadAsync.execute("https://ptsv2.com/t/j5cq1-1606468043/post", s);

        //TypeAdapter adapter = new TypeAdapter(getApplicationContext(), transactions);
        //gridView.setAdapter(adapter);

        final CashDBHelper databaseHelper = new CashDBHelper(this);
        //databaseHelper.insertSample();


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spnChoice;
                spnChoice = spnMenu.getSelectedItem().toString();
                if(spnChoice.compareTo("Cash")==0)
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivityCash.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else
                    if(spnChoice.compareTo("Wishlist")==0)
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivityWishlist.class);
                        startActivityForResult(intent, REQUEST_CODE);

                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), BankDetails.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeTransactions()
    {
        Date date = new Date(2020,07,15);
        Date date2 = new Date(2019,10,8);
        Cash cash1 = new Cash("Expense", 101, date, 2, "True", "False", "https://www.enca.com/sites/default/files/LOTTO%20BALLS.jpg");
        Cash cash2 = new Cash("Income", 148, date2, 5, "False", "False", "https://insights.dice.com/wp-content/uploads/2018/01/Salary-Satisfaction-Dice.jpeg");
        Cash cash3 = new Cash("Other", 248, date2, 7, "False", "True", "https://insights.dice.com/wp-content/uploads/2018/01/Salary-Satisfaction-Dice.jpeg");
        transactions.add(cash2);
        transactions.add(cash1);
        transactions.add(cash3);
    }

    private void populateSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.add_to_menu,
                android.R.layout.simple_spinner_dropdown_item);
        spnMenu.setAdapter(adapter);
    }

    private void initializeComponents()
    {
        spnMenu = findViewById(R.id.spinner);
        btnOK = findViewById(R.id.button);
        //gridView = findViewById(R.id.dgv);
        btnDelete = findViewById(R.id.button2);
        listView = findViewById(R.id.lv);
        //img = findViewById(R.id.imageView);
        btnSettings = findViewById(R.id.btnSettings);
        tvStart = findViewById(R.id.edtStart);
        tvDesc = findViewById(R.id.tvDescription);
        String description = "CashApp is a free financial manager and tracker for daily use.";
        tvDesc.setText(description);
        tvDesc.setTypeface(null, Typeface.BOLD);

    }

    public File makeFILE()
    {
        File file = new File(getApplicationContext().getFilesDir(), "LUPSAN_SABRINA_FILE");
        JSONObject obj = new JSONObject();
        try {
            FileWriter fileWriter =new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            JSONObject jsonObject = new JSONObject();
            JSONObject joCollectionOfTransactions = new JSONObject();
            JSONArray jaTransactions = new JSONArray();

            for (int i = 0; i < transactions.size(); i++) {
                Cash c = transactions.get(i);
                JSONObject joBook = new JSONObject();
                joBook.put("type", c.getType());
                joBook.put("amount", c.getCashAmount());
                joBook.put("rating", c.getRating());
                joBook.put("date", c.getDate());
                jaTransactions.put(joBook);
                String userString = joBook.toString();
                bufferedWriter.write(userString);
                Log.d(TAG, "Content: "+userString);
            }
            Log.d(TAG, "Content: "+jaTransactions);
            for(int i=0; i<jaTransactions.length(); i++)
            {
                joCollectionOfTransactions.put("transaction"+(i+1), jaTransactions.getJSONObject(i));
            }
            jsonObject.put("LUPSAN_SABRINA", joCollectionOfTransactions);
            obj = jsonObject;
            Log.d(TAG, "Content: "+jsonObject);
            //joCollectionOfTransactions.put(jaTransactions)
            bufferedWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public String makeJSONFile()
    {
        File file = new File(getApplicationContext().getFilesDir(), "LUPSAN_SABRINA_FILE");
        JSONObject obj = new JSONObject();
        try {
        FileWriter fileWriter =new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            JSONObject jsonObject = new JSONObject();
            JSONObject joCollectionOfTransactions = new JSONObject();
            JSONArray jaTransactions = new JSONArray();

            for (int i = 0; i < transactions.size(); i++) {
                Cash c = transactions.get(i);
                JSONObject joBook = new JSONObject();
                joBook.put("type", c.getType());
                joBook.put("amount", c.getCashAmount());
                joBook.put("rating", c.getRating());
                joBook.put("date", c.getDate());
                jaTransactions.put(joBook);
                String userString = joBook.toString();
                bufferedWriter.write(userString);
                Log.d(TAG, "Content: "+userString);
            }
            Log.d(TAG, "Content: "+jaTransactions);
            for(int i=0; i<jaTransactions.length(); i++)
            {
                joCollectionOfTransactions.put("transaction"+(i+1), jaTransactions.getJSONObject(i));
            }
            jsonObject.put("LUPSAN_SABRINA", joCollectionOfTransactions);
            obj = jsonObject;
            Log.d(TAG, "Content: "+jsonObject);
            //joCollectionOfTransactions.put(jaTransactions)
            bufferedWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
    public JSONObject makeJSONFile2()
    {
        File file = new File(getApplicationContext().getFilesDir(), "my_json");
        JSONObject obj = new JSONObject();
        try {
            FileWriter fileWriter =new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            JSONObject jsonObject = new JSONObject();
            JSONObject joCollectionOfTransactions = new JSONObject();
            JSONArray jaTransactions = new JSONArray();

            for (int i = 0; i < transactions.size(); i++) {
                Cash c = transactions.get(i);
                JSONObject joBook = new JSONObject();
                joBook.put("type", c.getType());
                joBook.put("amount", c.getCashAmount());
                joBook.put("rating", c.getRating());
                joBook.put("date", c.getDate());
                jaTransactions.put(joBook);
                String userString = joBook.toString();
                bufferedWriter.write(userString);
                Log.d(TAG, "Content: "+userString);
            }
            Log.d(TAG, "Content: "+jaTransactions);
            for(int i=0; i<jaTransactions.length(); i++)
            {
                joCollectionOfTransactions.put("transaction"+(i+1), jaTransactions.getJSONObject(i));
            }
            jsonObject.put("Sabrina", joCollectionOfTransactions);
            obj = jsonObject;
            Log.d(TAG, "Content: "+jsonObject);
            //joCollectionOfTransactions.put(jaTransactions)
            bufferedWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}