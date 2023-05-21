package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class yakinilandetay extends AppCompatActivity {
    private TextView textViewücret,textViewistanım,textViewtelefon,textViewkonum;
    private AdView adView;
    private InterstitialAd minterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yakinilandetay);

        String tel=getIntent().getStringExtra("telefon");
        String ücret=getIntent().getStringExtra("ücret");
        String istanımı=getIntent().getStringExtra("istanımı");
        String konum=getIntent().getStringExtra("konum");
        adView=findViewById(R.id.banneryakindetay);
        textViewücret=findViewById(R.id.textViewücretdetay);
        textViewtelefon=findViewById(R.id.textViewtelefondetay);
        textViewistanım=findViewById(R.id.textViewistanimdetay);
        textViewkonum=findViewById(R.id.textViewkonumdetay);


        MobileAds.initialize(yakinilandetay.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });


        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        InterstitialAd.load(this,"ca-app-pub-4479240638574774/8057154739", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        minterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        minterstitialAd = null;
                    }
                });

        textViewücret.setText(ücret);
        textViewistanım.setText(istanımı);
        textViewtelefon.setText(tel);
        if (textViewtelefon.getText().toString().trim().equals("")){
            textViewtelefon.setText("Telefon Bilgisi Girilmemiş");
        }
        textViewkonum.setText(konum);

        textViewkonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(minterstitialAd !=null){
            minterstitialAd.show(yakinilandetay.this);
            minterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {



                @Override
                public void onAdDismissedFullScreenContent() {
                    startActivity(new Intent(yakinilandetay.this,yakinilan.class));
                }
            });
        }
        else {
            startActivity(new Intent(yakinilandetay.this,yakinilan.class));
        }
    }
}