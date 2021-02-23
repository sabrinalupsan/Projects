package com.example.casatorii;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Marriage> marriages;
    private Context context;

    public CustomAdapter(ArrayList<Marriage> marriages, Context context)
    {
        this.marriages = marriages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return marriages.size();
    }

    @Override
    public Object getItem(int i) {
        return marriages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Marriage marriage = marriages.get(i);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View generatedView = layoutInflater.inflate(R.layout.my_layout, viewGroup, false);
        final View colorThisView = generatedView;

        TextView tv = generatedView.findViewById(R.id.textView);
        tv.setText(marriage.toString());

        Button btnClasificare = generatedView.findViewById(R.id.btnClasificare);
        Button btnEditare = generatedView.findViewById(R.id.btnEditare);

        btnClasificare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = Color.TRANSPARENT;
                Drawable background = colorThisView.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();
                if(marriage.getPrice()<50)
                    colorThisView.setBackgroundColor(Color.RED);
                else
                    if(marriage.getPrice()==50)
                        colorThisView.setBackgroundColor(Color.rgb(255, 165, 0));
                    else
                        colorThisView.setBackgroundColor(Color.GREEN);

            }
        });

        btnEditare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("modify_marriage", marriage);
                context.startActivity(intent);
            }
        });



        return generatedView;
    }
}
