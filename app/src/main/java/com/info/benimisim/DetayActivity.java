package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class DetayActivity extends AppCompatActivity {
    private TextView textviewücretim,textViewistanımım;
    private String id;
    private DatabaseReference databaseReference;
    private Button buttonsil;
    private AdView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        textviewücretim=findViewById(R.id.textViewücretim);
        textViewistanımım=findViewById(R.id.textViewistanımım);
        buttonsil=findViewById(R.id.buttonilansil);
        banner=findViewById(R.id.bannerdetay);

        id=getIntent().getStringExtra("id");

        MobileAds.initialize(DetayActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        databaseReference= FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren());
                Kullanıcı kullanıcı=snapshot.getValue(Kullanıcı.class);
                textviewücretim.setText(kullanıcı.getücret().toString()+" "+"₺");
                textViewistanımım.setText(kullanıcı.getIş_Tanımı().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(DetayActivity.this,"İlan Silinmiştir",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetayActivity.this,ilanlarimactivity.class));

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DetayActivity.this,ilanlarimactivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}