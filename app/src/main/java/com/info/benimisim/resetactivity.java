package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetactivity extends AppCompatActivity {
    private Button buttonreset;
    private EditText textViewreset;
    private FirebaseAuth firebaseAuth;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetactivity);

        buttonreset=findViewById(R.id.buttonreset);
        textViewreset=findViewById(R.id.textViewresett);
        firebaseAuth=FirebaseAuth.getInstance();
        adView=findViewById(R.id.bannerreset);
        MobileAds.initialize(resetactivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=textViewreset.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(resetactivity.this,"Lütfen e-mail adresinizi doğru yazdığınızdan emin olunuz",Toast.LENGTH_SHORT).show();

                }
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(resetactivity.this,"E-Mail Hesabınızı Kontol Ediniz",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(resetactivity.this,MainActivityGiris.class));

                            }
                            else {
                                String mesaj=task.getException().getMessage();
                                Toast.makeText(resetactivity.this,mesaj,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}