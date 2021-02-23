package com.example.casatorii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private Intent intent;
    private Button btnAdd;
    private SeekBar seekBar;
    private ChipGroup chipGroup;
    private EditText edtGroom;
    private EditText edtBride;
    private EditText edtPrice;
    private EditText edtUrl;
    private DatePicker datePicker;
    private Marriage modifyMarriage = null;
    private Chip chip1;
    private Chip chip2;
    private Chip chip3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initialize();
        intent = getIntent();
        modifyMarriage = intent.getParcelableExtra("modify_marriage");
        if (modifyMarriage != null)
        {
            populateControls();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Marriage m = getInfoFromControls();
                if(modifyMarriage!=null)
                {
                    if(m.getBuildingName().compareTo(modifyMarriage.getBuildingName())!=0 || m.getPrice()!=modifyMarriage.getPrice() ||
                        m.getNoOfPeople()!=modifyMarriage.getNoOfPeople() || m.getUrl().compareTo(modifyMarriage.getUrl())!=0)
                        new AlertDialog.Builder(AddActivity.this)
                                .setTitle("Update entry")
                                .setMessage("Are you sure you want to update this entry?")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        intent.putExtra( "one_marriage", m);
                                        intent.putExtra("old_marriage", modifyMarriage);
                                        setResult(100, intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                }
                else
                    if(m!=null)
                    {
                        intent.putExtra("one_marriage", m);
                        setResult(2, intent);
                        finish();
                    }
            }
        });

    }

    private void initialize()
    {
        btnAdd = findViewById(R.id.btnAdd);
        seekBar = findViewById(R.id.seekBar2);
        chipGroup = findViewById(R.id.chipGroup2);
        edtGroom = findViewById(R.id.editTextTextPersonName4);
        edtBride = findViewById(R.id.editTextTextPersonName5);
        edtPrice = findViewById(R.id.editTextNumberDecimal2);
        edtUrl = findViewById(R.id.editTextTextPersonName6);
        datePicker = findViewById(R.id.datePicker1);
        chip1 = findViewById(R.id.chip6);
        chip2 = findViewById(R.id.chip7);
        chip3 = findViewById(R.id.chip8);
    }

    private Marriage getInfoFromControls()
    {
        try {
            Date date = null;
            ArrayList<String> names;
            float price;
            String buildingName;
            int noOfPeople;

            Calendar calendar = Calendar.getInstance();
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            calendar.set(year, month, day);
            date = calendar.getTime();

            names = new ArrayList<>();
            names.add(edtGroom.getText().toString());
            names.add(edtBride.getText().toString());

            price = Float.valueOf(edtPrice.getText().toString());

            Chip chip = findViewById(chipGroup.getCheckedChipId());
            buildingName = chip.getText().toString();

            String urlLink = edtUrl.getText().toString();

            noOfPeople = seekBar.getProgress();

            if (year > 0 && date != null && names.size() == 2 && price > 0 && buildingName != null && buildingName.length() > 1 && noOfPeople > 1 && urlLink!=null) {
                Marriage m = new Marriage(date, names, price, buildingName, noOfPeople, urlLink);
                return m;
            } else
                return null;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    private void populateControls()
    {
        edtPrice.setText(String.valueOf(modifyMarriage.getPrice()));
        edtBride.setText(modifyMarriage.getNames().get(1));
        edtGroom.setText(modifyMarriage.getNames().get(0));
        seekBar.setProgress(modifyMarriage.getNoOfPeople());
        String building = modifyMarriage.getBuildingName();
        if(chip1.getText().toString().compareTo(building)==0)
            chip1.setChecked(true);
        if(chip2.getText().toString().compareTo(building)==0)
            chip2.setChecked(true);
        if(chip3.getText().toString().compareTo(building)==0)
            chip3.setChecked(true);
        edtUrl.setText(modifyMarriage.getUrl());
        Date date = modifyMarriage.getDate();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        datePicker.updateDate(year, month, day);
    }
}