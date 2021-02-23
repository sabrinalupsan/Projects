package com.example.casatorii;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private ArrayList<Marriage> marriages = new ArrayList<>();
    private ListView lv;
    private CustomAdapter customAdapter;
    private Button btnFilter;
    private RadioGroup radioGroup;
    private String JSONcontent;
    private Button btnPopulate;
    private Button btnPlot;
    private Context context;
    private RepoDatabase repoDatabase;
    private Button btnSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        repoDatabase = RepoDatabase.getInstance(this);
        /*for(int i=0; i<marriages.size();i++)
        {
            MarriageDao m = new MarriageDao(marriages.get(i));
            new InsertAsyncTask(this,m).execute(marriages.get(i));
        }*/

        customAdapter = new CustomAdapter(marriages, getApplicationContext());
        lv.setAdapter(customAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        btnPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                intent.putExtra("marriages_for_chart", marriages);
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Marriage m = (Marriage) adapterView.getItemAtPosition(i);
                String url = m.getUrl();
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                intent.putExtra("image", url);
                startActivity(intent);
                return true;
            }

        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().compareTo("Filter by building") == 0) {
                        Toast.makeText(getApplicationContext(), "Filtering for sector 1...", Toast.LENGTH_LONG).show();
                        ArrayList<Marriage> marriages2 = new ArrayList<>();
                        for (int i = 0; i < marriages.size(); i++) {
                            Marriage m = marriages.get(i);
                            if (m.getBuildingName().compareTo("Sector 1") == 0)
                                marriages2.add(m);
                        }
                        CustomAdapter adapter2 = new CustomAdapter(marriages2, getApplicationContext());
                        lv.setAdapter(adapter2);
                    } else if (radioButton.getText().toString().compareTo("Filter by price") == 0) {
                        Toast.makeText(getApplicationContext(), "Filtering for prices bigger than 50...", Toast.LENGTH_LONG).show();
                        ArrayList<Marriage> marriages2 = new ArrayList<>();
                        for (int i = 0; i < marriages.size(); i++) {
                            Marriage m = marriages.get(i);
                            if (m.getPrice() > 50)
                                marriages2.add(m);
                        }
                        CustomAdapter adapter2 = new CustomAdapter(marriages2, getApplicationContext());
                        lv.setAdapter(adapter2);
                    } else if (radioButton.getText().toString().compareTo("Filter by number of people") == 0) {
                        Toast.makeText(getApplicationContext(), "Filtering for more than 10 people...", Toast.LENGTH_LONG).show();
                        ArrayList<Marriage> marriages2 = new ArrayList<>();
                        for (int i = 0; i < marriages.size(); i++) {
                            Marriage m = marriages.get(i);
                            if (m.getNoOfPeople() > 10)
                                marriages2.add(m);
                        }
                        CustomAdapter adapter2 = new CustomAdapter(marriages2, getApplicationContext());
                        lv.setAdapter(adapter2);
                    } else if (radioButton.getText().toString().compareTo("Go back") == 0) {
                        lv.setAdapter(customAdapter);
                    }
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Please select a filter condition!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONcontent = downloadJSON();
                    getMarriagesFromFile();
                    customAdapter.notifyDataSetChanged();

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }





    public String downloadJSON() throws IOException {
        File file = new File(getApplicationContext().getFilesDir(), "FISIER");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();

        char caracterCurent;
        int data = bufferedReader.read();
        while (data != -1) {
            caracterCurent = (char) data;
            stringBuilder.append(caracterCurent);
            data = bufferedReader.read();
        }

        String result = stringBuilder.toString();
        Log.d("TAG", "REZULTATUL: "+result.toString());
        bufferedReader.close();
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.salvare) {
            File writeFile = new File(getApplicationContext().getFilesDir(), "FISIER");

            FileWriter writer = null;
            BufferedWriter bufferedWriter = null;
            try {
                writer = new FileWriter(writeFile);
                bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write("{\n" +
                        "    \"Sector 1\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Lupsan\",\n" +
                        "            \"number\": 1,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":60.0,\n" +
                        "                \"url\": \"https://static.officeholidays.com/images/1280x853c/christmas.jpg\",\n" +
                        "                \"noOfPeople\": 70\n" +
                        "              }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Paunica\",\n" +
                        "            \"number\": 2,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":80.0,\n" +
                        "                \"url\": \"https://static.officeholidays.com/images/1280x853c/christmas.jpg\",\n" +
                        "                \"noOfPeople\": 22\n" +
                        "              }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Andrei\",\n" +
                        "            \"number\": 3,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":90.0,\n" +
                        "                \"url\": \"https://static.officeholidays.com/images/1280x853c/christmas.jpg\",\n" +
                        "                \"noOfPeople\": 12\n" +
                        "              }\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Sector 2\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Lupsan\",\n" +
                        "            \"number\": 1,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":87.0,\n" +
                        "                \"url\": \"https://static.officeholidays.com/images/1280x853c/christmas.jpg\",\n" +
                        "                \"noOfPeople\": 2\n" +
                        "              }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Paunica\",\n" +
                        "            \"number\": 2,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":60.0,\n" +
                        "                \"url\": \"https://static.officeholidays.com/images/1280x853c/christmas.jpg\",\n" +
                        "                \"noOfPeople\": 10\n" +
                        "              }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Andrei\",\n" +
                        "            \"number\": 3,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":52.0,\n" +
                        "                \"url\": \"https://static.officeholidays.com/images/1280x853c/christmas.jpg\",\n" +
                        "                \"noOfPeople\": 2\n" +
                        "              }\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Sector 3\":\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Lupsan\",\n" +
                        "            \"number\": 1,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":60.0,\n" +
                        "                \"url\": \"https://steamuserimages-a.akamaihd.net/ugc/450736146520211399/53CB26B58CC9FD232DE69CD54CB2F7ED7CB8DE07/\",\n" +
                        "                \"noOfPeople\": 2\n" +
                        "              }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Paunica\",\n" +
                        "            \"number\": 2,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":63.0,\n" +
                        "                \"url\": \"https://steamuserimages-a.akamaihd.net/ugc/450736146520211399/53CB26B58CC9FD232DE69CD54CB2F7ED7CB8DE07/\",\n" +
                        "                \"noOfPeople\": 2\n" +
                        "              }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"FamilyName\": \"Andrei\",\n" +
                        "            \"number\": 3,\n" +
                        "            \"details\": \n" +
                        "              {\n" +
                        "                \"price\":40.0,\n" +
                        "                \"url\": \"https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MXx8aHVtYW58ZW58MHx8MHw%3D&ixlib=rb-1.2.1&w=1000&q=80\",\n" +
                        "                \"noOfPeople\": 2\n" +
                        "              }\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}");

                /*for(int i=0; i<apartments.size(); i++)
                    bufferedWriter.write(apartments.get(i).toString());*/
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        } else if (item.getItemId() == R.id.download) {

        }
        return true;
    }

    public class InsertAsyncTask extends AsyncTask<Marriage, Void, Marriage> {

        Context context;
        MarriageDao dao;
        MarriagesDao daos;

        public InsertAsyncTask(Context context, MarriageDao dao) {
            this.context = context;
            this.dao = dao;
        }

        @Override
        protected Marriage doInBackground(Marriage... marriages) {
            MarriageDao marriageDao = new MarriageDao(marriages[0]);
            daos.insertMarriage(marriageDao);
            return marriages[0];
        }

        @Override
        protected void onPostExecute(Marriage marriage) {
            super.onPostExecute(marriage);
            Toast.makeText(context, marriage.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initialize()
    {
        btnAdd = findViewById(R.id.btnAdd2);
        btnFilter = findViewById(R.id.btnFilter);
        lv = findViewById(R.id.lv);
        radioGroup = findViewById(R.id.rd);
        btnPopulate = findViewById(R.id.btnPopulate);
        btnPlot = findViewById(R.id.btnPlot);
        context = getApplicationContext();
        btnSettings = findViewById(R.id.btnSettings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null && requestCode == 1 && resultCode == 2) {
                Marriage m = data.getParcelableExtra("one_marriage");
                if (m != null) {
                    Marriage old = data.getParcelableExtra("old_marriage");
                    if (old != null)
                        marriages.remove(old);
                    final MarriageDataSource userDataSource = new MarriageDataSource(repoDatabase.marriagesDao());
                    MarriageDao marriageDao = new MarriageDao(m);
                    userDataSource.insertMarriage(marriageDao);
                    //InsertAsyncTask asyncTask = new InsertAsyncTask(getApplicationContext(), marriageDao);
                    //asyncTask.execute((Runnable) marriageDao);
                    //MarriageDao m2 = new MarriageDao(m);
                    new InsertAsyncTask(this, marriageDao).execute(m);
                    marriages.add(m);
                    customAdapter.notifyDataSetChanged();
                }
            } else {
                Marriage old = data.getParcelableExtra("old_marriage");
                Marriage newMarriage = data.getParcelableExtra("new_marriage");
                marriages.remove(old);
                marriages.add(newMarriage);
                customAdapter.notifyDataSetChanged();
            }
        }
        catch(Exception e)
        {
            //do something
        }
    }

    public void getMarriagesFromFile() throws JSONException {
        String s = JSONcontent;
        JSONObject bigObj = new JSONObject(s);

        JSONArray firstArray = new JSONArray();
        firstArray = bigObj.getJSONArray("Sector 1");

        for(int i=0; i<firstArray.length(); i++)
        {
            String building2 = "Sector 1";
            JSONObject obj = new JSONObject();
            obj = firstArray.getJSONObject(i);

            String famName = obj.getString("FamilyName");

            JSONObject details = obj.getJSONObject("details");


            float price = (float) details.getDouble("price");


            int number2 = details.getInt("noOfPeople");

            String url = details.getString("url");

            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            ArrayList<String> names = new ArrayList<>();
            names.add(famName);
            names.add(famName);

            Marriage m = new Marriage(date, names, price, building2, number2, url);
            marriages.add(m);
            //Apartments apartment = new Apartments(neighbourhood, number, surface, famName, building);
            //apartments.add(apartment);
        }

        JSONArray secondArray = new JSONArray();
        secondArray = bigObj.getJSONArray("Sector 2");
        for(int i=0; i<secondArray.length(); i++)
        {
            String building2 = "Sector 2";
            JSONObject obj = new JSONObject();
            obj = secondArray.getJSONObject(i);

            String famName = obj.getString("FamilyName");

            JSONObject details = obj.getJSONObject("details");


            float price = (float) details.getDouble("price");


            int number2 = details.getInt("noOfPeople");

            String url = details.getString("url");

            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            ArrayList<String> names = new ArrayList<>();
            names.add(famName);
            names.add(famName);

            Marriage m = new Marriage(date, names, price, building2, number2, url);
            marriages.add(m);
        }

        JSONArray thirdArray = new JSONArray();
        thirdArray = bigObj.getJSONArray("Sector 3");
        for(int i=0; i<thirdArray.length(); i++)
        {
            String building2 = "Sector 3";
            JSONObject obj = new JSONObject();
            obj = thirdArray.getJSONObject(i);

            String famName = obj.getString("FamilyName");

            JSONObject details = obj.getJSONObject("details");


            float price = (float) details.getDouble("price");


            int number2 = details.getInt("noOfPeople");

            String url = details.getString("url");

            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            ArrayList<String> names = new ArrayList<>();
            names.add(famName);
            names.add(famName);

            Marriage m = new Marriage(date, names, price, building2, number2, url);
            marriages.add(m);
        }
        customAdapter.notifyDataSetChanged();
    }
}