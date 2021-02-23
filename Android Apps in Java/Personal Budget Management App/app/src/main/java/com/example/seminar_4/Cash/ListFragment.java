package com.example.seminar_4.Cash;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.seminar_4.EditFragment;
import com.example.seminar_4.R;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private static final String TRANSACTIONS = "transactions";

    private ListView lvCash;
    private ArrayList<Cash> listOfTransactions = new ArrayList<Cash>();
    private EditFragment editFragment;
    private Intent intent;

    public ListFragment() {
    }

    public static ListFragment newInstance(ArrayList<Cash> transactions) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TRANSACTIONS, transactions);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected void initComponents(View view)
    {
        if (getArguments() != null)
            listOfTransactions = getArguments().getParcelableArrayList(TRANSACTIONS);
        if(getContext() != null)
        {
            ArrayAdapter<Cash> transactions = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, listOfTransactions);
            lvCash = (ListView)view.findViewById(R.id.lvFragment);
            lvCash.setAdapter(transactions);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        initComponents(view);
        lvCash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cash selectedCash = (Cash) adapterView.getItemAtPosition(i);
                editFragment = EditFragment.newInstance(selectedCash, listOfTransactions);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, editFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }



    public void notifyInternalAdapter() {
        ArrayAdapter adapter = (ArrayAdapter)lvCash.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void setListView(ArrayList<Cash> transactions)
    {

    }

    private void Listener(View view)
    {
        lvCash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cash selectedCash = (Cash) adapterView.getItemAtPosition(i);
                intent = getActivity().getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedCash", selectedCash);
                bundle.putParcelableArrayList(TRANSACTIONS, listOfTransactions);
                intent.putExtras(bundle);
                editFragment = EditFragment.newInstance(selectedCash, listOfTransactions);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, editFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}