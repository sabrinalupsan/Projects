package com.example.examenbiler2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaugaLivrare extends AppCompatActivity {

    private EditText destinatar;
    private EditText adresa;
    private EditText data;
    private EditText valoare;
    private Button btnDone;
    private Button btnDelete;
    private Spinner spnTip;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_livrare);
        initialize();
        intent = getIntent();

        Livrare liv = intent.getParcelableExtra("modify_this");
        if(liv != null) {
            destinatar.setText(liv.getDestinatar());
            adresa.setText(liv.getAdresa());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(liv.getData());
            data.setText(strDate);

            String val = String.valueOf(liv.getValoare());
            valoare.setText(val);

            for (int i = 0; i < spnTip.getCount(); i++) {
                if (spnTip.getItemAtPosition(i) == liv.getTip()) {
                    spnTip.setSelection(i);
                    return;
                }
            }
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Livrare livrare = validate();

                    if(livrare == null)
                        Toast.makeText(getApplicationContext(), R.string.wrong_Data, Toast.LENGTH_LONG).show();
                    else
                    {
                        intent.putExtra("o_liv", (Parcelable) livrare);
                        setResult(100, intent);
                        finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Livrare livrare = intent.getParcelableExtra("modify_this");

                if(livrare == null)
                    Toast.makeText(getApplicationContext(), R.string.wrong_Data, Toast.LENGTH_LONG).show();
                else
                {
                    intent.putExtra("delete_liv", (Parcelable) livrare);
                    setResult(300, intent);
                    finish();
                }
            }
        });
    }

    private void initialize()
    {
        destinatar = findViewById(R.id.tvDestinatar);
        adresa = findViewById(R.id.tvAdresa);
        data = findViewById(R.id.tvData);
        valoare = findViewById(R.id.tvNumber);
        spnTip = findViewById(R.id.spinner);
        btnDone = findViewById(R.id.button);
        btnDelete = findViewById(R.id.button2);
    }

    private Livrare validate() throws ParseException {
        Livrare livrare;
        String dest=null;
        String add=null;
        String date=null;
        String tip=null;
        float val1=0;
        String val2=null;

        try {
            dest = destinatar.getText().toString();
            if (dest.length() < 2)
                return null;
            add = adresa.getText().toString();
            if (add.length() < 2)
                return null;
            date = data.getText().toString();
            if (date.length() < 1)
                return null;
            Date finalDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            val2 = valoare.getText().toString();
            val1 = Float.parseFloat(val2);
            if (val1 < 0)
                return null;
            tip = spnTip.getSelectedItem().toString();
            if (tip.compareTo("FRAGIL") != 0 && tip.compareTo("DUR") != 0 && tip.compareTo("NORMAL") != 0)
                return null;

         livrare = new Livrare(dest, add, finalDate, val1, tip);
        }catch(Exception e)
        {
            SimpleDateFormat formatter5=new SimpleDateFormat("EEE MMM dd kk:mm:ss zXXX yyyy");

            livrare = new Livrare(dest, add, formatter5.parse(date), val1, tip);
            val2 = valoare.getText().toString();
            val1 = Float.parseFloat(val2);
            if (val1 < 0)
                return null;
            tip = spnTip.getSelectedItem().toString();
            if (tip.compareTo("FRAGIL") != 0 && tip.compareTo("DUR") != 0 && tip.compareTo("NORMAL") != 0)
                return null;
        }

        return livrare;
    }
}