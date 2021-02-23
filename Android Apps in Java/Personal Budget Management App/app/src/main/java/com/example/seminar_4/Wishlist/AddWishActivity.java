package com.example.seminar_4.Wishlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seminar_4.model.Wish;
import com.example.seminar_4.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddWishActivity extends AppCompatActivity {

    private EditText wishName;
    private RatingBar importance;
    private EditText deadline;
    private EditText cost;
    private Spinner spnAlerts;
    private Button btnAdd;
    private Intent intent;
    private RadioGroup rgCategory;
    private Button btnSeeMaps;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);
        init();
        populateSpnAlerts();
        btnAdd.setOnClickListener(addWishClickEvent());
        intent=getIntent();
        btnSeeMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapsIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(mapsIntent, 2);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private View.OnClickListener addWishClickEvent(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wish wish= buildWishFromWidgets();
                if(wish!=null) {
                    intent.putExtra("wish", wish);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private Wish buildWishFromWidgets() {
        try {
            Date date = null;
            SimpleDateFormat formater = new SimpleDateFormat("dd/mm/yyyy", Locale.UK);
            try {
                date = formater.parse(deadline.getText().toString());
//            wish.setDeadline(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String name = wishName.getText().toString();
            float wCost = Float.parseFloat(cost.getText().toString());
            float imp = importance.getRating();
            String category;
            if (rgCategory.getCheckedRadioButtonId() == R.id.rbExperience) {
                category = "Experience";
            } else category = "Material Good";

            String alert = spnAlerts.getSelectedItem().toString();

            Wish wish = new Wish(name, imp, date, wCost, alert, category);

            return wish;
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Incorrect info", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void init(){
        wishName=findViewById(R.id.editWishName);
        importance=findViewById(R.id.ratingBar);
        deadline=findViewById(R.id.editDeadline);
        cost=findViewById(R.id.editCost);
        spnAlerts=findViewById(R.id.spnAlerts);
        btnAdd=findViewById(R.id.addBtn);
        rgCategory=findViewById(R.id.rgCategory);
        btnSeeMaps = findViewById(R.id.addMaps);
        btnCancel = findViewById(R.id.cancelBtn);
    }

    private void populateSpnAlerts(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alerts, android.R.layout.simple_spinner_dropdown_item );
        spnAlerts.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            String res = data.getStringExtra("wishname");
            wishName.setText("Trip to "+res);
        }

    }
}

