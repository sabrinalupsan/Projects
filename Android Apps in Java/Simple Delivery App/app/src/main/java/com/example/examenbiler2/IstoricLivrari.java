package com.example.examenbiler2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IstoricLivrari extends AppCompatActivity {

    private ListView lvLivrari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_livrari);

        initialize();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Livrare> livrari = bundle.getParcelableArrayList("livrari");
        Toast.makeText(getApplicationContext(), livrari.get(0).toString(), Toast.LENGTH_LONG).show();

        Collections.sort(livrari, new Comparator<Livrare>(){

            public int compare(Livrare o1, Livrare o2)
            {
                return o1.getData().compareTo(o2.getData());
            }
        });
        ArrayAdapter<Livrare> liv = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, livrari);
        lvLivrari.setAdapter(liv);

        lvLivrari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livrare livrareForDeletion = (Livrare)adapterView.getItemAtPosition(i);
                Log.d("TO DELETE", "TO DELETE: "+livrareForDeletion.toString());
                Intent intent = new Intent(getApplicationContext(), AdaugaLivrare.class);
                intent.putExtra("modify_this", (Parcelable)livrareForDeletion);
                startActivityForResult(intent, 301);

            }
        });
    }

    private void initialize()
    {
        lvLivrari = findViewById(R.id.lvLivrari);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && resultCode==300)
        {
            setResult(300, data);
            finish();
        }
        else
            if(data!=null && resultCode==100)
            {
                setResult(301, data);
                finish();
            }
    }
}