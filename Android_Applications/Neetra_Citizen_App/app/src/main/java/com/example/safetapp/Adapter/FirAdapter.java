package com.example.safetapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safetapp.Model.FirRecord;
import com.example.safetapp.R;

import java.util.List;

public class FirAdapter extends RecyclerView.Adapter<FirAdapter.FirViewHolder> {
    Context ctx;
    List<FirRecord> firRecords;

    public FirAdapter(Context ctx, List<FirRecord> firRecords) {
        this.ctx = ctx;
        this.firRecords = firRecords;
    }

    @NonNull
    @Override
    public FirAdapter.FirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.content_fir_token, parent, false);

        FirViewHolder firViewHolder = new FirViewHolder(view);
        return firViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirAdapter.FirViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        final FirRecord firRecord = firRecords.get(position);
        holder.name.setText(firRecord.getName());
        holder.date.setText(firRecord.getDate());
        holder.time.setText(firRecord.getTime());
        holder.emailId.setText(firRecord.getEmailId());
        holder.phoneNo.setText(firRecord.getPhoneNo());
        holder.policeStation.setText(firRecord.getPoliceStation());
        holder.policeStationAddress.setText(firRecord.getPoliceStationAddress());
        holder.crimeType.setText(firRecord.getCrimeType());
        if (firRecord.getCrimeType().equals("Chain Snatching")) {
            holder.crimeType.setBackgroundColor(res.getColor(R.color.red));
        }
        if (firRecord.getCrimeType().equals("Vandalism")) {
            holder.crimeType.setBackgroundColor(res.getColor(R.color.green));
        }

        if (firRecord.getCrimeType().equals("Eve Teasing")) {
            holder.crimeType.setBackgroundColor(res.getColor(R.color.colorPrimary_w));
        }

        if (firRecord.getCrimeType().equals("")) {
            holder.crimeType.setBackgroundColor(res.getColor(R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return firRecords.size();
    }

    public class FirViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, time, emailId, phoneNo, policeStation, policeStationAddress, crimeType;


        public FirViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            emailId = itemView.findViewById(R.id.email);
            phoneNo = itemView.findViewById(R.id.mob);
            policeStation = itemView.findViewById(R.id.police_station);
            policeStationAddress = itemView.findViewById(R.id.police_station_address);
            crimeType = itemView.findViewById(R.id.crime_type);

        }
    }
}
