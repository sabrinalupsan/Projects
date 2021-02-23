package com.example.seminar_4.Cash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seminar_4.Cash.Cash;
import com.example.seminar_4.R;

import java.util.ArrayList;

public class TypeAdapter extends BaseAdapter {

    ArrayList<Cash> cashList = new ArrayList<Cash>();
    LayoutInflater layoutInflater;
    Context context;

    public TypeAdapter(Context context, ArrayList<Cash> list)
    {
        cashList = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cashList.size();
    }

    @Override
    public Object getItem(int i) {
        return cashList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class CashHolder
    {
        TextView type;
        TextView date;
        TextView cashAmount;
        TextView rating;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final CashHolder holder;
        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.my_layout_cash, viewGroup, false);
            holder = new CashHolder();
            holder.type = (TextView)view.findViewById(R.id.tvType);
            holder.date = (TextView)view.findViewById(R.id.tvDate);
            holder.cashAmount = (TextView)view.findViewById(R.id.tvCashAmount);
            holder.rating = (TextView)view.findViewById(R.id.tvRating);
            //holder.imageView = (ImageView)view.findViewById(R.id.imageView);
            view.setTag(holder);

        }
        else
        {
            holder = (CashHolder)view.getTag();
        }
        String cashType = cashList.get(i).getType();
        holder.type.setText(cashType);

        String datetime = cashList.get(i).getDate().toString();
        holder.date.setText("The date: " + datetime);

        float cash = cashList.get(i).getCashAmount();
        String cashAmount1 = String.valueOf(cash);
        holder.cashAmount.setText("The amount of cash: "+cashAmount1);

        float rating1 = cashList.get(i).getRating();
        String rating2 = String.valueOf("The rating: "+rating1);
        holder.rating.setText(rating2);
        /*DownloadImage imageTask = new DownloadImage(cashList.get(i).getURL());
        Thread downloadThread = new Thread(imageTask);
        downloadThread.start();

        DownloadImage.handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "-----------image received from thread-----------");
                Bundle data = msg.getData();
                Bitmap image = data.getParcelable("image");
                Log.d(TAG, String.valueOf(image.getHeight()));
                holder.imageView.setImageBitmap(image);
            }
        };*/
        return view;
    }
}
