package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ilanlarimactivity extends AppCompatActivity {
    private RecyclerView rv;
    private ArrayList<Kullanıcı> kullanıcıs;
    private Rvadapter rvadapter;
    private DatabaseReference databaseReference;
    private FirebaseUser useremail;
    private AdView banner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlarimactivity);

        banner=findViewById(R.id.banner);
        rv=findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        kullanıcıs=new ArrayList<>();
        rvadapter=new Rvadapter(this,kullanıcıs);
        rv.setAdapter(rvadapter);

        MobileAds.initialize(ilanlarimactivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest=new AdRequest.Builder().build();



        banner.loadAd(adRequest);


        databaseReference= FirebaseDatabase.getInstance().getReference("Kullanıcılar");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d:snapshot.getChildren()){
                    Kullanıcı kullanıcı=d.getValue(Kullanıcı.class);
                    String kullanıcıid=d.getKey();
                    kullanıcı.setid(kullanıcıid);

                    useremail=FirebaseAuth.getInstance().getCurrentUser();
                    String email=useremail.getEmail();
                    if (email.equals(kullanıcı.getEmail())){
                        kullanıcıs.add(kullanıcı);
                    }
                    else {

                    }

                }
                rvadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ilanlarimactivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}