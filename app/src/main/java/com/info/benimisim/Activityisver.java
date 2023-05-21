package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Activityisver extends AppCompatActivity {
    private EditText editTextücret, editTextkonum, editTexttelefon, editTextistanım,editTextbaslik;
    private Button buttonilanıver;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mauth;
    private String userid;
    private String email;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private AdView banner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityisver);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(Activityisver.this);
        editTexttelefon = findViewById(R.id.plaintexttelefon);
        editTextistanım = findViewById(R.id.plaintextistanımı);
        editTextkonum = findViewById(R.id.plaintextkonum);
        editTextbaslik=findViewById(R.id.plaintextbaslik);
        editTextücret = findViewById(R.id.plaintextücret);
        buttonilanıver = findViewById(R.id.buttonilanonay);
        mauth = FirebaseAuth.getInstance();
        userid = mauth.getCurrentUser().getUid();
        email = mauth.getCurrentUser().getEmail();
        banner=findViewById(R.id.banner);
        MobileAds.initialize(Activityisver.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest=new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        Dialog dialog=new Dialog(Activityisver.this);
        dialog.setContentView(R.layout.dialoglayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setWindowAnimations(R.style.AnimationForDialog);
        Button buttonkapat;
        buttonkapat=dialog.findViewById(R.id.buttonkapat);
        buttonkapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();






        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location !=null){
                            Double lat=location.getLatitude();
                            Double lng =location.getLongitude();
                            LatLng latLng=new LatLng(lat,lng);
                            editTextkonum.setText(String.valueOf(lat)+","+String.valueOf(lng));
                        }
                        else {
                            editTextkonum.setText("Konum Servisi Açık Değil");
                        }

                    }
                });
            }
            else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            }
        }

        editTextkonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location !=null){
                                    Double lat=location.getLatitude();
                                    Double lng =location.getLongitude();
                                    LatLng latLng=new LatLng(lat,lng);
                                    editTextkonum.setText(String.valueOf(lat)+","+String.valueOf(lng));
                                }
                                else {
                                    editTextkonum.setText("Konum Servisi Açık Değil");
                                }

                            }
                        });
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                    }
                }
            }
        });




        buttonilanıver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String istanımı = editTextistanım.getText().toString();
                String ücret = editTextücret.getText().toString();
                String telefonno = editTexttelefon.getText().toString();
                String konum = editTextkonum.getText().toString();
                String[] konumbölünmüs=konum.split(",");
                String id=" ";
                String baslik=editTextbaslik.getText().toString();
                String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String ülkeadi="";
                String sehiradi="";
                String mahalle="";


                if (!baslik.trim().equals("") && !istanımı.trim().equals("") && !ücret.trim().equals("") && !konum.trim().equals("")) {
                    Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addresses=geocoder.getFromLocation(Double.parseDouble(konumbölünmüs[0]),Double.parseDouble(konumbölünmüs[1]),1);
                        ülkeadi=addresses.get(0).getCountryName();
                        mahalle=addresses.get(0).getAddressLine(0);
                        sehiradi=addresses.get(0).getAdminArea();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar");
                    Kullanıcı kullanıcı=new Kullanıcı(email,telefonno,istanımı,ücret,konum,id,baslik,ülkeadi,sehiradi,mahalle);
                    databaseReference.push().setValue(kullanıcı).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                             startActivity(new Intent(Activityisver.this,ilanlarimactivity.class));


                            }
                        }
                    });




                }
                else if (konum.equals("Konum Servisi Açık Değil")){
                    Toast.makeText(Activityisver.this,"Konum Bilgisi Alınamadı",Toast.LENGTH_SHORT).show();
                }

                else if (!baslik.trim().equals("") && !istanımı.trim().equals("") && !ücret.trim().equals("") && konum.trim().equals("Konum Servisi Açık Değil")){
                    Toast.makeText(Activityisver.this,"Konum Bilgisi Alınamadı",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Activityisver.this,"Lütfen Gerekli Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
                }



            }

        });
    }

    private void requestPermissions(String[] strings) {
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Activityisver.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}