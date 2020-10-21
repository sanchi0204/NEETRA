package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Reports extends AppCompatActivity {

    private ReportAdapter adapter;
    private List<ReportItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillList();
        setUpRecyclerView();

    }

    private void fillList() {
        list = new ArrayList<>();
        list.add(new ReportItem("2 August 2020",
                "Laxmi Gupta", "487264074695", "Eve Teasing", "Proof", "9:00 PM"));
        list.add(new ReportItem("2 August 2020",
                "Sameeksha Bhatia", "4891234074695", "Chain Snatching", " ", "7:00 PM"));
        list.add(new ReportItem("1 August 2020",
                "Ishant Rajput", "126590074695", "Pick Pocketing", " ", "6:00 PM"));
        list.add(new ReportItem("1 August 2020",
                "Sanjay Puri", "487264074695", "Pick Pocketing", "Proof", "3:00 PM"));
        list.add(new ReportItem("1 August 2020",
                "Shreya Shail", "487264074695", "Vandalism", "Proof", "10:00 AM"));
        list.add(new ReportItem("31 July 2020",
                "Sahil Kapoor", "487264074695", "Chain Snatching", "Proof", "8:00 PM"));
        list.add(new ReportItem("31 July 2020",
                "Riya Nath", "487264074695", "Eve Teasing", " ", "5:00 PM"));

    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ReportAdapter(list,Reports.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
//    public void PriorityActivity(View view)
//    {
//        startActivity(new Intent(Reports.this,Priority.class));
//    }

}
