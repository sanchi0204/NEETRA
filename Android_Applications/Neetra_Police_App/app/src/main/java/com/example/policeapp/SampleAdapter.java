package com.example.policeapp;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
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

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.MyViewHolder> implements Filterable {

    private List<ReportItem> exampleList;
    Context context;
    private List<ReportItem> exampleListFull;


    public SampleAdapter(List<ReportItem> exampleList, Context context) {
        this.exampleList = exampleList;
        this.context = context;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.content_report, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date,name,aadhar,crime,proof,time;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
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

    private Filter exampleFilter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ReportItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ReportItem item : exampleListFull) {
                    if (item.getCrime().toLowerCase().contains(filterPattern)) {
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
