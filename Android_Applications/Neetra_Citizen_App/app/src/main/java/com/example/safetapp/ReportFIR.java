package com.example.safetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safetapp.Model.FirRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ReportFIR extends AppCompatActivity {
    private FirebaseDatabase firDb = FirebaseDatabase.getInstance();
    private DatabaseReference firRef = firDb.getReference().child("FIRdetail");
    private EditText name, email, phone, date, time, policeStation, policeStationAdd, crimeType;
    private String Name, Email, Date, Time, PoliceStation, PoliceStationAdd, Token, CrimeType;
    private String Phone;
    private FirRecord firRecord;
    private HashMap<String, Object> firDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fir);

        name = findViewById(R.id.name_edit_text);
        email = findViewById(R.id.email_edit_text);
        phone = findViewById(R.id.phone_edit_text);
        date = findViewById(R.id.date_edit_text);
        time = findViewById(R.id.time_edit_text);
        policeStation = findViewById(R.id.police_station_edit_text);
        policeStationAdd = findViewById(R.id.police_st_address_edit_text);
        crimeType = findViewById(R.id.crime_type1_edittext);
        firRecord = new FirRecord();

    }

    public void submitFirRecord(View view) {
        Name = name.getText().toString();
        Email = email.getText().toString();
        Date = date.getText().toString();
        Time = time.getText().toString();
        PoliceStation = policeStation.getText().toString();
        PoliceStationAdd = policeStationAdd.getText().toString();
        Phone = phone.getText().toString();
        CrimeType = crimeType.getText().toString();

        firRecord.setName(Name);
        firRecord.setEmailId(Email);
        firRecord.setDate(Date);
        firRecord.setTime(Time);
        firRecord.setPhoneNo(Phone);
        firRecord.setPoliceStation(PoliceStation);
        firRecord.setPoliceStationAddress(PoliceStationAdd);

        SimpleDateFormat sdf = new SimpleDateFormat("DDHHmmss", Locale.getDefault());


        Token = sdf.format(new Date());
        firRecord.setTokenId(Token);
//        firRef.child(Token).setValue(firRecord)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(ReportFIR.this, "FIR Token Generated", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ReportFIR.this, e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
        firDetail = new HashMap<>();
        firDetail.put("name", Name);
        firDetail.put("emailId", Email);
        firDetail.put("date", Date);
        firDetail.put("time", Time);
        firDetail.put("PhoneNo", Phone);
        firDetail.put("policeStation", PoliceStation);
        firDetail.put("policeStationAddress", PoliceStationAdd);
        firDetail.put("tokenId", Token);
        firDetail.put("crimeType", CrimeType);

        firRef.child(Token).updateChildren(firDetail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ReportFIR.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReportFIR.this, "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


        Toast.makeText(this, R.string.fir_report_recorded, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ReportFIR.this, Fir_token.class));
    }
}

