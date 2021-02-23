package com.example.revizii;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAddRevision;
    private ArrayList<Revision> revisions = new ArrayList<>();
    private Spinner spn;
    private Button btnDeleteRevision;
    private Button btnSeeList;
    private Button btnDuplicateRevision;
    private ArrayAdapter<Revision> adapter;
    private ChartFragment fragment;
    private RoomDatabase roomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        roomDatabase = RoomDatabase.getInstance(getApplicationContext());
        btnAddRevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btnDeleteRevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Revision revision = (Revision) spn.getSelectedItem();
                revisions.remove(revision);
                adapter.notifyDataSetChanged();
                ChartFragment fragment = ChartFragment.newInstance(revisions);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        btnDuplicateRevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Revision revision = (Revision) spn.getSelectedItem();
                if(revision!=null) {
                    revisions.add(revision);

                    adapter.notifyDataSetChanged();
                    ChartFragment fragment = ChartFragment.newInstance(revisions);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        btnSeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("all_revisions", revisions);
                intent.putExtras(bundle);
                startActivityForResult(intent, 5);
            }
        });
    }

    private void initialize()
    {
        btnAddRevision = findViewById(R.id.btnAddRevision);
        btnDeleteRevision = findViewById(R.id.btnDelete);
        btnDuplicateRevision = findViewById(R.id.btnDuplicate);
        spn = findViewById(R.id.spinner);
        btnSeeList = findViewById(R.id.btnSeeList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && requestCode==1 && resultCode==2)
        {
            Revision revision = data.getParcelableExtra("one_revision");
            if(revision!=null)
            {
                revisions.add(revision);
                RevisionDAO revisionDAO = new RevisionDAO(revision);
                final RevisionDataSource userDataSource = new RevisionDataSource(roomDatabase.revisionsDAO());
                userDataSource.insertRevision(revisionDAO);
            }
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, revisions);
            spn.setAdapter(adapter);

            ChartFragment fragment = ChartFragment.newInstance(revisions);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }
    }
}