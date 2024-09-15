package com.hustler.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create the list of WasteType objects
        List<WasteType> wasteTypes = new ArrayList<>();
        List<String> paperTips = new ArrayList<>();
        paperTips.add("•   Reduce: Shift to digital alternatives for documents, billing, and communication. Use reusable cloth towels or napkins instead of paper towels.");
        String t1 = "1. Paper Waste:";
        String d1 = "This is a type of waste that is recyclable and is generated from the sources like:\n" +
                "•\tHousehold: Newspapers, magazines, junk mail, packaging, notebooks, tissue paper.\n" +
                "•\tOffices: Printing paper, documentation, brochures.\n" +
                "•\tRetail/Commercial: Packaging, receipts, paper bags.\n";


        wasteTypes.add(new WasteType(t1, d1, paperTips, "Recycling process details..."));
        // Add other waste types

        WasteTypeAdapter adapter = new WasteTypeAdapter(wasteTypes);
        recyclerView.setAdapter(adapter);
    }
}
