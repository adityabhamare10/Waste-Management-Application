package com.hustler.wastemanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WasteTypeAdapter extends RecyclerView.Adapter<WasteTypeAdapter.ViewHolder> {
    private List<WasteType> wasteTypes;

    public WasteTypeAdapter(List<WasteType> wasteTypes) {
        this.wasteTypes = wasteTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waste_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WasteType wasteType = wasteTypes.get(position);
        holder.titleTextView.setText(wasteType.getTitle());
        holder.descriptionTextView.setText(wasteType.getDescription());
        // Set other fields (tips, recycling process)
    }

    @Override
    public int getItemCount() {
        return wasteTypes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        // Other views (tips, recycling process)

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            // Initialize other views
        }
    }
}
