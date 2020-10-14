package com.example.safetapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.safetapp.Model.CrimeCounter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.togglebuttongroup.button.CircularToggle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.sql.StatementEvent;

public class Crime_current_2 extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    Button next_page;
    RadioButton chainSnatchingRB,pickPocketingRB,eveTeasingRB,VandalismRB;
    RadioButton victim,spectatotr, none, minimal,normal,excessive;
    int CAMERA = 2;
    String crimeType,uid;
    Uri photoURI;
    Bitmap rotatedBitmap;
    String mCurrentPhotoPath;
    double lat,lon;
    String lati;
    String loni;
    private int pickPocketing,chainSnatching,EveTeasing,Vandalism;
    int flag;
    private static final int pic_id = 123;
    ImageButton voice;
    CrimeCounter crimeCounter=new CrimeCounter();

    HashMap<String,Object> crimeDataMap=new HashMap<>();
    DatabaseReference crimeDataRef= FirebaseDatabase.getInstance().getReference().child("Reported Crime");
    DatabaseReference crimeCounter1=FirebaseDatabase.getInstance().getReference().child("CrimeCounter");

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_current_2);
        chainSnatchingRB=findViewById(R.id.moles);
        pickPocketingRB=findViewById(R.id.Rape);
        eveTeasingRB=findViewById(R.id.kidnap);
        VandalismRB=findViewById(R.id.traff);

        victim = findViewById(R.id.victim);
        spectatotr = findViewById(R.id.spectator);
        none = findViewById(R.id.none);
        minimal = findViewById(R.id.minimal);
        normal = findViewById(R.id.normal);
        excessive = findViewById(R.id.excessive);

        voice = findViewById(R.id.mic);


        lat=getIntent().getDoubleExtra("Lat",00.00);
        lon=getIntent().getDoubleExtra("Lon",00.00);


        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeechInput(view);
            }
        });

//        Intent camera_intent
//                = new Intent(MediaStore
//                .ACTION_IMAGE_CAPTURE);
//        startActivityForResult(camera_intent, pic_id);

        next_page = findViewById(R.id.current_crim_next);
        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i =  new Intent(Crime_current_2.this, Current_crime_3.class);
//                startActivity(i);
                saveData();
            }
        });


        double roundOffLatitude = (double) Math. round(lat * 100) / 100;

        double roundOffLongitude = (double) Math. round(lon * 100) / 100;


        lati=String.valueOf(roundOffLatitude);
        loni=String.valueOf(roundOffLongitude);
        lati=lati.replace(".","!");
        loni=loni.replace(".","!");

        crimeCounter1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Data").child(lati+" "+loni).exists()))
                {
                    HashMap<String,Object> crimeCounterMap=new HashMap<>();
                    crimeCounterMap.put("ChainSnacthing",0);
                    crimeCounterMap.put("Vandalism",0);
                    crimeCounterMap.put("PickPocketing",0);
                    crimeCounterMap.put("EveTeasin",0);
                    crimeCounter1.child("Data").child(lati+" "+loni).updateChildren(crimeCounterMap);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveData() {

        SimpleDateFormat sdf=new SimpleDateFormat("DDHHmmss", Locale.getDefault());
        uid=sdf.format(new Date());

        crimeDataMap.put("crimeType",crimeType);
        crimeDataMap.put("uId",uid);
        crimeDataMap.put("lat",String.valueOf(lat));
        crimeDataMap.put("lon",String.valueOf(lon));

        crimeDataRef.child(uid).updateChildren(crimeDataMap);

        crimeCounter1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                crimeCounter=dataSnapshot.child("Data").child(lati+" "+loni).getValue(CrimeCounter.class);
                Vandalism=crimeCounter.getVandalism();
                EveTeasing=crimeCounter.getEveTeasing();
                pickPocketing=crimeCounter.getPickPocketing();
                chainSnatching=crimeCounter.getChainSnatching();
                if(chainSnatchingRB.isChecked())
                {
                   chainSnatching++;
                    //Toast.makeText(Crime_current_2.this, "Hi", Toast.LENGTH_SHORT).show();
                }
                else if(eveTeasingRB.isChecked())
                {
                    EveTeasing++;
                }
                else if(pickPocketingRB.isChecked())
                {
                    pickPocketing++;
                }
                else if(VandalismRB.isChecked())
                {
                    Vandalism++;
                }
                crimeCounter.setVandalism(Vandalism);
                crimeCounter.setPickPocketing(pickPocketing);
                crimeCounter.setChainSnatching(chainSnatching);
                crimeCounter.setEveTeasing(EveTeasing);
//                HashMap<String,Object>crimeDataMap1=new HashMap<>();
//                crimeDataMap1.put("kidnapping",kidnapping);
//                crimeDataMap1.put("molestation",molestation);
//                crimeDataMap1.put("rape",rape);
//                crimeDataMap1.put("trafficking",trafficking);
//
//                crimeCounter1.child("Data").child(lati+" "+loni).updateChildren(crimeDataMap1);
                crimeCounter1.child("Data").child(lati+" "+loni).setValue(crimeCounter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent i =  new Intent(Crime_current_2.this, Current_crime_3.class);
                startActivity(i);





    }
    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // startActivityForResult(camera_intent, pic_id);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {

                photoURI = FileProvider.getUriForFile(this,
                        "com.example.safetapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
            }

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("currentpath", mCurrentPhotoPath);
        return image;
    }
    public void ButtonClick(View view)
    {
        switch (view.getId()){
            case R.id.moles:
                crimeType="Chain Snatching";
                flag=1;
                break;
            case R.id.kidnap:
                crimeType="Eve Teasing";
                flag=2;
                break;
            case R.id.Rape:
                crimeType="Pick Pocketing";
                flag=3;
                break;
            case R.id.traff:
                crimeType="Vandalism";
                flag=4;
                break;
        }
    }



//code to support voice input by user to handle app navigation
    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Choose Activity");
        startActivityForResult(intent, REQUEST_CODE);

        //checks device compatibility

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                ArrayList<String> matches = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                assert matches != null;
                if (matches.size() == 0) {
                    Toast.makeText(this, "Please Speak Again", Toast.LENGTH_SHORT).show();
                } else {
                    String mostLikelyThingHeard = matches.get(0);
                    // toUpperCase() used to make string comparison equal
                    if(mostLikelyThingHeard.toUpperCase().equals("NEXT")){
                        startActivity(new Intent(this, Current_crime_3.class));
                    } else if(mostLikelyThingHeard.toUpperCase().equals("CHAIN SNATCHING")) {
                        chainSnatchingRB.setChecked(true);
                        VandalismRB.setChecked(false);
                        eveTeasingRB.setChecked(false);
                        pickPocketingRB.setChecked(false);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("VANDALISM")) {
                        chainSnatchingRB.setChecked(false);
                        VandalismRB.setChecked(true);
                        eveTeasingRB.setChecked(false);
                        pickPocketingRB.setChecked(false);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("EVE TEASING")) {
                        chainSnatchingRB.setChecked(false);
                        VandalismRB.setChecked(false);
                        eveTeasingRB.setChecked(true);
                        pickPocketingRB.setChecked(false);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("PICK POCKETING")) {
                        chainSnatchingRB.setChecked(false);
                        VandalismRB.setChecked(false);
                        eveTeasingRB.setChecked(false);
                        pickPocketingRB.setChecked(true);
                    }

                    else if(mostLikelyThingHeard.toUpperCase().equals("VICTIM")) {
                        victim.setChecked(true);
                        spectatotr.setChecked(false);

                    }

                    else if(mostLikelyThingHeard.toUpperCase().equals("SPECTATOR")) {
                        victim.setChecked(false);
                        spectatotr.setChecked(true);
                    }

                    else if(mostLikelyThingHeard.toUpperCase().equals("NONE")) {
                        none.setChecked(true);
                        minimal.setChecked(false);
                        normal.setChecked(false);
                        excessive.setChecked(false);
                    }

                    else if(mostLikelyThingHeard.toUpperCase().equals("MINIMAL")) {
                        none.setChecked(false);
                        minimal.setChecked(true);
                        normal.setChecked(false);
                        excessive.setChecked(false);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("NORMAL")) {
                        none.setChecked(false);
                        minimal.setChecked(false);
                        normal.setChecked(true);
                        excessive.setChecked(false);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("EXCESSIVE")) {
                        none.setChecked(false);
                        minimal.setChecked(false);
                        normal.setChecked(false);
                        excessive.setChecked(true);
                    }
                    super.onActivityResult(requestCode, resultCode, data);

                }


            }





        }
        try {
            Log.d("resultcodecheck", String.valueOf(requestCode));
            if (requestCode == 2) {
                if (resultCode == RESULT_OK) {
                    rotateBitmapFun();

                    Uri rotatedUri = getImageUri(Crime_current_2.this, rotatedBitmap);

                    if (rotatedUri == null) {
                        Toast.makeText(this, "empty uri", Toast.LENGTH_SHORT).show();

                    }

                            else {
                        InputStream in =  getContentResolver().openInputStream(rotatedUri);
                        File photo=createImageFile();
                        String path=photo.getAbsolutePath();
                        OutputStream out = new FileOutputStream(new File(path));
                        byte[] buf = new byte[1024];
                        int len;
                        while((len=in.read(buf))>0){
                            out.write(buf,0,len);
                        }
                        out.close();
                        in.close();




                            }


                }
            }

        } catch (Exception error) {
            Log.d("Outstream", "onActivityResult: "+error);
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private void rotateBitmapFun() throws IOException {

        //Toast.makeText(this, "Please Wait, Compressing.", Toast.LENGTH_SHORT).show();


        File file = new File(mCurrentPhotoPath);
        Bitmap bitmap = MediaStore.Images.Media
                .getBitmap(Crime_current_2.this.getContentResolver(), Uri.fromFile(file));

        ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;

        }
        rotatedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, 768, 1024, true);

    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}
