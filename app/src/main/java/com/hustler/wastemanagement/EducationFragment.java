package com.hustler.wastemanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class EducationFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the list of WasteType objects
        List<WasteType> wasteTypes = new ArrayList<>();
        List<String> paperTips = new ArrayList<>();

        wasteTypes.add(new WasteType("Paper Waste", "This is a type of waste that is recyclable...", paperTips, "Recycling process details..."));

        // Add other waste types

        WasteTypeAdapter adapter = new WasteTypeAdapter(wasteTypes);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
