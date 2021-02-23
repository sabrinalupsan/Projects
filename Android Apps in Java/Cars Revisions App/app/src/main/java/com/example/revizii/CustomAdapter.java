package com.example.revizii;

import android.content.Context;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Revision> revisions;
    private Context context;

    public CustomAdapter(ArrayList<Revision> revisions, Context context)
    {
        this.revisions = revisions;
        this.context=context;
    }

    @Override
    public int getCount() {
        return revisions.size();
    }

    @Override
    public Object getItem(int i) {
        return revisions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void updatePrice(int i, boolean getChecked)
    {
        if(getChecked==true)
        {
            revisions.get(i).setPret(revisions.get(i).getPret()/2);
        }
        else
            revisions.get(i).setPret(revisions.get(i).getPret()*2);

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final int x = i;
        final Revision revision = revisions.get(i);
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View generatedView = layoutInflater.inflate(R.layout.my_layout, viewGroup, false);

        TextView tvAll = generatedView.findViewById(R.id.tvAll);
        final TextView tvPrice = generatedView.findViewById(R.id.tvPrice);
        Switch aSwitch = generatedView.findViewById(R.id.switch2);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context, "onCheck", Toast.LENGTH_LONG).show();
                if(b==true)
                    revision.setPret(revision.getPret()/2);
                else
                    revision.setPret(revisions.get(x).getPret()*2);
                tvPrice.setText(String.valueOf(revision.getPret()));
            }
        });

        tvAll.setText(revision.almostToString());
        tvPrice.setText(String.valueOf(revision.getPret()));

        return generatedView;
    }
}
