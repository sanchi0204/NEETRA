package com.example.policeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ExampleViewHolder> implements Filterable {
    private List<ReportItem> exampleList;
    private List<ReportItem> exampleListFull;
    Context context;
    class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView name;
        TextView aadhar;
        TextView crime;
        TextView proof;
        TextView time;

        LinearLayout linearLayout;
        ExampleViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
            aadhar = itemView.findViewById(R.id.aadhar);
            crime = itemView.findViewById(R.id.crime);
            proof = itemView.findViewById(R.id.proof);
            time = itemView.findViewById(R.id.time);
            linearLayout= itemView.findViewById(R.id.linearlayout);
        }
    }

    public ReportAdapter(List<ReportItem> exampleList, Context context) {
        this.exampleList = exampleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_report, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ReportItem currentItem = exampleList.get(position);
        holder.date.setText(currentItem.getDate());
        holder.name.setText(currentItem.getName());
        holder.aadhar.setText(currentItem.getAadhar());
        holder.crime.setText(currentItem.getCrime());
        holder.proof.setText(currentItem.getProof());
        holder.time.setText(currentItem.getTime());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,Priority.class);
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ReportItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ReportItem item : exampleListFull) {
                    if (item.getProof().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}