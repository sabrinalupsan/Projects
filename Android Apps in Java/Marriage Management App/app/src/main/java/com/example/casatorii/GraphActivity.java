package com.example.casatorii;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class GraphActivity extends AppCompatActivity {

    private ArrayList<Marriage> marriages = new ArrayList<>();
    private Intent intent;
    private DisplayMetrics displayMetrics;
    private int barHeight;
    private float max;
    private ArrayList<Float> mapSector1 = new ArrayList<>();
    private ArrayList<Float> mapSector2 = new ArrayList<>();
    private ArrayList<Float> mapSector3 = new ArrayList<>();
    private HashMap<String, Float> map = new HashMap<>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        marriages = intent.getParcelableArrayListExtra("marriages_for_chart");

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

        barHeight = (int) (displayMetrics.heightPixels * 0.7);
        max = 0.0f;
        for(int i=0; i<marriages.size(); i++)
        {
            if(marriages.get(i).getPrice()>max)
                max = marriages.get(i).getPrice();
        }

        map.put("Sector 1", 0f);
        map.put("Sector 2", 0f);
        map.put("Sector 3", 0f);

        for(int i=0; i<marriages.size(); i++)
        {
            Marriage m = marriages.get(i);
            if(m.getBuildingName().compareTo("Sector 1")==0)
            {
                Float x = map.get("Sector 1");
                x+=m.getPrice();
                map.put("Sector 1", x);
            }
        }

        mapSector1 = createArray("Sector 1");
        mapSector1.sort(new Comparator<Float>() {
            @Override
            public int compare(Float aFloat, Float t1) {
                if(aFloat==t1)
                    return 0;
                else if(aFloat<t1)
                    return 1;
                return -1;
            }
        });
        mapSector2= createArray("Sector 2");
        mapSector2.sort(new Comparator<Float>() {
            @Override
            public int compare(Float aFloat, Float t1) {
                if(aFloat==t1)
                    return 0;
                else if(aFloat<t1)
                    return 1;
                return -1;
            }
        });
        mapSector3 = createArray("Sector 3");
        mapSector3.sort(new Comparator<Float>() {
            @Override
            public int compare(Float aFloat, Float t1) {
                if(aFloat==t1)
                    return 0;
                else if(aFloat<t1)
                    return 1;
                return -1;
            }
        });

        setContentView(new LineChart(getApplicationContext()));
    }

    private ArrayList<Float> createArray(String s)
    {
        ArrayList<Float> theArray = new ArrayList<>();
        for(int i=0; i<marriages.size(); i++)
        {
            if(marriages.get(i).getBuildingName().compareTo(s)==0)
                theArray.add(marriages.get(i).getPrice());
        }
        return theArray;
    }

    protected class BarChart extends View {

        public BarChart (Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int currentIndex = 0;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

            int barWidth = (displayMetrics.widthPixels - 300) / marriages.size();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            for (int i=0; i<marriages.size(); i++) {
                Marriage m = marriages.get(i);
                Random random = new Random();
                paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                canvas.drawRect(currentIndex  * barWidth + 150, (float) (displayMetrics.heightPixels * 0.8 - m.getPrice()*10),
                        (currentIndex + 1) * barWidth + 150, (float) (displayMetrics.heightPixels * 0.8) , paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(30);

                Rect bounds = new Rect();

                String info = m.getNames().get(0);
                paint.getTextBounds(info, 0, info.length(), bounds);
                canvas.drawText(info, currentIndex  * barWidth + (barWidth - bounds.width() / 2), (float) (displayMetrics.heightPixels * 0.82), paint);

                String value = String.valueOf(m.getPrice()*10 * max / barHeight) + "$";
                paint.getTextBounds(value, 0, value.length(), bounds);
                canvas.drawText(value,currentIndex * barWidth + (barWidth - bounds.width() / 2), (float) (displayMetrics.heightPixels * 0.8 - m.getPrice()*10 - 10), paint);

                currentIndex++;
            }
        }
    }

    protected class LineChart extends View {

        public LineChart (Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int currentIndex = 0;
            int widthStep = 70;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            int height  = displayMetrics.heightPixels;

            int barWidth = (displayMetrics.widthPixels - 300) / marriages.size();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            Random random = new Random();
            paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            paint.setStrokeWidth(10);
            paint.setTextSize(30);

            canvas.drawRect(new RectF(750, 1200, 900, 1250), paint);
            canvas.drawText("Sector 1",950, 1250, paint);



            int secondCurrentIndex = 0;
            for(int i=1; i<mapSector1.size(); i++)
            {
                Float x = mapSector1.get(i-1);
                Float y = mapSector1.get(i);
                random = new Random();
                canvas.drawCircle(currentIndex +secondCurrentIndex+ 150, x * 10, 10, paint);
                secondCurrentIndex+=30;
                canvas.drawCircle(currentIndex+secondCurrentIndex + 150, y * 10, 10, paint);
                canvas.drawLine(currentIndex +secondCurrentIndex+ 150-30, x*10, currentIndex+secondCurrentIndex+150, y*10, paint);
                    //canvas.drawLine(currentIndex+150, x.get(j), currentIndex + 150,  (Float) pair.getValue(), paint);
            }

            currentIndex+=150;
            secondCurrentIndex = 0;
            paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            canvas.drawRect(new RectF(750, 1300, 900, 1350), paint);
            canvas.drawText("Sector 2",950, 1350, paint);


            for(int i=1; i<mapSector2.size(); i++)
            {
                Float x = mapSector2.get(i-1);
                Float y = mapSector2.get(i);
                canvas.drawCircle(currentIndex +secondCurrentIndex+ 150, x * 10, 10, paint);
                secondCurrentIndex+=30;
                canvas.drawCircle(currentIndex+secondCurrentIndex + 150, y * 10, 10, paint);
                canvas.drawLine(currentIndex +secondCurrentIndex+ 150-30, x*10, currentIndex+secondCurrentIndex+150, y*10, paint);
            }

            currentIndex+=150;
            secondCurrentIndex = 0;
            paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            canvas.drawRect(new RectF(750, 1400, 900, 1450), paint);
            canvas.drawText("Sector 3",950, 1450, paint);

            for(int i=1; i<mapSector3.size(); i++)
            {
                Float x = mapSector3.get(i-1);
                Float y = mapSector3.get(i);

                canvas.drawCircle(currentIndex +secondCurrentIndex+ 150, x * 10, 10, paint);
                secondCurrentIndex+=30;
                canvas.drawCircle(currentIndex+secondCurrentIndex + 150, y * 10, 10, paint);
                canvas.drawLine(currentIndex +secondCurrentIndex+ 150-30, x*10, currentIndex+secondCurrentIndex+150, y*10, paint);
            }



            /*for(HashMap.Entry pair : mapSector1.entrySet()) {
                Float x = (Float) pair.getValue();

                Random random = new Random();
                paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                paint.setStrokeWidth(10);
                //drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
                canvas.drawLine(currentIndex+150,(float)height - (float)pair.getValue(), currentIndex + 150,  (Float) pair.getValue(), paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(30);

                Rect bounds = new Rect();

                String info = (String) pair.getKey();
                paint.getTextBounds(info, 0, info.length(), bounds);
                //canvas.drawText(info, currentIndex  * barWidth + (barWidth - bounds.width() / 2), (float) (displayMetrics.heightPixels * 0.82), paint);

                String value = String.valueOf(x*10 * max / barHeight) + "$";
                paint.getTextBounds(value, 0, value.length(), bounds);
                //canvas.drawText(value,currentIndex * barWidth + (barWidth - bounds.width() / 2), (float) (displayMetrics.heightPixels * 0.8 - x*10 - 10), paint);

                currentIndex+=widthStep;
            }*/
        }
    }

    class PieChart extends View {

        public PieChart(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int currentIndex = 0;
            float temp = 0;
            float previousValue = 0;

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

            RectF rectF = new RectF(200, 400, displayMetrics.widthPixels - 200, displayMetrics.heightPixels - 800);

            float centerX = (rectF.left + rectF.right) / 2;
            float centerY = (rectF.top + rectF.bottom) / 2;
            float radius = (rectF.right - rectF.left) / 2;

            for(int i=0; i<marriages.size(); i++){
                Marriage m = marriages.get(i);
                if(currentIndex > 0){
                    temp += previousValue;
                }

                Random random  = new Random();
                paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                canvas.drawArc(rectF, temp, m.getPrice(), true, paint);

                paint.setColor(Color.BLACK);
                paint.setTextSize(30);

                float medianAngle = (temp + (m.getPrice() / 2f )) * (float) Math.PI / 180f;
                canvas.drawText(m.getBuildingName(), (float)(centerX + (radius * Math.cos(medianAngle))), (float)(centerY + (radius * Math.sin(medianAngle))), paint);

                currentIndex++;
                previousValue = m.getPrice();
            }
        }
    }
}


