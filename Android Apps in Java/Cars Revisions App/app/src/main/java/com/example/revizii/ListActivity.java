package com.example.revizii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Revision> revisions;
    private CustomAdapter customAdapter;
    private GridView gv;
    private GridView gv2;
    private ArrayList<Revision> revisions2 = new ArrayList<>();
    private CustomAdapter customAdapter2;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initialize();
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        revisions = bundle.getParcelableArrayList("all_revisions");

        customAdapter = new CustomAdapter(revisions, getApplicationContext());
        gv.setAdapter(customAdapter);

        customAdapter2 = new CustomAdapter(revisions2, getApplicationContext());
        gv2.setAdapter(customAdapter2);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                if(radioButton.getText().toString().compareTo("Alfabetic (A->Z)")==0)
                {
                    Collections.sort(revisions,  new Revision.NameComparator());
                    customAdapter.notifyDataSetChanged();
                }
                else
                    if(radioButton.getText().toString().compareTo("Invers aflabetic (Z->A)")==0)
                    {
                        Collections.sort(revisions,  new Revision.NameComparator());
                        customAdapter.notifyDataSetChanged();
                    }
                    else
                        if(radioButton.getText().toString().compareTo("Crescator dupa un camp numeric")==0)
                        {
                            Collections.sort(revisions,  new Revision.NumberComparator());
                            customAdapter.notifyDataSetChanged();
                        }
                        else
                            if(radioButton.getText().toString().compareTo("Descrescator dupa un camp numeric")==0)
                            {
                                Collections.sort(revisions,  new Revision.InverseNumberComparator());
                                customAdapter.notifyDataSetChanged();
                            }
                            else
                                if(radioButton.getText().toString().compareTo("A->Z si crescator")==0)
                                {
                                    Collections.sort(revisions,  new Revision.InverseNumberAndNameComparator());
                                    customAdapter.notifyDataSetChanged();
                                }
                                else
                                    if(radioButton.getText().toString().compareTo("A->Z si descrescator")==0)
                                    {
                                        Collections.sort(revisions,  new Revision.InverseNumberComparator());
                                        customAdapter.notifyDataSetChanged();
                                    }
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Switch s = view.findViewById(R.id.switch2);
                final Revision r = (Revision) adapterView.getItemAtPosition(i);
                if(s.isChecked())
                {
                    new AlertDialog.Builder(ListActivity.this)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    revisions.remove(r);
                                    customAdapter.notifyDataSetChanged();                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Revision r = (Revision) adapterView.getItemAtPosition(i);
                revisions.remove(r);
                revisions2.add(r);
                customAdapter.notifyDataSetChanged();
                customAdapter2.notifyDataSetChanged();
                return true;
            }
        });
        gv2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Revision r = (Revision) adapterView.getItemAtPosition(i);
                revisions2.remove(r);
                revisions.add(r);
                customAdapter.notifyDataSetChanged();
                customAdapter2.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void initialize()
    {
        gv = findViewById(R.id.gv);
        gv2 = findViewById(R.id.gv2);
        radioGroup = findViewById(R.id.rd3);
    }
}