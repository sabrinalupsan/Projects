package com.example.revizii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton btnRenault;
    private RadioButton btnPorsche;
    private RadioButton btnSkoda;
    private SeekBar seekBarPrice;
    private DatePicker datePicker;
    private CheckBox cbModel1;
    private CheckBox cbModel2;
    private CheckBox cbModel3;
    private Switch type;
    private Button btnAdd;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initialize();
        intent = getIntent();

        cbModel1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbModel1.isChecked()==true)
                {
                    cbModel2.setEnabled(false);
                    cbModel3.setEnabled(false);
                }
                else
                {
                    cbModel2.setEnabled(true);
                    cbModel3.setEnabled(true);
                }
            }
        });

        cbModel2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbModel2.isChecked()==true)
                {
                    cbModel1.setEnabled(false);
                    cbModel3.setEnabled(false);
                }
                else
                {
                    cbModel1.setEnabled(true);
                    cbModel3.setEnabled(true);
                }
            }
        });
        cbModel3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbModel3.isChecked()==true)
                {
                    cbModel1.setEnabled(false);
                    cbModel2.setEnabled(false);
                }
                else
                {
                    cbModel1.setEnabled(true);
                    cbModel2.setEnabled(true);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Revision revision = getInfoFromControls();
                if(revision!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("one_revision", revision);
                    intent.putExtras(bundle);
                    setResult(2, intent);
                    finish();
                }
            }
        });
    }

    private void initialize()
    {
        radioGroup = findViewById(R.id.radioGroup2);
        btnRenault = findViewById(R.id.btnRenault);
        btnPorsche = findViewById(R.id.btnPorsche);
        btnSkoda = findViewById(R.id.btnSkoda);
        seekBarPrice = findViewById(R.id.seekBar);
        datePicker = findViewById(R.id.datePicker1);
        cbModel1 = findViewById(R.id.chkModel1);
        cbModel2 = findViewById(R.id.chkModel2);
        cbModel3 = findViewById(R.id.chkModel3);
        type = findViewById(R.id.switch1);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private Revision getInfoFromControls()
    {
        try {
            int pret;
            String modelMasina = null;
            String producator;
            Date dataRevizie;
            String theType;

            RadioButton btnProducator = findViewById(radioGroup.getCheckedRadioButtonId());
            producator = btnProducator.getText().toString();

            pret = seekBarPrice.getProgress();


            if (type.isChecked() == true)
                theType = TYPE.normal.toString();
            else
                theType = TYPE.sport.toString();

            if (cbModel1.isChecked())
                modelMasina = cbModel1.getText().toString();
            else if (cbModel2.isChecked())
                modelMasina = cbModel2.getText().toString();
            else if(cbModel3.isChecked())
                modelMasina = cbModel3.getText().toString();

            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            int day = datePicker.getDayOfMonth();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date theDate = calendar.getTime();

            Revision revision = null;
            if (pret > 0 && modelMasina!=null && modelMasina.length() > 1 && producator.length() > 1 && theDate != null && theType.length() > 1)
                revision = new Revision(pret, modelMasina, producator, theDate, theType);
            else
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

            return revision;
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            return null;
        }

    }
}