package com.example.examenbiler2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnAdaugaLivrari;
    private Button btnIstoricLivrari;
    private Button btnImportDate;
    private Button btnDespre;
    private TextView tvData;
    private ArrayList<Livrare> livrari = new ArrayList<Livrare>();
    RepoDatabase repoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        downloadJSON();
        repoDatabase = RepoDatabase.getInstance(this);
        btnDespre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.despre, Toast.LENGTH_LONG).show();
            }
        });
        btnAdaugaLivrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdaugaLivrare.class);
                startActivityForResult(intent, 1);
            }
        });
        btnIstoricLivrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IstoricLivrari.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("livrari", livrari);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });
        btnImportDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initialize()
    {
        btnAdaugaLivrari = findViewById(R.id.btnAdauga);
        btnIstoricLivrari = findViewById(R.id.btnIstoric);
        btnImportDate = findViewById(R.id.btnImport);
        btnDespre = findViewById(R.id.btnDespre);
        tvData = findViewById(R.id.textview3);
        Calendar cal = Calendar.getInstance();
        int currentHourIn24Format = cal.get(Calendar.HOUR_OF_DAY);
        tvData.setText(getString(R.string.accesata)+currentHourIn24Format);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && requestCode==1)
        {
            save();
            Livrare livrare = (Livrare)data.getParcelableExtra("o_liv");
            livrari.add(livrare);
        }
        if(data!=null && resultCode==300)
        {
            save();
            Livrare livrare = (Livrare)data.getParcelableExtra("modify_this");
            livrari.remove(livrare);

        }
        if(data!=null && resultCode==301)
        {
            save();
            Livrare livrare = (Livrare)data.getParcelableExtra("modify_this");
            Livrare livrare2 = (Livrare)data.getParcelableExtra("o_liv");
            livrari.remove(livrare);
            livrari.add(livrare2);

        }
    }
    public void downloadJSON()
    {
       /* DownloadContent imageTask = new DownloadContent("http://pdm.ase.ro/images/tehnologii.png");
        Thread downloadThread = new Thread(imageTask);
        downloadThread.start();*/

        DownloadAsync downloadAsync = new DownloadAsync()
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray jaBooks = new JSONArray(s);
                    for (int index=0; index<jaBooks.length(); index++)
                    {
                        JSONObject joBook = jaBooks.getJSONObject(index);
                        String destinatar = joBook.getString("statiePlecare");
                        String adresa = joBook.getString("satieSosire");
                        String data = joBook.getString("data");
                        String valoare = joBook.getString("pret");
                        String tip = joBook.getString("tip");
                        Log.d("SMTH", "TIPUL ERA: "+tip);
                        Livrare livrare = new Livrare(destinatar, adresa, new SimpleDateFormat("dd-MM-yyyy").parse(data), Float.parseFloat(valoare), "NORMAL");
                        livrari.add(livrare);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        downloadAsync.execute("http://pdm.ase.ro/examen/bilete.json");

    }

    public void save()
    {
        final LivrareDataSource userDataSource = new LivrareDataSource(repoDatabase.livrariDao());
        for(Livrare l : livrari)
        {
            LivrareDao liv= new LivrareDao(l);
            userDataSource.insertUser(liv);
        }
    }


}