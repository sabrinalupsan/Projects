package com.example.seminar_4.Wishlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.seminar_4.Cash.DownloadContent;
import com.example.seminar_4.model.Image;
import com.example.seminar_4.model.Wish;
import com.example.seminar_4.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityWishlist extends AppCompatActivity  {

    private  static final int ADD_WISH_REQUEST_CODE=210;
    private List<Wish> wishList=new ArrayList<>();
//    RepoDataBase repoDatabase;

    private ListView lvWishes;
    private GridView gvWishes;
    private  ArrayList<Image> bitmapArrayList=new ArrayList<>();
    private ProgressBar progressBar;
    private Button btnCancel;
    private static final String TAG = MainActivityWishlist.class.getSimpleName() ;
    private DbHelper databaseHelper;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wishes);
//      repoDatabase = RepoDataBase.getInstance(this);
        init();

        Date date=new Date();
        Wish wish1= new Wish(1,"https://www.autocar.co.uk/sites/autocar.co.uk/files/images/car-reviews/first-drives/legacy/large-2479-s-classsaloon.jpg","Car",5, date,3000,"weekly","Material Good");
        Wish wish2= new Wish(2,"https://didmdw8v48h5q.cloudfront.net/wp-content/uploads/2019/12/New-York-Study-915x580-1.jpg","Trip to USA",3, date,12000,"weekly","Experience");
        progressBar= findViewById(R.id.progressBar);

        this.databaseHelper = new DbHelper(this);

//        databaseHelper.insertWish(wish1);
        try {
            wishList=databaseHelper.selectAll();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Wish> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, //aici bag layout ul facut de mn
                wishList);
        lvWishes.setAdapter(adapter);

        progressBar= findViewById(R.id.progressBar);

        Map<Long, Wish> wishMap = new HashMap<>();
        wishMap.put(wish1.getId(),wish1);
        wishMap.put(wish2.getId(),wish2);

        Log.d(TAG, "----------downloadImage method------------");
        wishMap.forEach((id,wish)->{
            DownloadContent imageTask = new DownloadContent(wish);
            Thread downloadThread = new Thread(imageTask);
            downloadThread.start();
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DownloadContent.handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "----------image received from thread------------");
                Bundle data = msg.getData();
                Bitmap image = data.getParcelable("image");
                String categ=data.getString("category");

                bitmapArrayList.add(new Image(image,categ));
                        addLvWishesAdapter(wishMap);

            }
        };

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addLvWishesAdapter(Map<Long, Wish> wishMap) {
        if(this.bitmapArrayList.size()<wishMap.size())
            return;

        JSONArray jsonArray= new JSONArray();
        JSONObject jsonWishes= new JSONObject();

        try {
            wishMap.forEach((id,wish1)->{
            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("url",wish1.getUrl());

            jsonObject.put("name",wish1.getName());
            jsonObject.put("importance",wish1.getImportance());
            jsonObject.put("deadline",wish1.getDeadline());
            jsonObject.put("cost",wish1.getCost());
            jsonObject.put("alert",wish1.getAlert());
            jsonObject.put("category",wish1.getCategory());
            jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

            jsonWishes.put("Lepirda Damon-Gabriel",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,jsonWishes.toString());

        Async postAsync=new Async();
        String url = "https://ptsv2.com/t/azjlv-1606407696/post";

        postAsync.execute(url,jsonWishes.toString());

        MyAdapter customAdapter = new MyAdapter(this, wishMap,this.bitmapArrayList);
        gvWishes.setAdapter(customAdapter);

    }

    private void init(){
        lvWishes=findViewById(R.id.wishesLv);
        gvWishes=findViewById(R.id.gvWishes);
        btnCancel = findViewById(R.id.buttonCancelWish);
    }

    public void startAddWish(View view) {
        Intent intent= new Intent(getApplicationContext(), AddWishActivity.class);
        startActivityForResult(intent,ADD_WISH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== ADD_WISH_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
            Wish wish =(Wish)data.getSerializableExtra("wish"); // getParcealableExtra for yk
            if(wish!=null){
                Toast.makeText(getApplicationContext(),"Wish was added succesfully",Toast.LENGTH_LONG).show();
                wishList.add(wish);
                databaseHelper.insertWish(wish);


                ArrayAdapter adapter=(ArrayAdapter)lvWishes.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void btnSave(View view) {

        MyAdapter adapter = (MyAdapter)gvWishes.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.checkedHolder[i]){
                wishList.add((Wish)adapter.getItem(i));
            }

            ArrayAdapter<Wish> adapter1= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, //aici bag layout ul facut de mn
                    wishList);
            lvWishes.setAdapter(adapter1);

        }
    }

}