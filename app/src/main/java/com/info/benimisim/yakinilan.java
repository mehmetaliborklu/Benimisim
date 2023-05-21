package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class yakinilan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private yakinilanadapter yakinilanadapter;
    private List<Kullanıcı> yakinilanlar;
    private DatabaseReference databaseReference;
    private Kullanıcı k;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location anlıkkonum;
    private String konumsaglayici="gps";
    private String ilankm;
    private AdView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yakinilan);

        yakinilanlar=new ArrayList<>();
        banner=findViewById(R.id.banneryakin);
        recyclerView=findViewById(R.id.recyclerviewyakin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        yakinilanadapter=new yakinilanadapter(this,yakinilanlar);
        recyclerView.setAdapter(yakinilanadapter);
        ilankm=getIntent().getStringExtra("ilan");
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(yakinilan.this);
        anlıkkonum=new Location("");

        MobileAds.initialize(yakinilan.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();
        banner.loadAd(adRequest);


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null){
                            anlıkkonum=location;
                            Double lat=location.getLatitude();
                            Double lng=location.getLongitude();
                            anlıkkonum.setLongitude(lng);
                            anlıkkonum.setLatitude(lat);
                            databaseReference= FirebaseDatabase.getInstance().getReference("Kullanıcılar");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot d:snapshot.getChildren()){
                                        k=d.getValue(Kullanıcı.class);
                                        String konum=k.getKonum().toString();
                                        String[] konumlatlng=konum.split(",");
                                        String ücret=k.getücret().toString();
                                        String istanımı=k.getIş_Tanımı();
                                        String telefonno=k.getTelefon();
                                        Location loc=new Location("");
                                        loc.setLatitude(Double.parseDouble(konumlatlng[0]));
                                        loc.setLongitude(Double.parseDouble(konumlatlng[1]));



                                        float mesafe=anlıkkonum.distanceTo(loc);

                                        if ((mesafe/1000) <= Float.parseFloat(ilankm)){

                                            yakinilanlar.add(k);

                                        }

                                        else{

                                        }


                                    }
                                    yakinilanadapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                        else {
                               Toast.makeText(yakinilan.this,"Konum Alınamadı",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }








    }

    private void requestPermissions(String[] strings) {
    }


}

