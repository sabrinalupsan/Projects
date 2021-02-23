package com.example.seminar_4;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.seminar_4.Cash.Cash;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.example.seminar_4.CashActivity.TRANSACTIONS;

public class EditFragment extends Fragment {
    protected static final String TRANSACTIONSFORCHART = "transactions";
    private ArrayList<Cash> listOFTransactions = new ArrayList<Cash>();
    private EditText text;
    private Button btnBack;

    private BarChart barChart;
    private LineChart lineChart;
    private LineData lineData;
    private ArrayList<Entry> entryList = new ArrayList<>();
    private ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
    private ArrayList<String> labels = new ArrayList<String>();

    public EditFragment() {
    }

    public static EditFragment newInstance(Cash cash, ArrayList<Cash> transactions) {
        EditFragment fragment = new EditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cash", cash);
        bundle.putParcelableArrayList(TRANSACTIONSFORCHART, transactions);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        initComponents(view);
        if (getArguments() != null) {
            barChart = view.findViewById(R.id.barchart);
            //lineChart = view.findViewById(R.id.lineChart);
            this.listOFTransactions = getArguments().getParcelableArrayList(TRANSACTIONS);
            Cash cash = (Cash) getArguments().getSerializable("cash");

            if(listOFTransactions!=null) {
                for (int i = 0; i < listOFTransactions.size(); i++) {
                    float x = listOFTransactions.get(i).getCashAmount();
                    float y = listOFTransactions.get(i).getRating();
                    String type = listOFTransactions.get(i).getType();
                    if(cash == listOFTransactions.get(i))
                    {
                        Drawable dr = ContextCompat.getDrawable(getContext(), R.drawable.check);
                        BarEntry e = new BarEntry(x, y);
                        e.setIcon(dr);
                        barEntryArrayList.add(e);
                    }
                    barEntryArrayList.add(new BarEntry(x, y));
                    labels.add(type);
                }
            }
            else
            {
                float x = cash.getCashAmount();
                float y = cash.getRating();
                String type = cash.getType();
                Drawable dr = ContextCompat.getDrawable(getContext(), R.drawable.check);
                BarEntry e = new BarEntry(x, y);
                e.setIcon(dr);
                barEntryArrayList.add(e);
                labels.add(type);
            }
            BarDataSet bardataset = new BarDataSet(barEntryArrayList, "Transactions");
            BarData data = new BarData(bardataset);
            Description description = new Description();
            description.setText("The correlation between amount and rating");
            barChart.setData(data);
            barChart.setDescription(description);
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart.animateY(500);
            barChart.invalidate();
        }

        btnBack = view.findViewById(R.id.BackBtn);
        btnBack.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }



    private void initComponents(View view)
    {
        if (getArguments() != null)
        {
            Cash cash = (Cash)getArguments().getSerializable("cash");
            listOFTransactions = getArguments().getParcelableArrayList(TRANSACTIONS);
            if(getContext() != null)
            {
                text = (EditText) view.findViewById(R.id.cashToString);
                text.setText(cash.toString());
            }
        }
    }
}